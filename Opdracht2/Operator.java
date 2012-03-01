import java.lang.reflect.*;

/**
 * Represents an operator in a mathematical expression.
 * 
 * @author Marten Lohstroh
 * @author Stephan Schroevers
 */
public enum Operator implements Token {

  NEGATE("negate", 1, 8, Assoc.RIGHT_ASSOC),
  ADD("add", 2, 3, Assoc.ASSOCIATIVE),
  SUBTRACT("subtract", 2, 3, Assoc.LEFT_ASSOC),
  MULTIPLY("multiply", 2, 5, Assoc.ASSOCIATIVE),
  DIVIDE("divide", 2, 5, Assoc.LEFT_ASSOC),
  POWER("power", 2, 7, Assoc.RIGHT_ASSOC),
  FACTORIAL("factorial", 1, 9, Assoc.LEFT_ASSOC);

  /**
   * Name of the method that implements the operation.
   */
  private String name;

  /**
   * Number of arguments expected by this operator.
   */
  private int arity;

  /**
   * Operator precedence.
   */
  private int precedence;

  /**
   * The operator's associativity.
   */
  private Assoc assoc = Assoc.ASSOCIATIVE;

  /**
   * A <code>static double</code> method that takes as many <code>double</code>
   * arguments as this operator dictates and performs the actual operation.
   */
  protected Method action;

  /**
   * Enum for associativity properties.
   */
  public static enum Assoc {
    ASSOCIATIVE,
    LEFT_ASSOC,
    RIGHT_ASSOC;
  };

  /**
   * Creates an instance of the <code>Operator</code> class.
   * 
   * The argument <code>name</code> must be an accessible <code>static
   * double</code> method that takes as many <code>double</code> arguments as
   * the arity of the operator prescribes.
   * 
   * @param name
   *          function of appropriate arity that mimics this opertor's behavior
   * @param arity
   *          number of operands on which the operator works
   * @param precedence
   *          precedence of the operator
   * @param assoc
   *          associativity of the operator, either {@link #ASSOCIATIVE},
   *          {@link #LEFT_ASSOC} or {@link #RIGHT_ASSOC}
   */
  private Operator(String name, int arity, int precedence, Assoc assoc) {
    try {
      switch (arity) {
      case 1:
        this.action = this.getClass().getDeclaredMethod(name, double.class);
        break;
      case 2:
        this.action = this.getClass().getDeclaredMethod(name, double.class,
            double.class);
        break;
      default:
        throw new IllegalArgumentException(String.format(
            "Operator arity %d is not supported", arity));
      }
    } catch (SecurityException e) {
      throw new IllegalArgumentException(String.format(
          "Cannot use method '%s' with arity %d", name, arity));
    } catch (NoSuchMethodException e) {
      throw new IllegalArgumentException(String.format(
          "method '%s' with arity %d is not present", name, arity));
    }

    this.name = name;
    this.arity = arity;
    this.precedence = precedence;
    this.assoc = assoc;
  }

  /**
   * Returns the value that signifies the precedence of the operator.
   * 
   * @return the precedence value of this operator
   */
  public int getPrecedence() {
    return this.precedence;
  }

  /**
   * Returns the arity of the operator.
   * 
   * @return the arity of this operator
   */
  public int getArity() {
    return this.arity;
  }

  /**
   * Returns a method of appropriate arity which has behavior associated with
   * this operator.
   * 
   * @return the action that this operator represents
   */
  public Method getAction() {
    return this.action;
  }

  /**
   * Tells whether this operator is associative.
   * 
   * @return <code>true</code> if the operator is associative,
   *         <code>false</code> otherwise
   */
  public boolean isAssoc() {
    return this.assoc == Assoc.ASSOCIATIVE;
  }

  /**
   * Tells whether this operator is not associative, but is conventionally
   * evaluated from left to right.
   * 
   * @return <code>true</code> if the operator is left-associative,
   *         <code>false</code> otherwise
   */
  public boolean isLeftAssoc() {
    return this.assoc == Assoc.LEFT_ASSOC;
  }

  /**
   * Tells whether this operator is not associative, but is conventionally
   * evaluated from right to left.
   * 
   * @return <code>true</code> if the operator is right-associative,
   *         <code>false</code> otherwise
   */
  public boolean isRightAssoc() {
    return this.assoc == Assoc.RIGHT_ASSOC;
  }

  /**
   * Returns a string representation of the operator.
   * 
   * @return a description of the operator
   */
  public String toString() {
    return this.name;
  }

  /**
   * Performs negation on the provided argument.
   * 
   * This method implements the behavior of the <em>unary</em> <code>-</code>
   * operator.
   * 
   * @param o
   *          number to be negated
   * @return the negation of <code>o</code>
   */
  public static double negate(double o) {
    return -o;
  }

  /**
   * Performs addition with the provided arguments.
   * 
   * This method implements the behavior of the <code>+</code> operator.
   * 
   * @param o1
   *          first number
   * @param o2
   *          second number, to be added to the first
   * @return the sum of <code>o1</code> and <code>o2</code>
   */
  public static double add(double o1, double o2) {
    return o1 + o2;
  }

  /**
   * Subtracts the second argument from the first.
   * 
   * This method implements the behavior of the <em>binary</em> <code>-</code>
   * operator.
   * 
   * @param o1
   *          first number
   * @param o2
   *          second number, to be subtracted from the first
   * @return the difference between <code>o1</code> and <code>o2</code>
   */
  public static double subtract(double o1, double o2) {
    return o1 - o2;
  }

  /**
   * Performs multiplication with the provided arguments.
   * 
   * This method implements the behavior of the <code>*</code> operator.
   * 
   * @param o1
   *          first number
   * @param o2
   *          second number, to be multiplied with the first
   * @return the product of <code>o1</code> and <code>o2</code>
   */
  public static double multiply(double o1, double o2) {
    return o1 * o2;
  }

  /**
   * Divides the first argument by the second.
   * 
   * This method implements the behavior of the <code>/</code> operator.
   * 
   * @param o1
   *          first number, to be divided by the second
   * @param o2
   *          second number
   * @return the division of <code>o1</code> and <code>o2</code>
   */
  public static double divide(double o1, double o2) {
    return o1 / o2;
  }

  /**
   * Calculates the first operand to the power of the second operand.
   * 
   * @param o1
   *          first number
   * @param o2
   *          second number
   * @return <code>o1</code> to the <code>o2</code>th power
   */
  public static double power(double o1, double o2) {
    return Math.pow(o1, o2);
  }

  /**
   * Applies the factorial operator to the the given operand.
   * 
   * Note that this method will only accept natural numbers.
   * 
   * @param o
   *          number to apply to
   * @return the factorial of <code>o</code>
   */
  public static double factorial(double o) {
    long r = (long) o;

    if (r < 0 || o - r != 0.0) {
      throw new IllegalArgumentException(
          "Faculty only applies to natural numbers.");
    }

    for (int i = (r == 0 ? 1 : (int) r - 1); i > 1; i--) {
      r *= i;
    }

    return r;
  }

}
