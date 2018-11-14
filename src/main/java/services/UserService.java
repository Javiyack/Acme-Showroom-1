package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.User;
import forms.UserForm;
import repositories.UserRepository;

@Service
@Transactional
public class UserService {
	// Repositories
	@Autowired
	private UserRepository userRepository;
	// Services
	@Autowired
	private ActorService actorService;

	@Autowired
	private Validator validator;

	// Constructor
	public UserService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// Create
	public User create() {

		final User result = new User();

		return result;
	}

	public User save(User user) {
		return userRepository.save(user);

	}

	public User findOne(int userId) {
		return userRepository.findOne(userId);
	}


	public User reconstruct(UserForm userForm, BindingResult binding) {
		User user;
		this.validator.validate(userForm, binding);
		user = (User) actorService.reconstruct(userForm, binding);
		if (!binding.hasErrors()) {
			
			user.setPhoto(userForm.getPhoto());
			user.setBirthdate(userForm.getBirthdate());
			user.setGenere(userForm.getGenere());

		}
		return user;
	}

}
