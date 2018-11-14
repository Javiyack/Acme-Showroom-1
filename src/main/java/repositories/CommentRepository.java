
package repositories;

import domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("select c from Comment c where c.commentedObjectId=?1")
    Collection<Comment> findByCommentedObjectId(int objectId);

    @Query("select c from Comment c where c.actor.id=?1")
    Collection<Comment> findByActor(int actorId);

    @Query("select c from Comment c where c.actor.id=?1 and c.commentedObjectId=?2")
    Collection<Comment> findByActorAndObject(int actorId, int objectId);
}
