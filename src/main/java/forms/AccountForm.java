
package forms;

import domain.Actor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import javax.validation.constraints.Size;

public class AccountForm {

    private String username;
    private String password;
    private String newPassword;
    private String confirmPassword;
    private String authority;


    //Constructors -------------------------

    public AccountForm() {
        super();
    }

    public AccountForm(final Actor actor) {
        super();
        this.setUsername(actor.getUserAccount().getUsername());
        this.setAuthority(actor.getUserAccount().getAuthorities().iterator().next().getAuthority());
    }


    @NotBlank
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(final String authority) {
        this.authority = authority;
    }


    @SafeHtml(whitelistType = WhiteListType.NONE)
    @NotBlank
    @Size(min = 5, max = 32)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String userName) {
        this.username = userName;
    }

    @SafeHtml(whitelistType = WhiteListType.NONE)
    @NotBlank
    @Size(min = 5, max = 32)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String pasword) {
        this.password = pasword;
    }

    @SafeHtml(whitelistType = WhiteListType.NONE)
    @Size(min = 5, max = 32)
    public String getNewPassword() {
        return this.newPassword;
    }

    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }

    @SafeHtml(whitelistType = WhiteListType.NONE)
    @Size(min = 5, max = 32)
    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(final String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


}
