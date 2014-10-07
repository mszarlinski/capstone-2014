package pl.coursera.mszarlinski.symptoms.rest.converter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.coursera.mszarlinski.symptoms.domain.CheckInTemplate;
import pl.coursera.mszarlinski.symptoms.rest.resource.CheckIn;

import com.google.common.collect.Lists;

/**
 * 
 * @author Maciej
 *
 */
@Component
public class CheckInTemplateConverter extends Converter<CheckInTemplate, CheckIn> {

	@Autowired
	private MedicationConverter medicationConverter;

	@Override
	public CheckIn convertToResource(CheckInTemplate entity, Map<String, Object> params) {
		CheckIn checkIn = new CheckIn();
		checkIn.medications = Lists.newArrayList(medicationConverter.convertToResources(entity.medications, params));
		return checkIn;
	}

	@Override
	public CheckInTemplate convertToEntity(CheckIn resource, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

}
