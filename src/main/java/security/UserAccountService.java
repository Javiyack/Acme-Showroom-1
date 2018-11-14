
package security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserAccountService {

	@Autowired
	private UserAccountRepository	userAccountRepository;


	public UserAccountService() {
		super();
	}
	
	public UserAccount create(String authorityName) {
		UserAccount res;
		res = new UserAccount();
		final Authority authority = new Authority();
		final List<Authority> authorities = new ArrayList<Authority>();
		authority.setAuthority(authorityName);
		authorities.add(authority);
		res.setAuthorities(authorities);
		res.setActive(true);
		return res;
	}

	public UserAccount findOne(final int userAccountId) {
		return this.userAccountRepository.findOne(userAccountId);
	}

	public UserAccount save(final UserAccount userAccount) {
		return this.userAccountRepository.save(userAccount);
	}
	public void delete(final UserAccount userAccount) {
		this.userAccountRepository.delete(userAccount);
	}
}
