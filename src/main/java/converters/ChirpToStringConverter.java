
package converters;

import domain.Chirp;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ChirpToStringConverter implements Converter<Chirp, String> {

	@Override
	public String convert(final Chirp data) {
		String result;

		if (data == null)
			result = null;
		else
			result = String.valueOf(data.getId());

		return result;
	}

}
