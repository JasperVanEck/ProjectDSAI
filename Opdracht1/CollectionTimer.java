import java.util.Random;

public abstract class CollectionTimer{
	public static int[] DEFAULT_MUTATIONS = {-10000,10000};
	private Random elemGen;

	public CollectionTimer(long elemGenSeed){
		elemGen = Random(elemGenSeed);
	}

	public abstract void addElement(Integer elem);

	public boolean extract(int amount){
		if (amount < getSize()){
			return false;
		}


		for( i = 0; i < amount; i+=1){
			removeElement();
		}
		return true;
	}

	public abstract int getSize();

	public void insert(int amount){
		for (i = 0; i < amount; i += 1){
			addElement();
		}
	}

	public abstract boolean isEmpty();

	public abstract void removeElement();

	public long time(){
		return 1;
	}

	public long time(int[] mutations){
		if(mutations == null)
		mutations = DEFAULT_MUTATIONS;

		return 1;
	}
}
