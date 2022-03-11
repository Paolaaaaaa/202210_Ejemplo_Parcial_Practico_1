package co.edu.uniandes.dse.parcialejemplo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.MedicoRepository;
import co.edu.uniandes.dse.parcialejemplo.exceptions.ErrorMessage;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
/**
 * Entidad 
 * @author paola andrea campiño
 * 
 * */
public class MedicoService 
{

	
	@Autowired
	MedicoRepository medicoRepository;
	
	
	/**
	 * Implementación creación de una entity medico 
	 * @param MedicoEntity
	 * @return objeto medico Entity
	 * 
	 * no se permite la creación de esta entity si alguno de los campos en nulo
	 * 
	 * */
	
	@Transactional 
	public MedicoEntity createMedico(MedicoEntity medico) throws EntityNotFoundException,IllegalOperationException
	{
		log.info("Creación de un producto");
		if (medico.getNombre()==null) 
		{
			throw new IllegalOperationException("El nombre no puede ser nulo");
		}
		
		if (medico.getApellido()==null) 
		{
			throw new IllegalOperationException("El apellido no puede ser nulo");
		}
		if (medico.getRegistroMedico()==null) 
		{
			throw new IllegalOperationException("El registro no puede ser nulo");
		}
		if (medico.getEspecialidad()==null) 
		{
			throw new IllegalOperationException("La especialidad no puede ser nulo");
		}
		return medicoRepository.save(medico);

		
		
		
	}
	/**
	 * Implementación consulta todos los medicos
	 * @return lista con todos los medicos
	 * 
	 * 
	 * 
	 * */
	
	@Transactional 
	public List<MedicoEntity>getMedicos()
	{
		log.info("Iniciar consulta todos los medicos");
		return medicoRepository.findAll();
	}
	/**
	 * Implementación consulta medico en base a un id
	 * @param id del medico
	 * @return MedicoEntity
	 * 
	 * EntityNotFOund cuando no se ha encontrado un medico con el id
	 * */
	
	@Transactional 
	public MedicoEntity getMedico(Long idMedico)throws IllegalOperationException,EntityNotFoundException
	{
		Optional<MedicoEntity> medicoEntity = medicoRepository.findById(idMedico);
		if (medicoEntity.isEmpty())
		{
			throw new EntityNotFoundException(ErrorMessage.MEDICO_NOT_FOUND);

		}
		return medicoRepository.getById(idMedico);

		
	}
	
	/**
	 * Implementación actualizar medico
	 * @param id del medico
	 * @return la entidad actualizada
	 * @throws Entity not found exepction cuando no se ha encontrado el medico
	 * 
	 * */
	@Transactional
	public MedicoEntity updateMedico(Long idMedico,MedicoEntity medico) throws EntityNotFoundException
	{
		Optional<MedicoEntity> medicoEntity = medicoRepository.findById(idMedico);
		if (medicoEntity.isEmpty())
		{
			throw new EntityNotFoundException(ErrorMessage.MEDICO_NOT_FOUND);

		}
		return medicoRepository.save(medico);
		
	}
	/**
	 * Implementación delete medico
	 * @param id del medico
	 * @throws Entity not found exepction cuando no se ha encontrado el medico
	 * 
	 * */
	
	@Transactional
	public void deleteMedico (Long idMedico)throws EntityNotFoundException
	{
		Optional<MedicoEntity> medicoEntity = medicoRepository.findById(idMedico);
		if (medicoEntity.isEmpty())
		{
			throw new EntityNotFoundException(ErrorMessage.MEDICO_NOT_FOUND);

		}
		medicoRepository.deleteById(idMedico);
	}
	
	
	
}
