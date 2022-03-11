package co.edu.uniandes.dse.parcialejemplo.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Entidad 
 * @author paola andrea campiño
 * 
 * */

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(MedicoService.class)
public class MedicoServiceTests 
{
	@Autowired
	private MedicoService medicoService;
	@Autowired
	private TestEntityManager entityManager;
	
	private PodamFactory factory = new PodamFactoryImpl();
	
	private List<MedicoEntity> medicoList = new ArrayList<>();
	
	
	/**
	 * ANTES DE CADA PRUEBA
	 * */
	@BeforeEach
	void setUp() 
	{
		clearData();
		insertData();
	}
	
	private void clearData() 
	{
		entityManager.getEntityManager().createQuery("delete from MedicoEntity").executeUpdate();

	}
	private void insertData() 
	{for (int i = 0; i < 4;i++) 
	{
		MedicoEntity medico = factory.manufacturePojo(MedicoEntity.class);
		entityManager.persist(medico);
		medicoList.add(medico);

	
	}
		
	}
	/**
	 * 
	 * prueba crear un medico individual
	 * 
	 * */
	
	@Test
	void testCreateMedico() throws EntityNotFoundException, IllegalOperationException 
	{
		MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);

		MedicoEntity resultado = medicoService.createMedico(newEntity);
		assertNotNull(resultado);
		
		MedicoEntity entity = entityManager.find(MedicoEntity.class, resultado.getId());
		
		assertEquals(newEntity.getId(), entity.getId());
		assertEquals(newEntity.getNombre(),entity.getNombre());
		assertEquals(newEntity.getApellido(),entity.getApellido());
		assertEquals(newEntity.getRegistroMedico(),entity.getRegistroMedico());
		assertEquals(newEntity.getEspecialidad(),entity.getEspecialidad());


	}
	/**
	 * 
	 * Prueba crear un medico sin nombre
	 * */
	
	@Test
	void testNotNombre() 
	{
		assertThrows(IllegalOperationException.class, () -> {
			MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
			newEntity.setNombre(null);
			medicoService.createMedico(newEntity);
		});
	}
	
	/**
	 * 
	 * Prueba crear un medico sin apellido
	 * */
	
	@Test
	void testNotApellido() 
	{
		assertThrows(IllegalOperationException.class, () -> {
			MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
			newEntity.setApellido(null);
			medicoService.createMedico(newEntity);
		});
	}
	/**
	 * 
	 * Prueba crear un medico sin registro
	 * */
	
	@Test
	void testNotRegistroMedico() 
	{
		assertThrows(IllegalOperationException.class, () -> {
			MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
			newEntity.setRegistroMedico(null);
			medicoService.createMedico(newEntity);
		});
	}
	
	/**
	 * 
	 * Prueba crear un medico sin especialidad
	 * */
	
	@Test
	void testNotEspecialidad() 
	{
		assertThrows(IllegalOperationException.class, () -> {
			MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
			newEntity.setEspecialidad(null);
			medicoService.createMedico(newEntity);
		});
	}
	

	/**
	 * Prueba para consultar la lista de medicos.
	 */
	@Test
	void testGetMedicos() {
		List<MedicoEntity> medicosListcmp = medicoService.getMedicos();
		assertEquals(medicosListcmp.size(), medicoList.size());

		for (MedicoEntity entity : medicosListcmp) {
			boolean found = false;
			for (MedicoEntity storedEntity : medicosListcmp) {
				if (entity.getId().equals(storedEntity.getId())) {
					found = true;
				}
			}
			assertTrue(found);
		}
	}
	
	
	/*
	 * Prueba para consultar un medico.
	 */
	@Test
	void testGetMedico() throws EntityNotFoundException,IllegalOperationException {
		MedicoEntity entity = medicoList.get(0);
		MedicoEntity resultEntity = medicoService.getMedico(entity.getId());
		assertNotNull(resultEntity);
		assertEquals(entity.getId(), resultEntity.getId());
		assertEquals(entity.getNombre(), resultEntity.getNombre());
		assertEquals(entity.getApellido(),resultEntity.getApellido());
		assertEquals(entity.getRegistroMedico(),resultEntity.getRegistroMedico());
		assertEquals(entity.getEspecialidad(),resultEntity.getEspecialidad());

	}
	

	/**
	 * Prueba para actualizar un medico.
	 */

	@Test
	void testUpdateProducto() throws EntityNotFoundException, IllegalOperationException {
		MedicoEntity entity = medicoList.get(0);
		MedicoEntity pojoEntity = factory.manufacturePojo(MedicoEntity.class);
		pojoEntity.setId(entity.getId());
		medicoService.updateMedico(entity.getId(), pojoEntity);

		MedicoEntity resp = entityManager.find(MedicoEntity.class, entity.getId());
		assertEquals(resp.getId(), pojoEntity.getId());
		assertEquals(resp.getNombre(), pojoEntity.getNombre());
		assertEquals(resp.getApellido(),pojoEntity.getApellido());
		assertEquals(resp.getRegistroMedico(),pojoEntity.getRegistroMedico());
		assertEquals(resp.getEspecialidad(),pojoEntity.getEspecialidad());
}


	
	
	/**
	 * Prueba para consultar un medico que no existe.
	 */
	@Test
	void testGetInvalidProducto() 
	{
		assertThrows(EntityNotFoundException.class,()->{
			medicoService.getMedico(0L);
		});
	}
	
	/**
	 * Prueba para eliminar un medico.
	 */
	@Test
	void testDeleteProducto() throws EntityNotFoundException, IllegalOperationException {
		MedicoEntity entity = medicoList.get(1);
		medicoService.deleteMedico(entity.getId());
		MedicoEntity deleted = entityManager.find(MedicoEntity.class, entity.getId());
		assertNull(deleted);
	}
	
	/**
	 * Prueba para eliminar un medico que no existe.
	 */
	@Test
	void testDeleteInvalidProducto() {
		assertThrows(EntityNotFoundException.class, ()->{
			medicoService.deleteMedico(0L);
		});
	}
}
