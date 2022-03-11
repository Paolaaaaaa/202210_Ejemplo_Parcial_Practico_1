package co.edu.uniandes.dse.parcialejemplo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import co.edu.uniandes.dse.parcialejemplo.dto.MedicoDTO;
import co.edu.uniandes.dse.parcialejemplo.dto.MedicoDetailDTO;
import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.services.MedicoService;

/**
 * Entidad 
 * @author paola andrea campiño
 * 
 * */
public class MedicoController {
	
	@RestController
	@RequestMapping("/Medicos")
	public class ProductController {
		@Autowired
		private MedicoService medicoService;
		@Autowired
		private ModelMapper modelMapper;
	/**
	 * Busca y devuelve todos los productos de la aplicación
	 * @return array de productos
	 * 
	 * */
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<MedicoDetailDTO> findAllProduct()
	{
		List<MedicoEntity> products = medicoService.getMedicos();
		return modelMapper.map(products, new TypeToken<List<MedicoDetailDTO>> () {
	}.getType());
	}

	/**
	 * 
	 * Busca un producto con un id especifico
	 * @param el id identificador del producto
	 * @return el libro buscado
	 * 
	 * */

	@GetMapping(value="/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public MedicoDetailDTO findOneProduct(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException
	{
		MedicoEntity productoEntity = medicoService.getMedico(id);
		return modelMapper.map(productoEntity, MedicoDetailDTO.class);

	}
	/**
	 * 
	 * Crea un nuevo producto con la información que se recibe en la petición
	 * @param producto- el producto a guardar
	 * @return el producto guardado 
	 * 
	 * */

	@GetMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public MedicoDTO createProducto(@RequestBody MedicoDTO medicoDTO)throws IllegalOperationException,EntityNotFoundException
	{
		MedicoEntity medicoEntity = medicoService.createMedico(modelMapper.map(medicoDTO, MedicoEntity.class));
		return modelMapper.map(medicoEntity, MedicoDTO.class);
	}
	/*
	 * 
	 * Actualiza la informacion de un producto en base a un id
	 * @param el id del libro a actualizar
	 * @param el libro que se guarda
	 * @return el libro que se guarda
	 * **/

	@PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public MedicoDTO updateProducto(@PathVariable("id") Long id, @RequestBody MedicoDTO medicoDTO) throws IllegalOperationException,EntityNotFoundException
	{
		MedicoEntity productEntity = medicoService.updateMedico(id, modelMapper.map(medicoDTO, MedicoEntity.class));
		return modelMapper.map(productEntity, MedicoDTO.class);

	}
	/**
	 * Borra el libro con el id asociado 
	 * @param id del producto a borrar
	 * 
	 * */

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException 
	{
		medicoService.deleteMedico(id);
	}
	
	}
}
