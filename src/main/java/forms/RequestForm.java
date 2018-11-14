package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RequestForm {

    private String name;
    private String description;
    private String logo;
    private String welcomeMessageEs;
    private String welcomeMessageEn;
    private double length;

    @NotBlank
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getDescription() {
        return description;
    }

    public void setDescription(String descrip) {
        this.description = descrip;
    }

    @NotBlank
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getName() {
        return this.name;
    }

    public void setName(final String companyName) {
        this.name = companyName;
    }

    @SafeHtml(whitelistType = WhiteListType.NONE)
    @NotBlank
    public String getWelcomeMessageEs() {
        return this.welcomeMessageEs;
    }

    public void setWelcomeMessageEs(final String welcomeMessageEs) {
        this.welcomeMessageEs = welcomeMessageEs;
    }

    @SafeHtml(whitelistType = WhiteListType.NONE)
    @NotBlank
    public String getWelcomeMessageEn() {
        return this.welcomeMessageEn;
    }

    public void setWelcomeMessageEn(final String wecolmeMessageEn) {
        this.welcomeMessageEn = wecolmeMessageEn;
    }

    @Min(0)
    @Max(100)
    @NotNull
    public double getLength() {
        return this.length;
    }

    public void setLength(final double length) {
        this.length = length;
    }


    @SafeHtml(whitelistType = WhiteListType.NONE)
    @NotBlank
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

}
