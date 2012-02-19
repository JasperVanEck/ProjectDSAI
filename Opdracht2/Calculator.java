public abstract class Calculator extends Object{
	public static Calculator.Notation DEFAULT_NOTATION;
	public static int GOLD_STANDARD_EXPR;
	public static int MAX_RAND_EXPR_DEPTH;
	public static String PROMPT_STRING;
	public static String USAGE_MSG;

	public void apply(Operator op, TokenStack stack){
		Token operand1 = stack.pop();
		Token operand2 = stack.pop();

		//Token result = ;
		//stack.push(result);
	}

	private void benchmark(Calculator.Notation notationType){


	}

	public abstract double calc(TokenQueue expr, Calculator.Notation notationType);

	public abstract double calcInfix(TokenQueue expr);

	public abstract double calcPostfix(TokenQueue expr);

	protected static void errorExit(String msg){
		 System.out.printf(msg);
		 System.exit(1);
	}

	private void generateGoldStandard(Calculator.Notation notationType){


	}

	protected void init(String[] args){

	}

	protected abstract TokenQueue parseExpr(String expr, Calculator.Notation notationType);

	private void prompt(Calculator.Notation notationType){

	}

	protected abstract Token toToken(String tok);
}