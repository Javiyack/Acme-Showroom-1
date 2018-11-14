
package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import domain.Agent;
import security.Authority;

public class AgentForm extends ActorForm{

	private String 		company;		
	

	// Constructors -------------------------

	public AgentForm() {
		super();

		this.getAccount().setAuthority(Authority.AGENT);
	}

	public AgentForm(final Agent agent) {
		super(agent);
		this.setCompany(agent.getCompany());

	}


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
}
