package repositories;

import domain.Showroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ShowroomRepository extends JpaRepository<Showroom, Integer> {


	@Query("select r from Showroom r where r.user.id=?1")
	Collection<Showroom> findByOwner(Integer ownerId);


	@Query("select r from Showroom r where r.name like %?1% or r.description like %?1%")
	Collection<Showroom> findByKeyWord(String keyWord);

	@Query("select r from Showroom r where r.user.id=?1")
	Collection<Showroom> findByUserId(int id);
}
