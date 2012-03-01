/**
 * Represents a token in a mathematical expression.
 * 
 * @author Marten Lohstroh
 */
public interface Token {

  /**
   * Returns a string representation of the token, as it could occur in an
   * expression.
   * 
   * @return an appropriate string representation of the token
   */
  public String toString();

}