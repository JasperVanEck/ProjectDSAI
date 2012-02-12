import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Assignment1 extends Object{
	private ArrayList<List<Integer>> list;
	private ArrayList<Queue<Integer>> queue;


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




	}


	// Het testen van de snelheid van de verschillende collections
	public void benchmark(){

	}

	public void benchmark(long elemgenSeed){

	}

	public void benchmark(long elemGenSeed, int[] mutations){

	}


	// Error method
	private static void errorExit(String msg){
		System.out.printf("%s\n",msg);
		System.exit(1);
	}

}
