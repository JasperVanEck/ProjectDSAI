/**
 * Defines an interface for deques that contain {@link Token} instances.
 * 
 * @author Stephan Schroevers
 */
public interface TokenDeque {

  /**
   * Tells if the deque is empty.
   * 
   * @return <code>true</code> if the deque is empty, <code>false</code>
   *         otherwise
   */
  public boolean isEmpty();

  /**
   * Adds the given token to the front of the deque.
   * 
   * @param elem
   *          the {@link Token} instance that must be added
   * @return the <code>Token</code> instance that was added
   */
  public Token insertFirst(Token elem);

  /**
   * Adds the given token to the rear of the deque.
   * 
   * @param elem
   *          the {@link Token} instance that must be added
   * @return the <code>Token</code> instance that was added
   */
  public Token insertLast(Token elem);

  /**
   * Returns the token at the front of the deque.
   * 
   * @return the {@link Token} instance at the front of the deque
   * @throws java.util.NoSuchElementException
   *           if the deque is empty
   */
  public Token first();

  /**
   * Returns the token at the rear of the deque.
   * 
   * @return the {@link Token} instance at the rear of the deque
   * @throws java.util.NoSuchElementException
   *           if the deque is empty
   */
  public Token last();

  /**
   * Removes and returns the token at the front of the deque.
   * 
   * @return the {@link Token} instance at the front of the deque
   * @throws java.util.NoSuchElementException
   *           if the deque is empty
   */
  public Token removeFirst();

  /**
   * Removes and returns the token at the rear of the deque.
   * 
   * @return the {@link Token} instance at the rear of the deque
   * @throws java.util.NoSuchElementException
   *           if the deque is empty
   */
  public Token removeLast();

  /**
   * Returns the size of the deque.
   * 
   * @return a non-negative number
   */
  public int size();

}
