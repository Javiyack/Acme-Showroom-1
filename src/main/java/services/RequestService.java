package services;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.RequestRepository;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class RequestService {
    //Repositories
    @Autowired
    private RequestRepository requestRepository;
    //Services
    @Autowired
    private ActorService actorService;
    @Autowired
    private ItemService itemService;

    @Autowired
    private Validator validator;

    // CRUD  -----------------------------------------------------
    public Request save(Request request) {
        Assert.isTrue(request.getItem().getAvailable(), "msg.not.available.item.block");
        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.user.block");
        Request result = requestRepository.findOne(request.getId());
        if (request.getId() == 0) {
            Assert.isTrue(request.getUser().equals((User) actor)
                    || request.getItem().getShowroom().getUser().equals((User) actor), "msg.not.owned.block");
            Assert.isTrue(!request.getItem().getShowroom().getUser().equals((User) actor), "msg.not.own.request.block");
            request.setStatus(Constant.requestStatus.PENDING.toString());
            if (request.getItem().getPrice() > 0) {
                Assert.notNull(request.getCreditCard(), "msg.not.credit.card.block");
                Calendar fechaLimite = Calendar.getInstance();
                Calendar expirationDate = Calendar.getInstance();
                expirationDate.set(Integer.parseInt(20 + request.getCreditCard().getExpirationYear()),
                        Integer.parseInt(request.getCreditCard().getExpirationMonth()) - 1, 1);
                Assert.isTrue(expirationDate.after(fechaLimite), "msg.credit.card.expired.block");
                fechaLimite.add(Calendar.DATE, 30);
                Assert.isTrue(expirationDate.after(fechaLimite), "msg.credit.card.close.to.expire.block");
            }
            return requestRepository.save(request);
        } else {
            Assert.isTrue(result.getUser().equals((User) actor)
                    || result.getItem().getShowroom().getUser().equals((User) actor), "msg.not.owned.block");
            String status = result.getStatus();
            result.setStatus(request.getStatus());
            if (!status.equals(Constant.requestStatus.PENDING.toString())) // Aqui impedimos el cambio de estado una vez aceptado o rechazado
                result.setStatus(status);
            return requestRepository.save(result);

        }

    }


    public Request create(int itemId) {
        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.user.block");
        Item item = itemService.findOne(itemId);
        Assert.isTrue(item.getAvailable(), "msg.not.available.item.block");
        Request request = new Request();
        request.setUser((User) actor);
        request.setMoment(new Date());
        request.setItem(item);
        request.setStatus(Constant.requestStatus.PENDING.toString());
        return request;
    }

    public Collection <Request> findCreatedRequests() {
        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.user.block");
        return this.requestRepository.findByUser(actor.getId());
    }

    public Collection <Request> findRecivedRequests() {

        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.user.block");
        return this.requestRepository.findReceivedRequests(actor.getId());
    }

    public Request findOne(int requestId) {
        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.user.block");
        Request result = requestRepository.findOne(requestId);
        Assert.isTrue(result.getUser().equals((User) actor) || result.getItem().getShowroom().getUser().equals((User) actor), "msg.not.owned.block");
        return result;
    }

    public Request reconstruct(Request request, BindingResult binding) {
        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.user.block");
        Item item = this.itemService.findOne(request.getItem().getId());
        request.setItem(item);
        Actor userActor = this.actorService.findOne(request.getUser().getId());
        Assert.notNull(userActor, "msg.not.logged.block");
        Assert.isTrue(userActor instanceof User, "msg.not.user.block");
        request.setUser((User) userActor);
        if (item.getPrice() > 0)
            this.validator.validate(request, binding);
        else {
            CreditCard cd = new domain.CreditCard();
            cd.setBrandName("Brand Name");
            cd.setHolderName("Holder Name");
            cd.setCardNumber("1111-2222-3333-4444");
            cd.setExpirationMonth("12");
            cd.setExpirationYear("99");
            cd.setCVV("999");
            request.setCreditCard(cd);
            this.validator.validate(request, binding);
            request.setCreditCard(null);
        }
        Assert.isTrue(userActor.equals((User) actor) || request.getItem().getShowroom().getUser().equals((User) actor), "msg.not.owned.block");

        return request;
    }

    public Collection <Request> findByItemId(int id) {

        return this.requestRepository.findByItemId(id);
    }
}
