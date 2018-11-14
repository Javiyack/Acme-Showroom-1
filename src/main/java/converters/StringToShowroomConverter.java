package converters;

import domain.Showroom;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import repositories.ShowroomRepository;

@Component
@Transactional
public class StringToShowroomConverter implements Converter<String, Showroom> {
	@Autowired
	ShowroomRepository showroomRepository;


	@Override
	public Showroom convert(final String text) {
		Showroom result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {			
				id = Integer.valueOf(text);
				result = this.showroomRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
