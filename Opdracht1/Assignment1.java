import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Vector;
import java.util.PriorityQueue;


public class Assignment1 extends Object{
	private ArrayList<List<Integer>> lists = new ArrayList<List<Integer>>();
	private ArrayList<Queue<Integer>> queues = new ArrayList<Queue<Integer>>();

	//constructor
	public Assignment1(){
		lists.add(new ArrayList<Integer>());
		lists.add(new LinkedList<Integer>());
		lists.add(new Stack<Integer>());
		lists.add(new Vector<Integer>());
		queues.add(new LinkedList<Integer>());
		queues.add(new PriorityQueue<Integer>());
	}

	// Main method
	public static void main(String[] args){
		int[] mutations;
		long elemGenSeed;

		if(args.length == 0)
			errorExit("Please Provide Input Arguments");


		if(args[0].equals("-s")){
			mutations = new int[args.length-2];
			elemGenSeed = Long.parseLong(args[1]);
			for(int i = 2; i < args.length; i++){
				mutations[i-2] = Integer.parseInt(args[i]);
			}
		}else{
			elemGenSeed = 0;
			mutations = new int[args.length];
			for(int i = 0; i< args.length; i++){
				mutations[i] = Integer.parseInt(args[i]);
			}
		}

		Assignment1 test = new Assignment1();

                if(elemGenSeed == 0 && mutations.length == 0){
			test.benchmark();
		}else if(elemGenSeed == 0 && mutations.length != 0){
			test.benchmark(elemGenSeed, mutations);
		}else if(mutations.length == 0){
			test.benchmark(elemGenSeed);
		}else{
			test.benchmark(elemGenSeed, mutations);
		}

		System.out.printf("Einde van het programma.");
	}


	// Het testen van de snelheid van de verschillende collections
	public void benchmark(){
                for(List listings: lists){
                        ListTimer timerList = new ListTimer(listings);
                        long listTime = timerList.time();
                        System.out.printf("De verstreken tijd na de operaties op de %s: %3.5f ns\n", listings, listTime);
                }

                for(Queue queueings: queues){
                        QueueTimer timerQueue = new QueueTimer(queueings);
                        long queueTime = timerQueue.time();
                        System.out.printf("De verstreken tijd na de operaties op de %s: %3.5f ns\n", queueings, queueTime);
                }
	}

	public void benchmark(long elemGenSeed){
                for(List listings: lists){
                        ListTimer timerList = new ListTimer(listings, elemGenSeed);
                        long listTime = timerList.time();
                        System.out.printf("De verstreken tijd na de operaties op de %s: %3.5f ns\n", listings, listTime);
                }

                for(Queue queueings: queues){
                        QueueTimer timerQueue = new QueueTimer(queueings, elemGenSeed);
                        long queueTime = timerQueue.time();
                        System.out.printf("De verstreken tijd na de operaties op de %s: %3.5f ns\n", queueings, queueTime);
                }
	}

	public void benchmark(long elemGenSeed, int[] mutations){

		for(List listings: lists){
			ListTimer timerList = new ListTimer(listings, elemGenSeed);
			long listTime = timerList.time(mutations);
			System.out.printf("De verstreken tijd na de operaties op de %s: %3.5f ns\n", listings, listTime);
		}

                for(Queue queueings: queues){
                        QueueTimer timerQueue = new QueueTimer(queueings, elemGenSeed);
                        long queueTime = timerQueue.time(mutations);
                        System.out.printf("De verstreken tijd na de operaties op de %s: %3.5f ns\n", queueings, queueTime);
                }
	}


	// Error method
	private static void errorExit(String msg){
		System.out.printf("%s\n",msg);
		System.exit(1);
	}

}
