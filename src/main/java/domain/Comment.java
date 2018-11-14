
package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {@Index(columnList = "title, text")})
public class Comment extends DomainEntity {

    private String title;
    private String text;
    private Date moment;
    private Integer rating;
    private Collection<String> pictures;
    // Relationships
    private Actor actor;
    private int commentedObjectId;


    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    public Date getMoment() {
        return this.moment;
    }

    public void setMoment(final Date moment) {
        this.moment = moment;
    }

    @NotBlank
    @SafeHtml
    public String getText() {
        return this.text;
    }

    public void setText(final String body) {
        this.text = body;
    }

    @NotBlank
    @SafeHtml
    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }


    @NotNull
    public int getCommentedObjectId() {
        return this.commentedObjectId;
    }

    public void setCommentedObjectId(final int commentedObjectId) {
        this.commentedObjectId = commentedObjectId;
    }

    @Range(min = 0, max = 3)
    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @ElementCollection
    @NotNull
    public Collection<String> getPictures() {
        return pictures;
    }

    public void setPictures(Collection<String> pictures) {
        this.pictures = pictures;
    }

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor user) {
        this.actor = user;
    }
}
