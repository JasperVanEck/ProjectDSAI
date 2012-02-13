import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Assignment1 extends Object{
	private List<Integer> list;
	private Queue<Integer> queue;


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
		QueueTimer timerQueue = new QueueTimer(queue);
		ListTimer timerList = new ListTimer(list);

		long queueTime = timerQueue.time();
		long listTime = timerList.time();

		System.out.printf("De verstreken tijd na de operaties op de Queue: %3.5f ns \n", queueTime);
		System.out.printf("De verstreken tijd na de operaties op de List: %3.5f ns \n", listTime);
	}

	public void benchmark(long elemGenSeed){
		QueueTimer timerQueue =  new QueueTimer(queue, elemGenSeed);
                ListTimer timerList = new ListTimer(list, elemGenSeed);

                long queueTime = timerQueue.time();
                long listTime = timerList.time();

                System.out.printf("De verstreken tijd na de operaties op de Queue: %3.5f ns \n", queueTime);
                System.out.printf("De verstreken tijd na de operaties op de List: %3.5f ns \n", listTime);
	}

	public void benchmark(long elemGenSeed, int[] mutations){
		QueueTimer timerQueue = new QueueTimer(queue, elemGenSeed);
                ListTimer timerList = new ListTimer(list, elemGenSeed);

                long queueTime = timerQueue.time(mutations);
                long listTime = timerList.time(mutations);

                System.out.printf("De verstreken tijd na de operaties op de Queue: %3.5f ns \n", queueTime);
                System.out.printf("De verstreken tijd na de operaties op de List: %3.5f ns \n", listTime);

	}


	// Error method
	private static void errorExit(String msg){
		System.out.printf("%s\n",msg);
		System.exit(1);
	}

}
