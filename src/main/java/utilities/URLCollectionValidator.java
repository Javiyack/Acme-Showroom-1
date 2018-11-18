package utilities;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.internal.constraintvalidators.URLValidator;
import org.hibernate.validator.internal.util.annotationfactory.AnnotationDescriptor;
import org.hibernate.validator.internal.util.annotationfactory.AnnotationFactory;

public class URLCollectionValidator implements ConstraintValidator<URLCollection, Collection<String>> {

    @Override
    public void initialize(URLCollection constraintAnnotation) {

    }

    @Override
    public boolean isValid(Collection<String> value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        
        AnnotationDescriptor<URL> descriptor = new AnnotationDescriptor<URL>( URL.class ); 
        URL url = AnnotationFactory.create(descriptor);
        URLValidator urlValidator = new URLValidator();
        urlValidator.initialize(url);
        
        for (String s : value) {     
            if (!urlValidator.isValid(s, context)) {
                return false;
            }
        }
        return true;
    }

}