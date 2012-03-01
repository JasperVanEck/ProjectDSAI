/**
 * Defines an interface for stacks that contain {@link Token} instances.
 * 
 * @author Stephan Schroevers
 */
public interface TokenStack {

  /**
   * Tells if the stack is empty.
   * 
   * @return <code>true</code> if the stack is empty, <code>false</code>
   *         otherwise
   */
  public boolean isEmpty();

  /**
   * Adds the given token to the top of the stack.
   * 
   * @param elem
   *          the {@link Token} instance that must be added
   * @return the <code>Token</code> instance that was added
   */
  public Token push(Token elem);

  /**
   * Returns the token at the top of the stack.
   * 
   * @return the {@link Token} instance at the top of the stack
   * @throws java.util.NoSuchElementException
   *           if the stack is empty
   */
  public Token peek();

  /**
   * Removes and returns the token at the top of the stack.
   * 
   * @return the {@link Token} instance at the top of the stack
   * @throws java.util.NoSuchElementException
   *           if the stack is empty
   */
  public Token pop();

  /**
   * Returns the size of the stack.
   * 
   * @return a non-negative number
   */
  public int size();

}
