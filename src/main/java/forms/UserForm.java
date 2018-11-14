
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import domain.User;
import security.Authority;

public class UserForm extends ActorForm{

	private String 		photo;
	private Date 		birthdate; 			
	private String 		genere; 			
	

	// Constructors -------------------------

	public UserForm() {
		super();
		this.getAccount().setAuthority(Authority.USER);
	}

	public UserForm(final User user) {
		super(user);
		this.setPhoto(user.getPhoto());
		this.setBirthdate(user.getBirthdate());
		this.setGenere(user.getGenere());

	}

	@Past
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date bithdate) {
		this.birthdate = bithdate;
	}


	@Pattern(regexp = "MALE|FEMALE|UNDEFINED")
	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}
	
	@NotBlank
	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
