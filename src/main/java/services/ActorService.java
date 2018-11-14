
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Actor;
import domain.Administrator;
import domain.Agent;
import domain.Chirp;
import domain.User;
import forms.ActorForm;
import forms.AgentForm;
import forms.UserForm;
import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;

@Service
@Transactional
public class ActorService {

    // Managed repository -----------------------------------------------------
    @Autowired
    private ActorRepository actorRepository;

    // Supporting services ----------------------------------------------------
    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private Validator validator;

    // Constructors -----------------------------------------------------------
    public ActorService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Collection <Actor> findAll() {
        Collection <Actor> result;

        result = this.actorRepository.findAll();
        Assert.notNull(result);

        return result;
    }

    public Actor findOne(final int actorId) {
        Assert.isTrue(actorId != 0);

        Actor result;

        result = this.actorRepository.findOne(actorId);
        Assert.notNull(result);

        return result;
    }

    public Actor findOneIfActive(final int actorId) {
        Assert.isTrue(actorId != 0);

        Actor result;

        result = this.actorRepository.findOneIfActive(actorId);
        Assert.notNull(result);

        return result;
    }

    public Actor save(final Actor actor) {
        Assert.notNull(actor);
        Actor result;

        if (actor.getId() == 0) {
            actor.setTopics(new TreeSet <String>());
            actor.setFollows(new TreeSet <Actor>());
        } else {
            Assert.isTrue(actor.equals(this.findByPrincipal()), "not.allowed.action");
        }
        result = this.actorRepository.save(actor);
        this.flush();

        return result;
    }

    public void delete(final Actor actor) {
        Assert.notNull(actor);
        Assert.isTrue(actor.getId() != 0);
        Assert.isTrue(this.actorRepository.exists(actor.getId()));
        Assert.isTrue(actor.equals(this.findByPrincipal()));

        this.actorRepository.delete(actor);
    }

    // Other business methods -------------------------------------------------


    public Actor findByPrincipal() {
        Actor result = null;
        UserAccount userAccount;

        try {
            userAccount = LoginService.getPrincipal();
            Assert.notNull(userAccount, "msg.not.logged.block");
            result = this.findByUserAccount(userAccount);
            Assert.notNull(result, "msg.not.logged.block");
        } catch (final Throwable oops) {
        }

        return result;
    }

    public Actor findByUserAccount(final UserAccount userAccount) {
        Assert.notNull(userAccount);

        Actor result;

        result = this.actorRepository.findByUserAccountId(userAccount.getId());
        Assert.notNull(result);

        return result;
    }


    public void follow(int userId) {
        final Actor actor = this.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Actor followed = this.findOne(userId);
        if(actor.getFollows().contains(followed)){
            actor.getFollows().remove(followed);
            this.save(actor);
        }
        else{
            actor.getFollows().add(followed);
            this.save(actor);
        }
    }

    public void subscribe(String topic) {
        final Actor actor = this.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        topic=topic.trim();
        if(!topic.isEmpty() && !actor.getTopics().contains(topic)){
            actor.getTopics().add(topic);
            this.save(actor);
        }
    }

    public void unSubscribe(String topic) {
        final Actor actor = this.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        actor.getTopics().remove(topic);
        this.save(actor);
    }

    public Collection <Actor> findFollowers () {
        final Actor actor = this.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        return actorRepository.findFollowers(actor.getId());
    }
    public Collection <Actor> findFollows () {
        final Actor actor = this.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        return actor.getFollows();
    }

    public Collection <String> findTopics () {
        final Actor actor = this.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        return actor.getTopics();
    }

    public Collection <String> findAllTopics () {
        final Actor actor = this.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        return actorRepository.findAllTopics();
    }



    public Map<Actor, Integer> findFollowersCountPerUser() {
        Map<Actor, Integer> result = new HashMap <>();
        Collection<Actor> actors = this.findAll();
        for(Actor actor:actors){
            int count =0;
            for(Actor follower:actors){
                if(follower.getFollows().contains(actor))
                    count++;
            }
            result.put(actor,count);
        }
        return result;
    }


    public Actor reconstruct(ActorForm actorForm, BindingResult binding) {
        Actor actor = null;
        Actor logedActor = this.findByPrincipal();

        UserAccount useraccount = null;
        final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        if (actorForm.getId() == 0) {
            this.validator.validate(actorForm.getAccount(), binding);

            actorForm.getAccount().setNewPassword(actorForm.getAccount().getPassword());
            useraccount = new UserAccount();
            if (actorForm instanceof UserForm) {
                actor = new User();

            } else if (actorForm instanceof AgentForm) {
                actor = new Agent();
            } else {
                actor = new Administrator();
            }
            useraccount = this.userAccountService.create(actorForm.getAccount().getAuthority());
            actor.setUserAccount(useraccount);

            actor.getUserAccount().setUsername(actorForm.getAccount().getUsername());
            actor.getUserAccount().setPassword(actorForm.getAccount().getPassword());
            actor.setName(actorForm.getName());
            actor.setSurname(actorForm.getSurname());
            actor.setEmail(actorForm.getEmail());
            actor.setPhone(actorForm.getPhone());
            actor.setAddress(actorForm.getAddress());

            Assert.isTrue(actorForm.getAccount().getPassword().equals(actorForm.getAccount().getConfirmPassword()),
                    "msg.userAccount.repeatPassword.mismatch");
            actor.getUserAccount().setPassword(encoder.encodePassword(actorForm.getAccount().getPassword(), null));
            if (!(logedActor instanceof Administrator))
                Assert.isTrue(actorForm.isAgree(), "msg.not.terms.agree.block");

        } else {
            final String formPass = encoder.encodePassword(actorForm.getAccount().getPassword(), null);
            actor = this.findByPrincipal();
            Assert.notNull(actor, "msg.not.logged.block");
            if (!binding.hasErrors()) {
                actor.setName(actorForm.getName());
                actor.setSurname(actorForm.getSurname());
                actor.setEmail(actorForm.getEmail());
                actor.setPhone(actorForm.getPhone());
                actor.setAddress(actorForm.getAddress());
            }
            // Si ha cambiado alg�n par�metro del Authority (Usuario, password)
            // Si ha cambiado el nombre de usuario
            if (!actorForm.getAccount().getUsername().equals(actor.getUserAccount().getUsername())) {
                if (!actorForm.getAccount().getNewPassword().isEmpty()) {
                    // Valida el la cuenta de usuario
                    this.validator.validate(actorForm.getAccount(), binding);
                    Assert.isTrue(
                            actorForm.getAccount().getNewPassword().equals(actorForm.getAccount().getConfirmPassword()),
                            "msg.userAccount.repeatPassword.mismatch");
                    // Cambia la contrase�a
                    // Comprueba la contrase�a y la cambia si todo ha ido bien
                    Assert.isTrue(formPass.equals(actor.getUserAccount().getPassword()), "msg.wrong.password");
                    Assert.isTrue(checkLength(actorForm.getAccount().getNewPassword()), "msg.password.length");
                    actor.getUserAccount()
                            .setPassword(encoder.encodePassword(actorForm.getAccount().getNewPassword(), null));
                } else {
                    actorForm.setNewPassword(null);
                    actorForm.getAccount().setConfirmPassword(null);
                    // Valida el la cuenta de usuario
                    this.validator.validate(actorForm.getAccount(), binding);
                    // Comprueba la contrase�a
                    Assert.isTrue(formPass.equals(actor.getUserAccount().getPassword()), "msg.wrong.password");

                }

                // Cambia el nombre de usuario
                actor.getUserAccount().setUsername(actorForm.getAccount().getUsername());

            } else { // Si NO ha cambiado el nombre se usuario
                if (!actorForm.getAccount().getPassword().isEmpty()) {
                    if (!actorForm.getAccount().getNewPassword().isEmpty()) {
                        Assert.isTrue(
                                actorForm.getAccount().getNewPassword()
                                        .equals(actorForm.getAccount().getConfirmPassword()),
                                "msg.userAccount.repeatPassword.mismatch");
                        // Comprueba la contrase�a
                        Assert.isTrue(formPass.equals(actor.getUserAccount().getPassword()), "msg.wrong.password");
                        Assert.isTrue(checkLength(actorForm.getAccount().getNewPassword()), "msg.password.length");
                        actor.getUserAccount()
                                .setPassword(encoder.encodePassword(actorForm.getAccount().getNewPassword(), null));
                    } else {
                        actorForm.getAccount().setNewPassword("XXXXX");
                        actorForm.getAccount().setConfirmPassword("XXXXX");
                        // Comprueba la contrase�a
                        Assert.isTrue(formPass.equals(actor.getUserAccount().getPassword()), "msg.wrong.password");
                    }

                } else {
                    // Como no ha cambiado ni usuario ni escrito contrase�a seteamos temporalmente
                    // el username y passwords para pasar la validacion de userAccount
                    // Valida El formulario
                    actorForm.getAccount().setPassword("XXXXX");
                    actorForm.getAccount().setNewPassword("XXXXX");
                    actorForm.getAccount().setConfirmPassword("XXXXX");
                    this.validator.validate(actorForm.getAccount(), binding);
                }
            }
        }
        return actor;
    }

    private boolean checkLength(String newPassword) {
        return newPassword.length() > 4 && newPassword.length() < 33;
    }

    public String getType(final UserAccount userAccount) {

        final List <Authority> authorities = new ArrayList <Authority>(userAccount.getAuthorities());

        return authorities.get(0).getAuthority();
    }

    public void flush() {
        this.actorRepository.flush();

    }

    public Boolean checkIfSubscribedToActor(Actor actor) {
        final Actor principal = this.findByPrincipal();
        Assert.notNull(principal, "msg.not.logged.block");
        Boolean result = principal.getFollows().contains(actor);
        return result;
    }

    public Boolean checkIfSubscribedToTopic(Chirp chirp) {
        final Actor actor = this.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Boolean result = actor.getTopics().contains(chirp.getTopic());
        return result;
    }
}
