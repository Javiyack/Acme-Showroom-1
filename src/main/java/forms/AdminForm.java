
package forms;

import domain.Actor;
import security.Authority;

public class AdminForm extends ActorForm{


	// Constructors -------------------------

	public AdminForm() {
		super();
		this.getAccount().setAuthority(Authority.ADMINISTRATOR);
	}

	public AdminForm(final Actor actor) {
		super(actor);

		this.getAccount().setAuthority(Authority.ADMINISTRATOR);
	}

}
