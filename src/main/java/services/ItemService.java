
package services;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.ItemRepository;

import java.util.Calendar;
import java.util.Collection;
import java.util.Random;

@Service
@Transactional
public class ItemService {

    // Managed repository -----------------------------------------------------
    @Autowired
    private ItemRepository itemRepository;

    // Supporting services
    @Autowired
    private ActorService actorService;
    @Autowired
    private RequestService requestService;


    // CRUD Methods

    // Create
    public Item create(Showroom showroom) {
        Actor actor = actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.owned.block");
        Item result= new Item();
        result.setShowroom(showroom);
        result.setSKU(this.generateSKU());
        return result;
    }

    private String generateSKU() {
        String result ="";
        Calendar calendar = Calendar.getInstance();
        result += (("" + calendar.get(Calendar.YEAR) % 100).length() == 2) ? calendar.get(Calendar.YEAR) % 100
                : "0" + calendar.get(Calendar.YEAR) % 100;
        int mes = calendar.get(Calendar.MONTH) + 1;
        result += (("" + mes).length() == 2) ? mes : "0" + mes;
        result += (("" + calendar.get(Calendar.DAY_OF_MONTH)).length() == 2) ? calendar.get(Calendar.DAY_OF_MONTH)
                : "0" + calendar.get(Calendar.DAY_OF_MONTH);
        result += "-";
        String uppercaseLetters = "ABCEFGHYJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        for (int i = 0; i < 4; i++) {
            result += uppercaseLetters.charAt(rnd.nextInt(26));
        }
        result += rnd.nextInt(10);
        result += rnd.nextInt(10);

        return result;
    }

    // Save
    public Item save( Item item) {
        Assert.notNull(item, "msg.not.found.resource");
        Actor actor = actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.owned.block");
        Assert.isTrue(item.getShowroom().getUser().equals(actor), "msg.not.owned.block");
        Item bdItem = itemRepository.findOne(item.getId());
        if(item.getId()!=0){
            item.setSKU(bdItem.getSKU());
        }
        Item result = itemRepository.saveAndFlush(item);
        return result;
    }

    public Item findOne(Integer id) {
        return itemRepository.findOne(id);
    }

    public Collection<Item> findAll() {
        Collection<Item> items =  itemRepository.findAll();
        return items;
    }

    public Collection<Item> findByLogedActor() {
        Actor actor;
        actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        return itemRepository.findByUserId((actor.getId()));
    }

    public Collection<Item> findByUserId(int id) {
        return itemRepository.findByUserId(id);
    }

    public Collection<Item> findByKeyWord(String keyWord) {
        return itemRepository.findByKeyWord(keyWord);
    }


    public void flush() {
        this.itemRepository.flush();

    }

    public void delete(Item item) {
        Assert.notNull(item, "msg.not.found.resource");
        this.delete(item.getId());
    }

    public void delete(int itemId) {
        Actor actor = actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.owned.block");
        Assert.isTrue(!hasRequests(itemId), "msg.item.has.requests.block");
        itemRepository.delete(itemId);
    }
    public boolean hasRequests(Integer itemId){
        Collection<Request> requests = requestService.findByItemId(itemId);
        return !requests.isEmpty();
    }
    public Collection<Item> findByShowroom(Showroom showroom) {
        return this.findByShowroomId(showroom.getId());
    }
    public Collection<Item> findByShowroomId(Integer showroomId) {

        return itemRepository.findByShowroomId(showroomId);
    }

    public Collection<Item> findByKeyWordAndShowroom(String word, Integer showroomId) {

        return itemRepository.findByKeyWordAndShowroom(word, showroomId );
    }

    public Collection <Item> findByKeyWordAndLogedActor(String word) {
        Actor actor = actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.owned.block");
        return itemRepository.findByKeyWordAndLogedActor(word, actor.getId());
    }
}
