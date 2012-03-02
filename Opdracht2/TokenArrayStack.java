public class TokenArrayStack extends Object implements TokenStack{
	protected Token[] arr;
	public static int DEFAULT_INIT_CAPACITY = 16;
	protected int top;

	//Constructor using DEFAULT_INIT_CAPACITY.
	public TokenArrayStack(){
		arr = new Token[DEFAULT_INIT_CAPACITY];
		top = 0;
	}

	//Constructor using given initCapacity.
	public TokenArrayStack(int initCapacity){
		if(initCapacity <= 0){
			arr = new Token[DEFAULT_INIT_CAPACITY];
		}else{
			arr = new Token[initCapacity];
		}
		top = 0;
	}

	//Ensuring the capacity of the array, doubling it in size if needed.
	protected void ensureCapacity(){
		int size = size();

		if(top >= size){
			Token[] temparray = new Token[2*size];

			for(int i = 0; i < arr.length; i++){
				temparray[i] = arr[i];
			}

			arr = temparray;
		}
	}

	//Check whether array is empty
	public boolean isEmpty(){
		return top == 0;
	}

	//Looking at the top element and returning it
	public Token peek(){
		return arr[top];
	}

	//taking an element from the top of the stack
	public Token pop(){
		Token firstElem = peek();
		top--;
		return firstElem;
	}

	//pushing an element on top of the stack
	public Token push(Token elem){
		ensureCapacity();

		top++;
		arr[top] = elem;

		return elem;
	}

	//Retrieving the size of the Token array
	public int size(){
		return arr.length;
	}
}