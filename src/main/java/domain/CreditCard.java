package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Embeddable
@Access(AccessType.PROPERTY)
public class CreditCard {

    private String holderName;

    private String brandName;

    private String cardNumber;

    private String expirationMonth;

    private String expirationYear;

    private String CVV;

    public static final String	VISA			= "VISA";
    public static final String	MASTERCARD	= "MASTER-CARD";
    public static final String	DINERS	= "DINERS";
    public static final String	AMEX	= "AMEX";


    @NotBlank
    @SafeHtml
    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    @NotBlank
    @SafeHtml
    @Pattern(regexp = "^(VISA|MASTER-CARD|DINERS|AMEX)$")
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Pattern(regexp = "^(?=.*\\d).{1,64}$")
    @CreditCardNumber
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String number) {
        this.cardNumber = number;
    }

    @NotBlank
    @SafeHtml
    @Pattern(regexp = "^([0][1-9]|[1][0-2])$")
    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    @NotBlank
    @SafeHtml
    @Pattern(regexp = "^([0][1-9]|[1-9][0-9])$")
    public String getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }

    @NotBlank
    @SafeHtml
    @Pattern(regexp = "^\\d{3}$")
    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }
}
