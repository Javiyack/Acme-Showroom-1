package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Agent;
import forms.AgentForm;
import repositories.AgentRepository;

@Service
@Transactional
public class AgentService {
    //Repositories
    @Autowired
    private AgentRepository agentRepository;
    //Services
    @Autowired
    private ActorService actorService;

	@Autowired
	private Validator validator;
	
	//Constructor
    public AgentService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    //Create
    public Agent create() {

        final Agent result = new Agent();


        return result;
    }

    public Agent save(Agent agent) {
        return agentRepository.save(agent);

    }

    public Collection<Agent> findAll() {
        return agentRepository.findAll();
    }

    public Collection<Agent> findAllActive() {
        return agentRepository.findAllActive();
    }

	public Agent findOne(int id){
		Agent result = agentRepository.findOne(id);
		return result;
	}


	public Agent reconstruct(AgentForm agentForm, BindingResult binding) {
		Agent agent = null;
		this.validator.validate(agentForm, binding);
		agent = (Agent) actorService.reconstruct(agentForm, binding);
		if (!binding.hasErrors()) {			
			agent.setCompany(agentForm.getCompany());
		}
		return agent;
	}
}
