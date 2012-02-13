import java.util.Random;

public abstract class CollectionTimer{
	public static int[] DEFAULT_MUTATIONS = {10000,-10000};
	private Random elemGen;

	public CollectionTimer(){
		elemGen = new Random(0);
	}

	public CollectionTimer(long elemGenSeed){
		elemGen = new Random(elemGenSeed);
	}

	public abstract void addElement(Integer elem);

	public boolean extract(int amount){
		if (amount > getSize()){
			return false;
		}

		for(int i = 0; i <= amount; i++){
			removeElement();
		}
		return true;
	}

	public abstract int getSize();

	public void insert(int amount){
		for (int i = 0; i <= amount; i++){
			this.addElement(new Integer(elemGen.nextInt()));
		}
	}

	public abstract boolean isEmpty();

	public abstract void removeElement();

	public long time(){
		long startTime = System.nanoTime();
		int[] mutations = DEFAULT_MUTATIONS;
		boolean extractSucces;

                for(int i=0;i < mutations.length;i++){
                        if(mutations[i]>0){
                                this.insert(mutations[i]);
                        }else{
				this.extract(-mutations[i]);
                        }
                }

		return System.nanoTime() - startTime;
	}

	public long time(int[] mutations){
		if(mutations == null)
		mutations = DEFAULT_MUTATIONS;

		long startTime = System.nanoTime();
		for(int i=0;i < mutations.length;i++){
			if(mutations[i]>0){
				this.insert(mutations[i]);
			}else{
				this.extract(-mutations[i]);

			}
		}

		return System.nanoTime() - startTime;
	}
}
