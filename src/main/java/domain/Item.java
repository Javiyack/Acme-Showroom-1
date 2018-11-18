
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {@Index(columnList = "SKU, title, description")})
public class Item extends Commentable {
    /*store an auto-generated SKU, a
    title, a description, a price, and whether it?s available or not.*/
    private String SKU;
    private String title;
    private String description;
    private Boolean available;

    private Double price;

    // Relationships
    private Showroom showroom;
    // Relationships


    @NotBlank
    @SafeHtml
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    @NotNull
    @Valid
    @Digits(integer = 7, fraction = 2 )
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double length) {
        this.price = length;
    }

    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public Showroom getShowroom() {
        return showroom;
    }

    public void setShowroom(Showroom showroom) {
        this.showroom = showroom;
    }


    @NotBlank
    @SafeHtml
    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        super.setObjectName(title);
        this.title = title;
    }


    @NotBlank
    @Pattern(regexp = "^\\d\\d([0][1-9]|[1][0-2])([0][1-9]|[12][0-9]|[3][01])-[A-Z]{4}\\d\\d$")
    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
