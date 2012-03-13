/**
 * Represents an atomic numerical operand in a mathematical expression.
 * 
 * @author Marten Lohstroh
 */
public class Operand implements Token {

  /**
   * The value represented by this object.
   */
  private double value;

  /**
   * Instantiates an operand with the given value.
   * 
   * @param value
   *          the value that this operand should represent
   */
  public Operand(double value) {
    this.value = value;
  }

  /**
   * Returns the value represented by this operand.
   * 
   * @return the operand, as a <code>double</code>
   */
  public double getValue() {
    return value;
  }

  /**
   * Returns a string representation of the operand.
   * 
   * @return a commond notation of the operand
   */
  public String toString() {
    return String.valueOf(value);
  }

}
