
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Agent;
import repositories.AgentRepository;

@Component
@Transactional
public class StringToAgentConverter implements Converter<String, Agent> {

	@Autowired
	private AgentRepository	agentRepository;


	@Override
	public Agent convert(final String str) {
		Agent result;
		Integer id;
		try {
			if (StringUtils.isEmpty(str))
				result = null;
			else {
				id = Integer.valueOf(str);
				result = this.agentRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
