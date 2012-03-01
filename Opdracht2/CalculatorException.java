/**
 * An Exception thrown by Calculator instances to indicate .
 * 
 * @author Koos van Strien
 * @author Marten Lohstroh
 */
public class CalculatorException extends RuntimeException {

  /**
   * Default Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  public CalculatorException() {
    super();
  }

  public CalculatorException(String message) {
    super(message);
  }

  public CalculatorException(String message, Throwable cause) {
    super(message, cause);
  }

  public CalculatorException(Throwable cause) {
    super(cause);
  }

}