package services;

import domain.Actor;
import domain.Chirp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.ChirpRepository;

import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class ChirpService {

    //Repositories
    @Autowired
    private ChirpRepository chirpRepository;

    //Services
    @Autowired
    private ActorService actorService;

    //Constructor
    public ChirpService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    //Create
    public Chirp create() {
        final Chirp result = new Chirp();
        result.setMoment(new Date());
        return result;
    }

    public Chirp save(Chirp chirp) {
        Assert.notNull(chirp, "msg.commit.error");
        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        chirp.setActor(actor);

        if (chirp.getId() == 0) {
            chirp.setTopic(chirp.getTopic().trim());
            chirp = chirpRepository.save(chirp);
        }
        return chirp;
    }

    public Collection <Chirp> findAll() {
        return chirpRepository.findAll();
    }

    public Chirp findOne(int chirpId) {
        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        return chirpRepository.findOne(chirpId);
    }

    public Collection <Chirp> findByLoggedActor() {
        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        return chirpRepository.findByActorId(actor.getId());
    }

    public Collection <Chirp> findByActorId(Integer actorId) {
        return chirpRepository.findByActorId(actorId);
    }

    public Collection <String> findAllTopics() {
        return chirpRepository.findAllTopics();
    }

    public Collection<Chirp> findByTopic(String topic) {
        return chirpRepository.findByTopic(topic);
    }

    public Chirp createAutomaticChrip(String topic, String tile, String text) {
        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Chirp chirp = this.create();
        chirp.setTopic(topic);
        chirp.setTitle(tile);
        chirp.setDescription(text);
        return this.save(chirp);
    }

    public Collection <Chirp> findFollowedChirps() {
        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        return chirpRepository.findFollowedChirps(actor);
    }



}
