import java.util.*;

/**
 * Student test program for the third assignment of the course Datastructuren
 * 2007/2008 at the University of Amsterdam.
 * 
 * <ul>
 * <li>
 * Usage: <code>java Assignment3 [rounds] [max_add_rem] [seed]</code>.</li>
 * </ul>
 * 
 * Repeats the process of adding and removing elements <code>rounds</code>
 * number times, each time adding and then removing at most
 * <code>max_add_rem</code> elements. The random number generator that is used
 * to generate elements that are to be inserted into the priority queue is
 * seeded with <code>seed</code>.
 * 
 * Default values:
 * <ul>
 * <li><code>rounds</code>: 1000.</li>
 * <li><code>max_add_rem</code>: 1000.</li>
 * <li><code>seed</code>: 0.</li>
 * </ul>
 * 
 * @see MeldQueue
 * @author Stephan Schroevers
 */
public class Assignment3 {
  /**
   * Number of times that the process of adding and removing elements should be
   * repeated.
   */
  public static final int NO_OF_ROUNDS = 1000;
  /**
   * Maximum number of elements to add/remove per round.
   */
  public static final int MAX_SUCC_ADD_REM = 1000;
  /**
   * Default seed for the random number generator used for testing.
   */
  public static final long DEFAULT_SEED = 0;

  /**
   * Print a message to stderr and exit with value 2.
   * 
   * @param msg
   *          the error message
   */
  private static void errorExit(String msg) {
    System.err.printf("Error: %s\n", msg);
    System.exit(2);
  }

  /**
   * Main method of the program.
   * 
   * Parses the command line options and initiates the testing of the
   * {@link MeldQueue} data structure.
   * 
   * The result of the test is outputted to stdout. If the test fails, the
   * program exits with status 1.
   * 
   * See the {@link Assignment3 class description} for an overview of the
   * accepted paramers.
   * 
   * @param args
   *          the command line arguments passed to the program
   */
  @SuppressWarnings("fallthrough")
  public static void main(String[] args) {
    /* Default settings, which may be overridden by command line arguments. */
    int rounds = NO_OF_ROUNDS;
    int addRem = MAX_SUCC_ADD_REM;
    long seed = DEFAULT_SEED;
    /* Priority queue that is to be tested. */
    MeldQueue<Integer> meld = new MeldQueue<Integer>();
    /* Reference data structure. */
    PriorityQueue<Integer> java = new PriorityQueue<Integer>();

    /* Parse the arguments. */
    try {
      switch (args.length) {
      case 3:
        seed = Integer.parseInt(args[2]);
      case 2:
        addRem = Integer.parseInt(args[1]);
      case 1:
        rounds = Integer.parseInt(args[0]);
      case 0:
        break;
      default:
        errorExit("wrong number of arguments");
      }
    } catch (NumberFormatException ex) {
      errorExit("invalid argument provided");
    }

    /* Start the test, catch any exception. */
    try {
      test(meld, java, rounds, addRem, seed);
    } catch (RuntimeException ex) {
      System.out.printf("Failure: %s.\n", ex.getMessage());
      System.exit(1);
    }

    System.out.println("Success!");
  }

  /**
   * Performs tests on a {@link MeldQueue} priority queue, using the Java SDK's
   * {@link java.util.PriorityQueue} as a reference.
   * 
   * The data structure is tested by inserting random integers. A descriptive
   * {@link java.lang.RuntimeException} is thown if the tests fails.
   * 
   * @param meld
   *          the priority queue that is to be tested
   * @param java
   *          reference priority queue provided by the Java SDK
   * @param rounds
   *          the number of times insert and removal runs should be performed
   * @param addRem
   *          maximum number of elements to insert/remove per run
   * @param seed
   *          seed for the random element generator
   * @throws java.lang.RuntimeException
   *           if the priority queues are positively distinct.
   */
  public static void test(MeldQueue<Integer> meld,
      PriorityQueue<Integer> java, int rounds, int addRem, long seed) {
    Random elemGen = new Random(seed);

    /*
     * Repeat the process of adding and removing elements 'rounds' number of
     * times.
     */
    for (int i = 0; i < rounds; i++) {
      /* Add elements, anywhere between 0 and 'addRem' (inclusive) times. */
      for (int j = elemGen.nextInt(addRem + 1); j > 0; j--) {
        java.add(meld.add(elemGen.nextInt()));
        assertAllIsFine(meld, java, false);
      }

      /*
       * Remove elements, anywhere between 0 and 'addRem' (inclusive) times.
       * Note that the program may try to remove more elements than are present
       * in the queue. This is not a problem, since both queues will then just
       * return null.
       */
      for (int j = elemGen.nextInt(addRem + 1); j > 0; j--) {
        assertAllIsFine(meld, java, true);
      }
    }

    /* Make a final check, after the last mutation. */
    assertAllIsFine(meld, java, false);
  }

  /**
   * Throws a descriptive {@link java.lang.RuntimeException} if the directly
   * accessible information on the given priority queues does not match.
   * 
   * This method compares the output of at least the following three methods:
   * <code>size()</code>, <code>isEmpty()</code>, <code>peek()</code>.
   * 
   * If specified, the method will also dequeue the head elements of the queues
   * and compare those, using <code>poll()</code>. This action is performed
   * <em>after</em> the other checks.
   * 
   * @param meld
   *          the priority queue that is to be tested
   * @param java
   *          reference priority queue provided by the Java SDK
   * @param poll
   *          whether to remove and compare the head elements of the queues
   * @throws java.lang.RuntimeException
   *           if the priority queues are positively distinct.
   */
  public static void assertAllIsFine(MeldQueue<Integer> meld,
      PriorityQueue<Integer> java, boolean poll) {
    if (meld.size() != java.size()) {
      throw new RuntimeException("results of size() did not match");
    }

    if (meld.isEmpty() != java.isEmpty()) {
      throw new RuntimeException("results of isEmpty() did not match");
    }

    if (meld.isEmpty() ? meld.peek() != java.peek() : !meld.peek().equals(
        java.peek())) {
      throw new RuntimeException("results of peek() did not match");
    }

    if (poll
        && (meld.isEmpty() ? meld.poll() != java.poll() : !meld.poll().equals(
            java.poll()))) {
      throw new RuntimeException("results of poll() did not match");
    }
  }
}
