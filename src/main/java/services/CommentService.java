package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Actor;
import domain.Comment;
import repositories.CommentRepository;

@Service
@Transactional
public class CommentService {

    //Repositories
    @Autowired
    private CommentRepository commentRepository;

    //Services
    @Autowired
    private ActorService actorService;
    @Autowired
    private Validator validator;

    //Constructor
    public CommentService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    //Create
    public Comment create() {
        final Comment result = new Comment();
        result.setMoment(new Date());
        return result;
    }

    public Comment save(Comment comment) {
        Actor actor = actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(comment.getCommentedObjectId()!=0, "msg.save.first");
        comment.setMoment(new Date());
        return commentRepository.save(comment);
    }

    public Comment findOne(int followId) {
        return commentRepository.findOne(followId);
    }


    public Collection <Comment> findByCommentedObjectId(Integer objectId) {
        return commentRepository.findByCommentedObjectId(objectId);    }

    public Comment recontruct(Comment comment, BindingResult binding ) {
        Actor actor = actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        comment.setActor(actor);
        this.validator.validate(comment, binding);
        return comment;
    }
}
