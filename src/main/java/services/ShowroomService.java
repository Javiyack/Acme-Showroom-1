
package services;

import domain.Actor;
import domain.Item;
import domain.Showroom;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.ItemRepository;
import repositories.ShowroomRepository;

import java.util.Collection;

@Service
@Transactional
public class ShowroomService {

    // Managed repository -----------------------------------------------------
    @Autowired
    private ShowroomRepository showroomRepository;

    // Supporting services
    @Autowired
    private ActorService actorService;
    @Autowired
    private ItemRepository itemRepository;


    // CRUD Methods

    // Create
    public Showroom create() {
        Actor actor = actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.owned.block");
        Showroom result=new Showroom();
        result.setUser((User)actor);
        return result;
    }

    // Save
    public Showroom save( Showroom showroom) {
        Assert.notNull(showroom, "msg.not.found.resource");
        Actor actor = actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.owned.block");
        Assert.isTrue(showroom.getUser().equals(actor), "msg.not.owned.block");
        Showroom result = showroomRepository.saveAndFlush(showroom);

        return result;
    }

    public Showroom findOne(Integer id) {
        return showroomRepository.findOne(id);
    }

    public Collection<Showroom> findAll() {
        Collection<Showroom> showrooms =  showroomRepository.findAll();
        return showrooms;
    }

    public Collection<Showroom> findByLogedActor() {
        Actor actor;
        actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        return showroomRepository.findByOwner(actor.getId());
    }

    public Collection<Showroom> findByUserId(int id) {
        return showroomRepository.findByUserId(id);
    }

    public Collection<Showroom> findByKeyWord(String keyWord) {
        return showroomRepository.findByKeyWord(keyWord);
    }


    public void flush() {
        this.showroomRepository.flush();

    }

    public void delete(Showroom showroom) {
        Assert.notNull(showroom, "msg.not.found.resource");
        Assert.notNull(showroom.getUser().equals(actorService.findByPrincipal()), "msg.not.owned.block");
        Collection<Item> Items = itemRepository.findByShowroomId(showroom.getId());
        for (Item item:Items) {
            itemRepository.delete(item);
        }
        showroomRepository.delete(showroom);
    }
    public void delete(int showroomId) {
        Showroom showroom = showroomRepository.findOne(showroomId);
        Assert.notNull(showroom, "msg.not.found.resource");
        Assert.isTrue(showroom.getUser().equals(actorService.findByPrincipal()), "msg.not.owned.block");
        Collection<Item> items = itemRepository.findByShowroomId(showroomId);
        itemRepository.deleteInBatch(items);
        showroomRepository.delete(showroomId);
    }

    public Collection<Showroom> findByKeyWordAndUserId(String word, Integer userId) {
            return showroomRepository.findByKeyWordAndUserId(word, userId);
    }
}
