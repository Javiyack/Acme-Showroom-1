
package repositories;

import domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    
    @Query("select r from Request r where r.user.id=?1")
    Collection<Request> findByUser(int id);

    @Query("select r from Request r where r.item.showroom.user.id=?1")
    Collection<Request> findReceivedRequests(int id);

    @Query("select r from Request r where r.item.id=?1")
    Collection<Request> findByItemId(int id);

	@Query("select r from Request r where r.item.id=?1 and r.id!=?2")
    Collection<Request> findOthersByItemId(int itemId, int requestId);
}

