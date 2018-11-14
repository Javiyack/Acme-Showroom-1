
package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {@Index(columnList = "title, description")})
public class Chirp extends DomainEntity implements Comparable<Chirp>{

    private String title;
    private String description;
    private Date moment;
    // Relationships
    private Actor actor;
    private String topic;

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
    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String body) {
        this.description = body;
    }

    @NotBlank
    @SafeHtml
    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @ManyToOne(optional = true)
    @Valid
    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }


    @SafeHtml
    @Size(max = 16)
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }


    @Override
    public int compareTo(Chirp o) {
        int result;
        result = this.getActor().getUserAccount().getUsername().compareTo(o.getActor().getUserAccount().getUsername());
        if (result==0)
            result = this.getMoment().compareTo(o.getMoment());
        if (result==0)
            result = this.getTopic().compareTo(o.getTopic());
        if (result==0)
            result = this.getTitle().compareTo(o.getTitle());
        return result;
    }
}
