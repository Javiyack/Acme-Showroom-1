
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Actor;
import domain.Administrator;
import forms.AdminForm;
import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AdministratorService {

    // Managed repositories ------------------------------------------------
    @Autowired
    private AdministratorRepository administratorRepository;

    // Supporting services ----------------------------------------------------
    @Autowired
    private ActorService actorService;
    @Autowired
    private Validator validator;


    // Constructor ----------------------------------------------------------
    public AdministratorService() {
        super();
    }

    // Methods CRUD ---------------------------------------------------------

    public Administrator findOne(final int administratorId) {
        Administrator result;

        result = this.administratorRepository.findOne(administratorId);
        Assert.notNull(result);

        return result;
    }

    public Administrator findByPrincipal() {
        Administrator result;
        UserAccount userAccount;

        userAccount = LoginService.getPrincipal();
        Assert.notNull(userAccount);
        result = (Administrator) this.actorService.findByUserAccount(userAccount);
        Assert.notNull(result);

        return result;
    }

    public Administrator create() {
        return new Administrator();

    }

    public Administrator reconstruct(final AdminForm adminForm, final BindingResult binding) {
        Administrator admin = null;
        Actor loggedActor = actorService.findByPrincipal();
        Assert.notNull(loggedActor, "msg.not.logged.block");
        Assert.isTrue(loggedActor instanceof Administrator, "msg.not.owned.block");

        this.validator.validate(adminForm, binding);
        admin = (Administrator) actorService.reconstruct(adminForm, binding);

        return admin;
    }

    public Double findAverageShowroomsPerUser() {
        return administratorRepository.findAverageShowroomsPerUser();
    }

    public Integer findMinimunShowroomsPerUser() {
        return administratorRepository.findMinimunShowroomsPerUser();
    }

    public Integer findMaximunShowroomsPerUser() {
        return administratorRepository.findMaximunShowroomsPerUser();
    }

    public Double findStdevShowroomsPerUser() {
        return administratorRepository.findStdevShowroomsPerUser();
    }

    public Double findAverageItemsPerUser() {
        return administratorRepository.findAverageItemsPerUser();
    }

    public Integer findMinimunItemsPerUser() {
        return administratorRepository.findMinimunItemsPerUser();
    }

    public Integer findMaximunItemsPerUser() {
        return administratorRepository.findMaximunItemsPerUser();
    }

    public Double findStdevItemsPerUser() {
        return administratorRepository.findStdevItemsPerUser();
    }

    public Double findAverageRequestsPerUser() {
        return administratorRepository.findAverageRequestsPerUser();
    }

    public Integer findMinimunRequestsPerUser() {
        return administratorRepository.findMinimunRequestsPerUser();
    }

    public Integer findMaximunRequestsPerUser() {
        return administratorRepository.findMaximunRequestsPerUser();
    }

    public Double findStdevRequestsPerUser() {
        return administratorRepository.findStdevRequestsPerUser();
    }

    public Double findAverageRejectedRequestsPerUser() {
        return administratorRepository.findAverageRejectedRequestsPerUser();
    }

    public Integer findMinimunRejectedRequestsPerUser() {
        return administratorRepository.findMinimunRejectedRequestsPerUser();
    }

    public Integer findMaximunRejectedRequestsPerUser() {
        return administratorRepository.findMaximunRejectedRequestsPerUser();
    }

    public Double findStdevRejectedRequestsPerUser() {
        return administratorRepository.findStdevRejectedRequestsPerUser();
    }

    public Double findAverageChirpsPerUser() {
        return administratorRepository.findAverageChirpsPerUser();
    }

    public Integer findMinimunChirpsPerUser() {
        return administratorRepository.findMinimunChirpsPerUser();
    }

    public Integer findMaximunChirpsPerUser() {
        return administratorRepository.findMaximunChirpsPerUser();
    }

    public Double findStdevChirpsPerUser() {
        return administratorRepository.findStdevChirpsPerUser();
    }


    public Double findAverageFollowersPerUser() {
        Double result = this.avg(actorService.findFollowersCountPerUser().values());
        return result;
    }


    public Integer findMinimunFollowersPerUser() {
        return this.min(actorService.findFollowersCountPerUser().values());

    }

    public Integer findMaximunFollowersPerUser() {

        return this.max(actorService.findFollowersCountPerUser().values());
    }

    public Double findStdevFollowersPerUser() {

        return this.stdev(actorService.findFollowersCountPerUser().values());
    }



    public Double findAverageFollowedsPerUser() {
        return administratorRepository.findAverageFollowsPerUser();
    }

    public Integer findMinimunFollowedsPerUser() {
        return administratorRepository.findMinimunFollowsPerUser();
    }

    public Integer findMaximunFollowedsPerUser() {
        return administratorRepository.findMaximunFollowsPerUser();
    }

    public Double findStdevFollowedsPerUser() {
        return administratorRepository.findStdevFollowsPerUser();
    }

    public Collection <Object> findChirpsNumberPerTopic() {
        return administratorRepository.findChirpsNumberPerTopic();
    }


    public Double findAverageCommentsPerShowroom() {
        return administratorRepository.findAverageCommentsPerShowroom();
    }

    public Integer findMinimunCommentsPerShowroom() {
        return administratorRepository.findMinimunCommentsPerShowroom();
    }

    public Integer findMaximunCommentsPerShowroom() {
        return administratorRepository.findMaximunCommentsPerShowroom();
    }

    public Double findStdevCommentsPerShowroom() {
        return administratorRepository.findStdevCommentsPerShowroom();
    }


    public Double findAverageCommentsPerItem() {
        return administratorRepository.findAverageCommentsPerItem();
    }

    public Integer findMinimunCommentsPerItem() {
        return administratorRepository.findMinimunCommentsPerItem();
    }

    public Integer findMaximunCommentsPerItem() {
        return administratorRepository.findMaximunCommentsPerItem();
    }

    public Double findStdevCommentsPerItem() {
        return administratorRepository.findStdevCommentsPerItem();
    }


    public Double findAverageCommentsPerUser() {
        return administratorRepository.findAverageCommentsPerUser();
    }

    public Integer findMinimunCommentsPerUser() {
        return administratorRepository.findMinimunCommentsPerUser();
    }

    public Integer findMaximunCommentsPerUser() {
        return administratorRepository.findMaximunCommentsPerUser();
    }

    public Double findStdevCommentsPerUser() {
        return administratorRepository.findStdevCommentsPerUser();
    }

    // Metodos auxiliares -----------------------------

    private Double avg(Collection <Integer> values) {
        Double result = 0.;
        if (!values.isEmpty()) {
            for (Integer value : values) {
                result += value;
            }
            result = result / values.size();
        }
        return result;
    }

    private Integer min(Collection <Integer> values) {
        Integer result = 0;
        if (!values.isEmpty()) {
            result = values.iterator().next();
            for (Integer value : values) {
                result = Math.min(result,value);
            }
        }
        return result;
    }

    private Integer max(Collection <Integer> values) {
        Integer result = 0;
        if (!values.isEmpty()) {
            result = values.iterator().next();
            for (Integer value : values) {
                result = Math.max(result,value);
            }
        }
        return result;
    }

    private Double stdev(Collection <Integer> values) {
    /*  Paso 1: calcular la media.
        Paso 2: calcular el cuadrado de la distancia a la media para cada dato.
        Paso 3: sumar los valores que resultaron del paso 2.
        Paso 4: dividir entre el número de datos.
        Paso 5: sacar la raíz cuadrada.*/
        Double result = 0.;
        Double avg = this.avg(values);
        int N = values.size();
        if (!values.isEmpty()) {
            for (Integer value : values) {
                result += (value - avg)*(value - avg);
            }
            result = result/N;
            result = Math.sqrt(result);
        }
        return result;
    }
}
