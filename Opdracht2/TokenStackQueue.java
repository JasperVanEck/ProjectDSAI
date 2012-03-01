public class TokenStacksQueue extends Object implements TokenQueue{
	protected TokenStack in;
	protected TokenStack out;

	public TokenStacksQueue(){
		in = new TokenArrayStack();
		out = new TokenArrayStack();
	} 

	public TokenStacksQueue(TokenStack in, TokenStack out) {
		this.in = in;
		this.out = out;
	}

	//Check whether TokenStacksQueue is empty.
	public boolean isEmpty(){
		return in.isEmpty() && out.isEmpty();
	}

	
}