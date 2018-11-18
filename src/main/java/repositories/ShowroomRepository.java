package repositories;

import domain.Showroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ShowroomRepository extends JpaRepository<Showroom, Integer> {


	@Query("select s from Showroom s where s.user.id=?1")
	Collection<Showroom> findByOwner(Integer ownerId);


	@Query("select s from Showroom s where s.name like %?1% or s.description like %?1%")
	Collection<Showroom> findByKeyWord(String keyWord);

	@Query("select s from Showroom s where s.user.id=?1")
	Collection<Showroom> findByUserId(int id);

	@Query("select s from Showroom s where (s.name like %?1% or s.description like %?1%) and s.user.id=?2")
	Collection<Showroom> findByKeyWordAndUserId(String word, Integer userId);
}
