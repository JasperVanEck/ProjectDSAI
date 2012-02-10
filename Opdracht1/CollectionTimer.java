import java.util.Random;

public abstract class CollectionTimer{
	public static int[] DEFAULT_MUTATIONS = {-10000,10000};
	private Random elemGen;

	public CollectionTimer(long elemGenSeed){
		elemGen = Random(elemGenSeed);
	}

	public abstract void addElement(Integer elem);

	public boolean extract(int amount){
		
		return true;
	}

	public abstract int getSize();

	public void insert(int amount){

	}

	public abstract boolean isEmpty();

	public abstract void removeElement();

	public long time(){
		return 1;
	}

	public long time(int[] mutations){
		return 1;
	}
}
