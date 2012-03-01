package ass2;

import java.util.EmptyStackException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Assignment2 extends Calculator {

	public static void main(String[] args) {
		new Assignment2().init(args);
	}

	@Override
	public double calc(TokenQueue expr, Notation notationType) {
		if ( notationType == Notation.INFIX ) {
			return calcInfix( expr );
		} else if ( notationType == Notation.POSTFIX ) {
			return calcPostfix( expr );
		}
		return 0;
	}

	@Override
	public double calcInfix(TokenQueue expr) {
		TokenQueue outputQueue = new TokenStacksQueue();
		TokenStack stack = new TokenArrayStack();
		
//	    * While there are tokens to be read:
		while ( !expr.isEmpty() ) {
//	        * Read a token.
			Token tok = expr.dequeue();
//	        * If the token is a number, then add it to the output queue.
			if ( tok instanceof Operand ) {
				outputQueue.enqueue( tok );
			}
			
//	        * If the token is an operator, o1, then:
			if ( tok instanceof Operator )
	        {
//				* while there is an operator token, o2, at the top of the stack, and
//   			  either o1 is left-associative and its precedence is less than or equal to that of o2,
//                or o1 is right-associative and its precedence is less than that of o2,
				 while (( !stack.isEmpty() && stack.peek() instanceof Operator ) &&
				 		(( (((Operator) tok).isLeftAssoc()||((Operator) tok).isAssoc() ) && ((Operator) tok).getPrecedence() <= ((Operator) stack.peek()).getPrecedence() ) ||
						( ((Operator) tok).isRightAssoc() && ((Operator) tok).getPrecedence() < ((Operator) stack.peek()).getPrecedence() ))) {
//	                	pop o2 off the stack, onto the output queue;
						outputQueue.enqueue( stack.pop() );	

				}
//	            * push o1 onto the stack.
				stack.push( tok );
	        }
//			* If the token is a parenthesis
			if ( tok instanceof Parenthesis ) {
//	        	* If the token is a left parenthesis, then push it onto the stack.
				Parenthesis p = (Parenthesis) tok;
				if ( p.isLeft() ) {
					stack.push( p );
				}
//	        	* If the token is a right parenthesis:
				if ( p.isRight() ) {
//	            	* Until the token at the top of the stack is a left parenthesis,
					while ( !stack.isEmpty() && !(stack.peek() instanceof Parenthesis) ) {
//						* pop operators off the stack onto the output queue.
						try {
							outputQueue.enqueue( stack.pop() );
						} catch ( EmptyStackException e ) {
//		            		* If the stack runs out without finding a left parenthesis, 
//							  then there are mismatched parentheses.
							throw new CalculatorException();
						}
						
//	            		* Pop the left parenthesis from the stack, but not onto the output queue.
					}					
					try {
						stack.pop();
					} catch ( EmptyStackException e ) {
//	            		* If the stack runs out without finding a left parenthesis, 
//						  then there are mismatched parentheses.
						throw new CalculatorException();
					}

					
				}
			}
		}
//		* When there are no more tokens to read:
//	    * While there are still operator tokens in the stack:
		while ( !stack.isEmpty() ) {
//	    	* If the operator token on the top of the stack is a parenthesis, 
//			  then there are mismatched parentheses.
			Token tok = stack.pop();
			if ( tok instanceof Parenthesis ) {
				throw new CalculatorException();
			}
//	        * Pop the operator onto the output queue.
			outputQueue.enqueue( tok );
		}
//	    * Exit.

		return calcPostfix( outputQueue );
	}

	@Override
	public double calcPostfix(TokenQueue expr) {
		TokenStack stack = new TokenArrayStack(); 
		
		while ( !expr.isEmpty() ) {
			Token elem = expr.dequeue();
//			System.out.print(elem.toString());
			if ( elem instanceof Operand ) {
				// The element is an operand		
				stack.push( elem );
			} 
			
			if ( elem instanceof Operator ) {
				apply( (Operator) elem, stack);				
			}
		}
		
		if ( stack.isEmpty() ) {
			throw new CalculatorException();
		} else {
			return ((Operand) stack.pop()).getValue();
		}
	}

	@Override
	protected TokenQueue parseExpr(String expr, Notation notationType) {
		TokenQueue queue = new TokenStacksQueue();
		boolean wasOperator = true;
		
		Matcher m = Pattern.compile("(\\d+(\\.\\d+)?)|" +
				"(\\-)|" +
				"(\\+)|" +
				"(\\*)|" +
				"(\\/)|" +
				"(\\^)|" +
				"(\\!)|" +
				"(\\_)|" +
				"\\(|" +
				"\\)|]").matcher( expr );
		while ( m.find() ) {
			Token elem = toToken(m.group());
			if (elem instanceof Operator && elem != Operator.FACTORIAL) {
				if (((Operator) elem).toString() == "subtract" && wasOperator) {
					elem = Operator.NEGATE;
				}
				wasOperator = true;
			} else if ( elem instanceof Parenthesis ) {
				if ( ((Parenthesis)elem).isLeft() ) {
					wasOperator = true;
				}
			} else {
				wasOperator = false;
			}
			
			queue.enqueue(elem);
		}
		
		return queue;
	}

	@Override
	protected Token toToken(String tok) {
		if(tok.equals("+")){
			return Operator.ADD;
		} else if(tok.equals("-")) {
			return Operator.SUBTRACT;
		} else if(tok.equals("*")) {
			return Operator.MULTIPLY;
		} else if(tok.equals("/")){
			return Operator.DIVIDE;
		} else if(tok.equals("^")){
			return Operator.POWER;
		} else if(tok.equals("!")){
			return Operator.FACTORIAL;	
		} else if(tok.equals("_")){
			return Operator.NEGATE;
		} else if(tok.equals("(")){
			return Parenthesis.LEFT_PARENTHESIS;
		} else if(tok.equals(")")){
			return Parenthesis.RIGHT_PARENTHESIS;
		} else {
			return new Operand(Double.parseDouble(tok));
		}			
	}
}