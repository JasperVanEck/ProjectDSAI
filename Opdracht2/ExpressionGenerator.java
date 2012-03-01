//package src.ass2;

import java.io.*;
import java.util.*;

/**
 * Generates infix and postfix expressions on demand.
 * 
 * @author Marten Lohstroh
 * @author Stephan Schroevers
 */
public class ExpressionGenerator {
  public static final int OPERAND_RANGE = 100;
  public static final int MAX_PRECISION = 7;

  public static final String[] INTERNAL_INFIX_OPERATORS = { "-", "+", "-",
      "*", "/", "^", "!" };
  public static final String[] EXTERNAL_INFIX_OPERATORS = { " negate ", "+",
      "-", "*", "/", "**", "!1 " };
  public static final String[] INTERNAL_POSTFIX_OPERATORS = { "_", "+", "-",
      "*", "/", "^", "!" };
  public static final String[] EXTERNAL_POSTFIX_OPERATORS = { "lNx", "+", "-",
      "*", "/", "^", "lFx" };
  public static final int[] OPERATOR_PLACEMENT = { 2, 3, 3, 3, 3, 3, 1 };
  public static final int[] OPERATOR_ARITY = { 1, 2, 2, 2, 2, 2, 1 };

  public static final String[] EXTERNAL_INFIX_CALC = {
      "ghc-6.8.2",
      "-e",
      "let (!) :: (RealFrac a) => a -> a -> a\n"
          + "    (!) n a | fromIntegral (round n) /= n = error $ show n ++ errMsg\n"
          + "            | n < 0                       = error $ show n ++ errMsg\n"
          + "            | n > 65                     = error $ show n ++ errMsg'\n"
          + "            | n <= 1                      = a\n"
          + "            | otherwise                   = (n - 1) ! (n * a)\n"
          + "    errMsg :: String\n"
          + "    errMsg = \" factorial not defined\"\n"
          + "    errMsg' :: String\n"
          + "    errMsg' = \" factorial too big\"\n" + "in %s" }; /*
                                                                   * perform
                                                                   * calculations
                                                                   * , use n ! 1
                                                                   * for n
                                                                   * factorial
                                                                   */

  public static final String[] EXTERNAL_POSTFIX_CALC = { "dc", "-e",
      "[Ksk 0k d0>E s1%%0!=E d65<T sa 1 lBx lkk]sF " + /* factorial macro lFx */
      "[la0<R]sB " + /* test whether (more) multiplications are required */
      "[la* la1- sa lBx]sR " + /* a single multiplication */
      "[[Error: factorial undefined: ]np lkkq]sE " + /* invalid inp error msg */
      "[[Error: factorial too big: ]np lkkq]sT " + /* no lengthy recursion */
      "[_1*]sN " + /* negation macro; usage: lNx */
      "18k %s p" }; /* perform calculations and print the result */

  private static Random rand = new Random();

  public static String[] generateExpr(int depth,
      Calculator.Notation notationType) {
    long seed = rand.nextLong();
    String expr = null;

    rand.setSeed(seed);
    switch (notationType) {
    case INFIX:
      expr = generateInfixExpr(INTERNAL_INFIX_OPERATORS, depth);
      rand.setSeed(seed);
      return new String[] {
          expr,
          externalCalc(EXTERNAL_INFIX_CALC, generateInfixExpr(
              EXTERNAL_INFIX_OPERATORS, depth)) };
    case POSTFIX:
      expr = generatePostfixExpr(INTERNAL_POSTFIX_OPERATORS, depth);
      rand.setSeed(seed);
      return new String[] {
          expr,
          externalCalc(EXTERNAL_POSTFIX_CALC, generatePostfixExpr(
              EXTERNAL_POSTFIX_OPERATORS, depth)) };
    default:
      throw new IllegalArgumentException(String.format(
          "Don't know notation type '%d'", notationType));
    }
  }

  public static String generatePostfixExpr(String[] ops, int depth) {
    StringBuilder expr = new StringBuilder();
    int i = 0, cur_op = 0;

    if (depth < 0) {
      throw new IllegalArgumentException(String.format(
          "recursion depth '%d' not allowed", depth));
    }

    if (depth == 0) {
      return generateSpacing(generateOperand());
    }

    cur_op = rand.nextInt(ops.length);

    for (i = 0; i < OPERATOR_ARITY[cur_op] - 1; i++) {
      expr.append(" ").append(generateSpacing(generateOperand()));
    }

    return String.format("%s%s%s", generatePostfixExpr(ops, rand
        .nextInt(depth)), expr.toString(), generateSpacing(ops[cur_op]));
  }

  public static String generateInfixExpr(String[] ops, int depth) {
    StringBuilder expr = new StringBuilder();
    int cur_op = 0;

    if (depth < 0) {
      throw new IllegalArgumentException(String.format(
          "recursion depth '%d' not allowed", depth));
    }

    if (depth == 0) {
      return String.format(rand.nextBoolean() ? "%s" : "(%s)",
          generateSpacing(generateOperand()));
    }

    cur_op = rand.nextInt(ops.length);

    if ((OPERATOR_PLACEMENT[cur_op] & 0x1) != 0) {
      expr.append(generateParens(generateInfixExpr(ops, rand.nextInt(depth))));
    }

    expr.append(ops[cur_op]);

    if ((OPERATOR_PLACEMENT[cur_op] & 0x2) != 0) {
      expr.append(generateParens(generateInfixExpr(ops, rand.nextInt(depth))));
    }

    return generateParens(expr.toString());
  }

  private static String generateParens(String expr) {
    return String.format(rand.nextBoolean() ? "%s" : "(%s)",
        generateSpacing(expr));
  }

  private static String generateSpacing(String expr) {
    return String.format("%s%s%s", rand.nextBoolean() ? "" : " ", expr, rand
        .nextBoolean() ? "" : " ");
  }

  private static String generateOperand() {
    return rand.nextBoolean() ? Integer.toString(rand.nextInt(OPERAND_RANGE))
        : String.format(String.format("%%.%df", rand.nextInt(MAX_PRECISION)),
            rand.nextDouble() * OPERAND_RANGE);
  }

  private static String externalCalc(String[] cmd, String expr) {
    int i = 0, c = 0;
    Process p = null;
    StringBuilder outp_buf = new StringBuilder(32);

    /* We don't want to alter the original command. */
    cmd = cmd.clone();
    for (i = cmd.length - 1; i >= 0; i--) {
      cmd[i] = String.format(cmd[i], expr);
    }

    /* Execute the command and store whatever it outputs to stdout. */
    try {
      p = Runtime.getRuntime().exec(cmd);

      while (-1 != (c = p.getInputStream().read())) {
        outp_buf.append((char) c);
      }
    } catch (IOException ex) {
      throw new CalculatorException(String.format(
          "Cannot run external application: '%s'", ex.getMessage()));
    }

    /* In case the command did not terminate itself, force it to do so now. */
    p.destroy();

    try {
      /* Try to convert the program's output to a double. */
      return new Double(outp_buf.toString()).toString();
    } catch (NumberFormatException ex) {
      /*
       * If conversion fails, then the program's output may in fact be a string
       * containing an error message. Note that we disregard any information
       * that may have been written to stderr.
       */
      return ((outp_buf.length() > 0) ? String.format(
          "NULL: External output: '%s'", outp_buf.toString())
          : "NULL: Unknown Error");
    }
  }

}