package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Item;
import repositories.ItemRepository;

@Component
@Transactional
public class StringToItemConverter implements Converter<String, Item> {
	@Autowired
	ItemRepository repository;


	@Override
	public Item convert(final String text) {
		Item result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {			
				id = Integer.valueOf(text);
				result = this.repository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
