
package repositories;

import domain.Actor;
import domain.Chirp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ChirpRepository extends JpaRepository<Chirp, Integer> {
    @Query("select c from Chirp c where c.actor.id=?1")
    Collection<Chirp> findByActorId(int actorId);

    @Query("select distinct(c.topic) from Chirp c")
    Collection<String> findAllTopics();

    @Query("select c from Chirp c where c.topic=?1")
    Collection<Chirp> findByTopic(String topic);

    @Query("select c from Chirp c, Actor a where a = ?1 " +
            "and (c.topic member of a.topics " +
            "or c.actor member of a.follows)")
    Collection<Chirp> findFollowedChirps(Actor actor);
}
