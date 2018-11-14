package converters;

import domain.Showroom;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ShowroomToStringConverter implements Converter<Showroom, String>{
	
	@Override
	public String convert(final Showroom folder) {
		String result;

		if (folder == null)
			result = null;
		else
			result = String.valueOf(folder.getId());

		return result;
	}

}
