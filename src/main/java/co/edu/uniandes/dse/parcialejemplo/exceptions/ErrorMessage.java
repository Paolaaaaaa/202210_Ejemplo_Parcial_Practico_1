package co.edu.uniandes.dse.parcialejemplo.exceptions;

public final class ErrorMessage {
	public static final String MEDICO_NOT_FOUND ="No se ha encontrado el medico con el id";
	private ErrorMessage() 
	{
		throw new IllegalStateException("Utility class");
	}
}
