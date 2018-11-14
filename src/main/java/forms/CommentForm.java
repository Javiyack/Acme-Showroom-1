
package forms;

import domain.Actor;
import domain.Item;
import domain.Showroom;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;


public class CommentForm {

    private String title;
    private String text;
    private Date moment;
    private Integer rating;
    private Collection<String> pictures;
    // Relationships
    private Actor actor;
    private Item item;
    private Showroom showroom;
    // Static
    public static String SHOWROOM       = "Showroom";
    public static String ITEM       = "Item";

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
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

    @Valid
    @ManyToOne(optional = true)
    public Item getItem() {
        return this.item;
    }

    public void setItem(final Item parentFolder) {
        this.item = parentFolder;
    }

    @ManyToOne(optional = true)
    @Valid
    public Showroom getShowroom() {
        return showroom;
    }

    public void setShowroom(Showroom showroom) {
        this.showroom = showroom;
    }

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
