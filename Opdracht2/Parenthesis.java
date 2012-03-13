/**
 * Represents a parenthesis in a mathematical expression.
 * 
 * @author Marten Lohstroh
 * @author Stephan Schroevers
 */
public enum Parenthesis implements Token {

  LEFT_PARENTHESIS('('),
  RIGHT_PARENTHESIS(')');

  private char symbol;

  /**
   * Creates a <code>Parenthesis</code> instance for the given character.
   * 
   * @param paren
   *          the parenthesis to represent
   */
  private Parenthesis(char symbol) {
    this.symbol = symbol;
  }

  /**
   * Returns the <code>Parenthesis</code> instance represented by the given
   * string.
   * 
   * @param str
   *          the string for which an appropriate <code>Parenthesis</code>
   *          instance must be found
   * @return the appropriate token
   * @throws java.lang.IllegalArgumentException
   *           if <code>str</code> does not represent a parenthesis
   */
  static Parenthesis toParenthesis(String str) {
    for (Parenthesis p : Parenthesis.values()) {
      if (p.toString().equals(str)) {
        return p;
      }
    }

    throw new IllegalArgumentException(String.format(
        "Parenthesis '%s' unknown", str));
  }

  /**
   * Tells whether this instance represents a left parenthesis.
   * 
   * @return <code>true</code> if this is a left paren, <code>false</code>
   *         otherwise.
   */
  public boolean isLeft() {
    return this == Parenthesis.LEFT_PARENTHESIS;
  }

  /**
   * Tells whether this instance represents a right parenthesis.
   * 
   * @return <code>true</code> if this is a right paren, <code>false</code>
   *         otherwise.
   */
  public boolean isRight() {
    return this == Parenthesis.RIGHT_PARENTHESIS;
  }

  /**
   * Returns a string representation of the parenthesis.
   * 
   * @return the usual notation of the parenthesis
   */
  public String toString() {
    return String.valueOf(this.symbol);
  }

}
