import java.util.*;

/**
 * Randomized Meldable Priority Queue implementation based on a paper by Anna
 * Gambin and Adam Malinowski (1998).
 */
public class MeldQueue<E extends Comparable<E>> {
  /**
   * Number of nodes in the tree (number of elements in the queue).
   */
  private int size = 0;
  /**
   * Root of the tree (head of the queue).
   */
  private MeldNode<E> root = null;
  /**
   * Random number generator used for melding subtrees.
   */
  private Random coin = new Random();

  /**
   * Default constructor that creates an empty <code>MeldQueue</code> instance
   * with a random seed for the coin flip mechanism.
   */
  public MeldQueue() {
	//coin = new Random(); This is already done.
  }

  /**
   * Custom constructor that creates an empty <code>MeldQueue</code> instance
   * with the specified seed for the coin flip mechanism.
   * 
   * @param seed
   *          the seed for the random number generator that is used for melding
   *          subtrees
   */
  public MeldQueue(long seed) {
	coin = new Random(seed);
  }

  /**
   * Adds the specified element to this queue.
   * 
   * For convenience, the inserted element is also returned by this method.
   * 
   * @param obj
   *          the object to add
   * @return the element that was just added
   */
  public E add(E obj) {
	size++;
	root = meld(root, new MeldNode<E>(obj));
	return obj;
  }

  /**
   * Retrieves, but does not remove, the head of this queue, returning
   * <code>null</code> if this queue is empty.
   * 
   * @return the element that is in front of the queue
   */
  public E peek() {
	if(size == 0){
		return null;
	}
	
	return root.getObject();
  }

  /**
   * Retrieves and removes the head of this queue, or <code>null</code> if this
   * queue is empty.
   * 
   * @return the element that is in front of the queue
   */
  public E poll() {
	if(size == 0){
		return null;
	}

	E front = root.getObject();
	root = meld(root.leftChild, root.rightChild);
	size--;
	
	return front;
  }

  /**
   * Removes all elements from the priority queue.
   * 
   * The queue will be empty after this call returns.
   */
  public void clear() {
	size = 0;
	root = null;
  }

  /**
   * Returns the number of elements in the queue.
   * 
   * @return the size of the queue
   */
  public int size() {
	return size;
  }

  /**
   * Tells wheter the queue is empty.
   * 
   * @return <code>true</code> if the queue does not contain any elements,
   *         <code>false</code> otherwise
   */
  public boolean isEmpty() {
	 return size == 0;
  }

  /**
   * Melds the two trees rooted at <code>n1</code> and <code>n2</code>, and
   * returns the root of the resulting merged tree.
   * 
   * @param n1
   *          root of the first tree
   * @param n2
   *          root of the first tree
   * @return the result of melding <code>n1</code> and <code>n2</code>
   */
  private MeldNode<E> meld(MeldNode<E> n1, MeldNode<E> n2) {
	if(n1 == null){
		return n2;
	}
	
	if(n2 == null){
		return n1;
	}

	if(n1.compareTo(n2) > 0){
		MeldNode<E> temp = n2;
		n2 = n1;
		n1 = temp;
	}

	if(coin.nextInt(2) == 1){
		n1.leftChild = meld(n1.leftChild, n2);
	}else{
		n1.rightChild = meld(n1.rightChild, n2);
	}

	return n1;
  }

  /**
   * Tree node used by the Randomized Meldable Priority Queue to store a single
   * element.
   */
  private final class MeldNode<F extends Comparable<F>> implements
      Comparable<MeldNode<F>> {
    /**
     * The left child node of the (sub)tree rooted at this node.
     */
    public MeldNode<F> leftChild = null;
    /**
     * The right child node of the (sub)tree rooted at this node.
     */
    public MeldNode<F> rightChild = null;
    /**
     * The object (value) represented by this node.
     */
    private F obj = null;

    /**
     * Default constructor that creates an <code>MeldNode</code> instance for
     * the given object.
     * 
     * The new node will initially have no children.
     * 
     * @param obj
     *          the object to store in this node
     */
    public MeldNode(F obj) {
	this.obj = obj;
    }

    /**
     * Return the object (value) represented by this node.
     * 
     * @return the contents of this node
     */
    public F getObject() {
	return obj;
    }

    /**
     * Compares the value of this node with the value of the specified node for
     * order.
     * 
     * @param other
     *          the node to compare with
     * @return a negative integer, zero, or a positive integer as this node's
     *         value is less than, equal to, or greater than the value of the
     *         specified node.
     * @see java.lang.Comparable
     */
    public int compareTo(MeldNode<F> other) {
	return obj.compareTo(other.getObject());
    }
  }
}
