import java.util.Scanner;

public final class Assignment2 extends Calculator{
	public static String[] INFIX_TOKENS;
	public static String[] RPN_TOKENS;
	public static String[][] TOKENS;

	public static void main(String[] args){
		new Assignment2().init(args);
	}

	//Determine whether to do a post- or infix calculation.
	public double calc(TokenQueue expr, Calculator.Notation notationType){
		if(expr.isEmpty()){
			throw new CalculatorException("No expression was given to Calculate.");
		}else if(notationType == Calculator.Notation.INFIX){
			return calcInfix(expr);
		}else if(notationType == Calculator.Notation.POSTFIX){
			return calcPostfix(expr);
		}else{
			throw new CalculatorException("Incorrect NotationType.");
		}
	}

	public double calcInfix(TokenQueue expr){
		TokenStack stack = new TokenArrayStack();
		TokenQueue que = new TokenStacksQueue();
		Token opera;
		//Start of the shunting yard algorithm
		while(!expr.isEmpty()){
			opera = expr.dequeue();
			
			//Queue if token is operand
			if(opera instanceof Operand){
				que.enqueue(opera);
			}else if(opera instanceof Operator){
				//The operator must be left associative and its precedence must be lower or equal than the token on top of the stack OR
				//the operator must be right associative and its precedence must be lower than the token on top of the stack.
				if(!stack.isEmpty()){
					while(stack.peek() instanceof Operator && 
						((((Operator)opera).isLeftAssoc() || ((Operator)opera).isAssoc()) && 
						(((Operator)opera).getPrecedence() <= ((Operator)stack.peek()).getPrecedence())) || 
						((((Operator)opera).isRightAssoc() || ((Operator)opera).isAssoc()) && 
						(((Operator)opera).getPrecedence() < ((Operator)stack.peek()).getPrecedence()))
						){
							que.enqueue(stack.pop());
					}
				}
				stack.push(opera);
			}else if(opera.equals(Parenthesis.LEFT_PARENTHESIS)){
				stack.push(opera);
			}else if(opera.equals(Parenthesis.RIGHT_PARENTHESIS)){
				//Ensure that there isnt a missing parenthesis.
				while(!stack.isEmpty() && !stack.peek().equals(Parenthesis.LEFT_PARENTHESIS)){
					que.enqueue(stack.pop());
				}
				
				if(stack.isEmpty()){
					throw new CalculatorException("There is a missing left Parenthesis.");
				}
				
				stack.pop();
			}else{
				throw new CalculatorException("You didn't enter a correct infix sequence.");
			}
			
			
			
		}
		
		//Move all the tokens from the stack to the queue
		while(!stack.isEmpty()){
			//Ensure that there are no parenthesis left on the stack
			if(stack.peek().equals(Parenthesis.LEFT_PARENTHESIS) || stack.peek().equals(Parenthesis.RIGHT_PARENTHESIS)){
				throw new CalculatorException("There are still Parenthesis left on the stack, this is not allowed.");
			}
			que.enqueue(stack.pop());
		}
		
		//que was rewritten to RPN, calcPostfix will do the actual calculating.
		return calcPostfix(que);
	}


	public double calcPostfix(TokenQueue expr){
		try{
			double operand1;
			double operand2;
			Token operator;
			
			TokenArrayStack operands = new TokenArrayStack();
			
			//iterate through the tokens.
			while(!(expr.isEmpty())){
				//retrieve the operands from the queue.
				while(expr.front() instanceof Operand){
					operands.push(expr.dequeue());
				}
				System.out.printf(expr.isEmpty()+ "\n");
				if(!expr.isEmpty() && ((Operator)expr.front()).getArity() == 1){
					try{
						operator = expr.dequeue();

						operand1 = Double.parseDouble(operands.pop().toString());
						//Negative numbers or too big of a numbers arent nice to factorials.
						if(operator.equals(Operator.FACTORIAL)){
							if(operand1 < 0 || operand1 > 50){
								throw new CalculatorException("The entered number for the factorial is either too small or too big.");
							}else{
								operands.push(toToken(Double.toString(Operator.factorial(operand1))));
							}
						
						}else if(operator.equals(Operator.NEGATE)){
							operands.push(toToken(Double.toString(Operator.negate(operand1))));
						}
					}catch(NullPointerException e){
						throw new CalculatorException("");
					}
					
				}else if(!expr.isEmpty() && ((Operator)expr.front()).getArity() == 2){
					try{
						operator = expr.dequeue();
						operand1 = Double.parseDouble(operands.pop().toString());
						operand2 = Double.parseDouble(operands.pop().toString());
						
						if(operator.equals(Operator.ADD)){
							operands.push(toToken(Double.toString(Operator.add(operand1,operand2))));
						}else if(operator.equals(Operator.SUBTRACT)){
							operands.push(toToken(Double.toString(Operator.subtract(operand1,operand2))));
						}else if(operator.equals(Operator.MULTIPLY)){
							operands.push(toToken(Double.toString(Operator.multiply(operand1,operand2))));
						}else if(operator.equals(Operator.DIVIDE)){
							operands.push(toToken(Double.toString(Operator.divide(operand1,operand2))));
						}else if(operator.equals(Operator.POWER)){
							operands.push(toToken(Double.toString(Operator.power(operand1,operand2))));
						}
						
					}catch(NullPointerException e){
					      throw new CalculatorException("");
					}
				}
			}
			return Double.parseDouble(operands.pop().toString());
		}catch(NumberFormatException e){
			throw new CalculatorException("Something went wrong in the calculation.");
		}
	}

	public TokenQueue parseExpr(String expr, Calculator.Notation notationType){
		//java only works with dots.
		expr = expr.replaceAll(",",".");

		//Remove the spaces when using the INFIX notationType, saves iterations
		if(notationType.equals(Calculator.Notation.INFIX)){
			expr = expr.replaceAll(" ","");
		}

		//Creating the queue to be returned
		TokenQueue que = new TokenStacksQueue();

		int pos = 0;
		//Iterating through the expr
		while(pos < expr.length()){
			if(expr.charAt(pos) == ' '){
				pos++;//skip spaces
			}else if((pos+2 < expr.length()) && expr.charAt(pos) ==  '-' && expr.charAt(pos+1) == '-'){
				que.enqueue(toToken("+"));//double negatives are postives.
				pos = pos + 2;
			
			//Check whether a number is being negated
			}else if((pos+2 < expr.length()) && expr.charAt(pos) == '-' && isNumber(expr.charAt(pos+1))){
				int tempPos = ++pos;

				while(pos < expr.length() && (isNumber(expr.charAt(pos)) || (expr.charAt(pos) == '.') )){
					pos++;
				}

				que.enqueue(toToken("_"));
				que.enqueue(toToken(expr.substring(tempPos, pos)));
			
			//Enqueue a whole number, including decimal point
			}else if(isNumber(expr.charAt(pos)) || expr.charAt(pos) == '.'){
				int tempPos = pos;

				while(pos < expr.length() && (isNumber(expr.charAt(pos)) || (expr.charAt(pos) == '.') )){
					pos++;
				}
				
				que.enqueue(toToken(expr.substring(tempPos, pos)));
			//Minus signs following a parenthesis is a negate symbol.
			}else if((pos+2 < expr.length()) && expr.charAt(pos) == '(' && expr.charAt(pos+1) == '-'){
				que.enqueue(toToken("("));
				que.enqueue(toToken("_"));
				pos = pos + 2;
			}else{
				que.enqueue(toToken(Character.toString(expr.charAt(pos))));
				pos++;
			}
		}
		return que;
	}

	//Returns the token of the respective string.
	public Token toToken(String tok){
		try{
			if(tok.equals("_")){
				return Operator.NEGATE;
			}else if(tok.equals("+")){
				return Operator.ADD;
			}else if(tok.equals("-")){
				return Operator.SUBTRACT;
			}else if(tok.equals("*")){
				return Operator.MULTIPLY;
			}else if(tok.equals("/")){
				return Operator.DIVIDE;
			}else if(tok.equals("!")){
				return Operator.FACTORIAL;
			}else if(tok.equals("^")){
				return Operator.POWER;
			}else if(tok.equals("(")){
				return Parenthesis.LEFT_PARENTHESIS;
			}else if(tok.equals(")")){
				return Parenthesis.RIGHT_PARENTHESIS;
			}else{
				return new Operand(Double.parseDouble(tok));
			}
		}catch(NumberFormatException e){
			throw new CalculatorException("The token: " + tok + ", is not an Operand.");
		}
	}

	public boolean isNumber(char c){
		try{
			Double.parseDouble(Character.toString(c));
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}
}
