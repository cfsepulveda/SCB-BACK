package com.artsoft.scb.model.bll;

import java.util.Hashtable;
import java.util.Set;

import javax.swing.plaf.basic.BasicTreeUI.TreeHomeAction;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artsoft.scb.model.bll.interfaces.IConvocatoryService;
import com.artsoft.scb.model.dao.ConvocatoryRepository;
import com.artsoft.scb.model.dao.ConvocatoryStateRepository;
import com.artsoft.scb.model.dao.ConvocatoryTypeRepository;
import com.artsoft.scb.model.dao.OffererRepository;
import com.artsoft.scb.model.entity.Convocatory;
import com.artsoft.scb.model.entity.ConvocatoryType;
import com.artsoft.scb.model.entity.Offerer;


@Service
public class ConvocatoryService extends ExceptionService implements IConvocatoryService{

	@Autowired
	private ConvocatoryStateRepository convocatoryStateRepository;
	
	@Autowired
	private ConvocatoryTypeRepository convocatoryTypeRepository;
	
	@Autowired
	private ConvocatoryRepository convocatoryRepository;
	
	@Autowired
	private OffererRepository offererRepository;
	
	
	private final int ID_CREADA = 1;
	private final int ID_CERRADA = 2;
	private final int ID_ABIERTA = 3;
	private final int ID_PUBLICADA = 4;
	
	@Override
	public boolean createConvocatory(Convocatory convocatory) throws Exception {
		validateConvocatory(convocatory);
		convocatory.setConvocatoryState(convocatoryStateRepository.findById(ID_CREADA));
		Offerer offerer = offererRepository.findByEmail(convocatory.getOfferer().getEmail());
		convocatory.setOfferer(offerer);
		Convocatory convocatorySaved = convocatoryRepository.save(convocatory);		
		if(convocatorySaved == null){
			return false;
		}
		
		return true;
	}
	
	private void validateConvocatory(Convocatory convocatory) throws Exception{
		Hashtable<String, String> parameters = new Hashtable<>();
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(convocatory);
		
		if (constraintViolations.size() > 0) {
			for (ConstraintViolation<Object> constraintViolation : constraintViolations){
				parameters.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());				
			}			
		}
		
		if (parameters.values().size() > 0){
			throwException(parameters);
		}
		
		validateMail(convocatory.getOfferer().getEmail());
		validateConvocatoryType(convocatory.getConvocatoryType().getId());
		validateNumberOfBeneficiaries(convocatory);
		validateEmptyName(convocatory);
		validateEmptyDescription(convocatory);
	}
	
	private void validateMail(String email) throws Exception{
		Offerer offererFound = offererRepository.findByEmail(email);
		
		if(offererFound == null){
			throwException("email", "No existe un oferente asociado a ese correo");
		}
	}
	
	private void validateConvocatoryType(int id) throws Exception{
		ConvocatoryType convocatoryTypeFound = convocatoryTypeRepository.findById(id);
		
		if(convocatoryTypeFound == null){
			throwException("tipoConvocatoria", "El tipo de convocatoria ingresado no existe");
		}
	}
	
	private void validateNumberOfBeneficiaries(Convocatory convocatory) throws Exception{
		if(convocatory.getNumberBeneficiaries() <= 0){
			throwException("numeroBeneficiarios", "El número de beneficiarios es inválido, debe ser mayor a cero");
		}
	}
	
	private void validateEmptyName(Convocatory convocatory) throws Exception{
		if(convocatory.getName().isEmpty() || convocatory.getName() == null){
			throwException("name", "El nombre es obligatorio");
		}
	}
	
	private void validateEmptyDescription(Convocatory convocatory) throws Exception{
		if(convocatory.getDescription().isEmpty() || convocatory.getDescription() == null){
			throwException("description", "La descripción es obligatoria");
		}
	}

}
