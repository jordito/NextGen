package local.NextGen.exceptions;

/**
 * Clase CustomException que extiende de Exception.
 * Esta es una excepción personalizada utilizada para manejar situaciones específicas en la aplicación.
 * Puede ser lanzada para indicar una condición de error específica relacionada con las operaciones de la aplicación.
 */
public class CustomException extends Exception {

    /**
     * Constructor que crea una nueva CustomException con un mensaje específico.
     * Este mensaje puede ser utilizado para proporcionar información detallada sobre el error ocurrido.
     *
     * @param mensaje El mensaje detallado que describe la excepción.
     */
    public CustomException(String mensaje) {
        super(mensaje);
    }

}
