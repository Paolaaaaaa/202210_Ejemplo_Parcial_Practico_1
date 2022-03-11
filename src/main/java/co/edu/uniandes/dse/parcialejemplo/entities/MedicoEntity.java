package co.edu.uniandes.dse.parcialejemplo.entities;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
/**
 * Entidad 
 * @author paola andrea campiño
 * 
 * */

public class MedicoEntity extends BaseEntity 
{
	
	private String nombre;
	private String apellido;
	private String registroMedico;
	private String especialidad;

}
