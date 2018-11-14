package repositories;

import domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	@Query("select i from Item i where i.showroom.id=?1")
	Collection<Item> findByShowroomId(Integer showroomId);

	@Query("select i from Item i where i.title like %?1% or i.description like %?1% or i.SKU like %?1%")
	Collection<Item> findByKeyWord(String keyWord);

	@Query("select i from Item i where (i.title like %?1% or i.description like %?1% or i.SKU like %?1%) and i.showroom.id=?2")
	Collection<Item> findByKeyWordAndShowroom(String word, Integer showroomId);
	@Query("select i from Item i where (i.title like %?1% or i.description like %?1% or i.SKU like %?1%) and i.showroom.user.id=?2")
	Collection<Item> findByKeyWordAndLogedActor(String word, Integer actorId);

	@Query("select i from Item i where i.SKU like %?1%")
	Collection<Item> findBySKU(String keyWord);

	@Query("select i from Item i where i.title like %?1% or i.description like %?1% or i.SKU like %?1%")
	Collection<Item> findByIndexedKeyWord(String keyWord);

	@Query("select i from Item i" +
			" where i.showroom.user.id=?1")
	Collection<Item> findByUserId(int id);

}
