import java.io.*;
import java.lang.reflect.*;
import java.util.*;

/**
 * The general outline of a Calculator.
 * 
 * @author Marten Lohstroh
 * @author Stephan Schroevers
 */
public abstract class Calculator {

  /**
   * Usage message to be displayed in case of invalid arguments.
   */
  public static final String USAGE_MSG = "Usage:\n    "
      + "java Assignment2 [-infix | [-postfix | -rpn] | -benchmark]";

  /**
   * Default command line prompt.
   */
  public static final String PROMPT_STRING = ">> ";

  /*
   * Maximum recursion depth allowed during the generation of a random
   * expression.
   */
  public static final int MAX_RAND_EXPR_DEPTH = 3;

  public static final int GOLD_STANDARD_EXPR = 1024;

  /**
   * Enumeration for notation type.
   */
  public static enum Notation {
    /**
     * Infix notation.
     */
    INFIX("Infix"),
    /**
     * Postfix notation (also RPN, Reverse Polish Notation).
     */
    POSTFIX("Postfix");

    private String name;

    private Notation(String name) {
      this.name = name;
    }

    public String toString() {
      return this.name;
    }
  };

  /**
   * Flag of default notation.
   */
  public static final Notation DEFAULT_NOTATION = Notation.INFIX;

  public Calculator() {
  }

  /**
   * Inspects the given string and returns an appropriate instance of a subclass
   * of {@link src.ass2.Token}.
   * 
   * @param tok
   *          the token to evaluate.
   * @return <code>Token</code> representation of <code>tok</code>.
   */
  protected abstract Token toToken(String tok);

  /**
   * Returns the sequence of tokens in the given expression as a
   * {@link src.ass2.TokenQueue}.
   * 
   * The expression is evaluated according to the supplied notation type.
   * 
   * @param expr
   *          the expression the parse
   * @param notationType
   *          the type of syntax used by the expression
   * @return a queue containing all tokens, in left-to-rght order
   * @throws src.ass2.CalculatorException
   *           if the expression is syntactically incorrect
   */
  protected abstract TokenQueue parseExpr(String expr, Notation notationType);

  /**
   * Evaluates the given expression, assuming the specified notation format.
   * 
   * @param expr
   *          the expression to evaluate
   * @param notationType
   *          the type of syntax used by the expression that must be evaluated
   * @return the result of the calculation
   * @throws java.lang.IllegalArgumentException
   *           if <code>notationType</code> is unknown
   */
  public abstract double calc(TokenQueue expr, Notation notationType);

  /**
   * Evaluate the given expression as if it were in infix notation.
   * 
   * This method applies Dijkstra's Shunting Yard algorithm as a first step,
   * thus converting the infix expression to an equivalent RPN expression. The
   * RPN expression is then evaluated using {@link #calcPostfix}.
   * 
   * @param expr
   *          the expression to evaluate
   * @return the result of the calculation
   */
  public abstract double calcInfix(TokenQueue expr);

  /**
   * Evaluate the given expression as if it were in RPN notation.
   * 
   * @param expr
   *          the expression to evaluate
   * @return the result of the calculation
   * @throws src.ass2.CalculatorException
   *           if the expression is syntactically incorrect
   */
  public abstract double calcPostfix(TokenQueue expr);

  /**
   * Perform the operation represented by the given operator on the top operands
   * of the given stack.
   * 
   * The result of the operation is pushed back on the stack.
   * 
   * @param op
   *          the operation to perform
   * @param stack
   *          the stack from which to pop the operands and onto which the result
   *          will be pushed
   */
  public void apply(Operator op, TokenStack stack) {
    Object[] s = new Object[op.getArity()];

    if (stack.size() < op.getArity()) {
      throw new CalculatorException(String.format(
          "not enough operands for operator '%s'", op.toString()));
    }

    /* Pop all operands from the stack, and store them in reverse order. */
    for (int i = op.getArity() - 1; i >= 0; i--) {
      s[i] = ((Operand) stack.pop()).getValue();
    }

    try {
      /* Apply the operator to the operands. */
      stack.push(new Operand(((Double) op.getAction().invoke(null, s))
          .doubleValue()));
    } catch (IllegalAccessException ex) {
      /* This really shouldn't happen. */
      ex.printStackTrace();
    } catch (InvocationTargetException ex) {
      /* The operator may throw an Exception. Pass it on. */
      throw new CalculatorException(ex.getCause().getMessage());
    }
  }

  /**
   * Show command prompt and evaluate expressions.
   * 
   * The prompt will keep on returning, until either EOF from stdin is received,
   * or an exception is thrown.
   * 
   * @param notationType
   *          the type of syntax expected on the command line
   */
  private void prompt(Notation notationType) {
    String input = null;
    Scanner stdin = new Scanner(System.in);

    /*
     * Evaluate each line after 'return' has been pressed, print the output, and
     * show the prompt again.
     */
    System.out.print(PROMPT_STRING);
    while (stdin.hasNextLine()) {
      try {
        input = stdin.nextLine();

        /* The special command 'rand' generates a random expression. */
        if (input.trim().equals("rand")) {
          input = ExpressionGenerator.generateExpr(MAX_RAND_EXPR_DEPTH,
              notationType)[0];
          System.out.printf("Evaluating random expression '%s'...\n", input);
        }

        System.out.println(calc(parseExpr(input, notationType), notationType));
      } catch (CalculatorException ex) {
        System.out.printf("Error: %s\n", ex.getMessage());
      }

      System.out.print(PROMPT_STRING);
    }
  }

  /**
   * Creates a benchmark based on this <code>Calculator</code> instance.
   * 
   * A gold standard file containing expression/outcome pairs is written to a
   * file, after verification of the outcomes by an external program.
   * 
   * Exits with an appropriate error message in case of an error related to the
   * writing of benchmark data.
   * 
   * @param notationType
   *          the notation type for which a gold standard is to be created
   * @throws java.lang.IllegalArgumentException
   *           if no benchmark can be created for the given notation type
   */
  private void generateGoldStandard(Notation notationType) {
    String[] expr = null;
    String answ = null;
    String file = String.format("%s%s%s", "GoldStandard", notationType
        .toString(), ".properties");
    Properties p = new Properties();
    FileOutputStream out = null;

    /* Generate the required number of expression/outcome pairs. */
    while (p.size() < GOLD_STANDARD_EXPR) {
      /*
       * Get a random expression (expr[0]) together with the outcome as reported
       * by an external program (expr[1]). expr[1] may also contain an error
       * message.
       */
      expr = ExpressionGenerator.generateExpr(MAX_RAND_EXPR_DEPTH,
          notationType);

      try {
        /* Evaluate the given expression with our own calcualtor. */
        answ = Double.toString(calc(parseExpr(expr[0], notationType),
            notationType));

        /*
         * The answers may not match (e.g. because of a difference in precision,
         * or an overflow. Answers to these questions cannot be verified, so we
         * ignore them.
         */
        if (!expr[1].equals(answ)) {
          // System.out.printf("(1) MISMATCH: %s = %s != %s\n",
          // expr[0], expr[1], answ);
          continue;
        }

        p.setProperty(expr[0], answ);
      } catch (CalculatorException ex) {
        /*
         * FIXME: to be honest, there may or may not be a good reason for this
         * situation to occur. Fact is, it does occur. We ignore the
         * corresponding expression.
         */
        if (!expr[1].startsWith("NULL")) {
          // System.out.printf("(2) MISMATCH: %s = %s != %s\n",
          // expr[0], expr[1], answ);
          continue;
        }

        /*
         * Register that the calculatorshould throw an exception on this
         * expression.
         */
        p.setProperty(expr[0], "NULL");
      }
    }

    /* Write the gold standard to file. */
    try {
      out = new FileOutputStream(file);
      p.store(out, "");
      out.close();
    } catch (FileNotFoundException ex) {
      errorExit(String.format("Cannot open file '%s' for writing: %s",
          new File(file).getAbsolutePath(), ex.getMessage()));
    } catch (IOException ex) {
      errorExit(String.format("Error while writing to file '%s': %s.",
          new File(file).getAbsolutePath(), ex.getMessage()));
    }
  }

  /**
   * Performs a benchmark on this <code>Calculator</code> instance.
   * 
   * A gold standard file containing expression/outcome pairs is read and
   * compared with the output of this calculator for the same expressions. The
   * percentage of correct outcomes is reported.
   * 
   * Exits with an appropriate error message in case of an error related to the
   * reading of benchmark data.
   * 
   * @param notationType
   *          the notation type that is to be tested
   * @throws java.lang.IllegalArgumentException
   *           if no benchmark is implemented for the given notation type
   */
  private void benchmark(Notation notationType) {
    int correct = 0;
    Properties p = new Properties();
    String expr = null, answ = null, studAnsw = null;
    String file = String.format("%s%s%s", "GoldStandard", notationType
        .toString(), ".properties");
    Enumeration<?> exprs = null;
    FileInputStream in = null;

    /* Read the gold standard file. */
    try {
      in = new FileInputStream(file);
      p.load(in);
      in.close();
    } catch (FileNotFoundException ex) {
      errorExit(String.format("Cannot open file '%s' for reading: %s",
          new File(file).getAbsolutePath(), ex.getMessage()));
    } catch (IOException ex) {
      errorExit(String.format("Error while reading from file '%s': %s.",
          new File(file).getAbsolutePath(), ex.getMessage()));
    } catch (IllegalArgumentException ex) {
      errorExit(String.format("Error while reading from file '%s': %s.",
          new File(file).getAbsolutePath(), ex.getMessage()));
    }

    /*
     * Evaluate each of the expressions in the gold standard, and compare each
     * result to the (supposedly) correct outcome for the given expression.
     */
    exprs = p.propertyNames();
    while (exprs.hasMoreElements()) {
      expr = exprs.nextElement().toString();
      answ = p.getProperty(expr);

      try {
        studAnsw = Double.toString(calc(parseExpr(expr, notationType),
            notationType));
      } catch (CalculatorException ex) {
        /*
         * Some expressions are not supported (e.g. the factorial of a negative
         * number). The `outcomes' of such an expression is denoted NULL.
         */
        studAnsw = "NULL";
      }

      /* Test the current expression and report on the result. */
      if (!studAnsw.equals(answ)) {
        System.out.printf("(FAIL) %s = %s != %s\n", expr, answ, studAnsw);
      } else {
        System.out.println(String.format("(PASS) %s", expr));
        correct++;
      }
    }

    /* Print a summary. */
    System.out.printf("%s benchmark result: %d/%d (%.2f %%) correct\n",
        notationType.name(), correct, p.size(), correct * 100.0 / p.size());
  }

  /**
   * Prints a message to stderr and exits with value 1.
   * 
   * @param msg
   *          the error message
   */
  protected static void errorExit(String msg) {
    System.err.printf("Error: %s\n", msg);
    System.exit(1);
  }

  /**
   * Initialization method of the program, supposed to be called directly from
   * the main method of a subclass.
   * 
   * Parses the command line options and starts the command line interface, or
   * performs a benchmark if so requested.
   * 
   * See the {@link Assignment2 class description} for an overview of the
   * accepted paramers.
   * 
   * @param args
   *          the command line arguments passed to the program
   */
  protected void init(String[] args) {
    Notation notationType = DEFAULT_NOTATION;
    boolean doBenchmark = false;

    /* Parse command line arguments. */
    if (args.length > 2) {
      errorExit(String.format("too many arguments.\n%s", USAGE_MSG));
    }

    /* Determine what kind of notation to use, and whether to benchmark. */
    for (int i = 0; i < args.length; i++) {
      /* Check for benchmark switch */
      if (args[i].equals("-benchmark")) {
        doBenchmark = true;
      } else if (args[i].equals("-infix")) {
        notationType = Notation.INFIX;
      } else if (args[i].equals("-postfix") || args[i].equals("-rpn")) {
        notationType = Notation.POSTFIX;
      } else {
        errorExit(String.format("unknown argument '%s'.\n%s", args[i],
            USAGE_MSG));
      }
    }

    /* Start the interface or benchmark. */
    if (doBenchmark) {
      // generateGoldStandard(notationType);
      benchmark(notationType);
    } else {
      prompt(notationType);
    }
  }

}
