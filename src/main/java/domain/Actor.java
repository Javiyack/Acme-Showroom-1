
package domain;

import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {

	//Attributes
	private String 		name;
	private String 		surname;
	private String 		email;
	private String 		address;
	private String 		phone;
	private Set<String> topics;
	//Relationships
	private UserAccount userAccount;
	private Set<Actor> follows;

	@SafeHtml
	@NotBlank
	@Size(max = 32)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@SafeHtml
	@NotBlank
	@Size(max = 32)
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	//@Pattern(regexp = "[0-9+()]{4,32}")
	@NotBlank
	@Size(min = 4,max = 32)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}


	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}


	@SafeHtml
	@NotBlank
	@Size(max = 32)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Valid
	@ManyToMany()
	public Set <Actor> getFollows() {
		return follows;
	}

	public void setFollows(Set <Actor> follows) {
		this.follows = follows;
	}

	@ElementCollection
	@NotNull
	public Set <String> getTopics() {
		return topics;
	}

	public void setTopics(Set <String> topics) {
		this.topics = topics;
	}
}
