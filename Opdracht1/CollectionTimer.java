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
			System.out.printf("i = %d\n", i);
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

                for(int i=0;i <= mutations.length;i++){
                        if(mutations[i]>0){
                                this.insert(mutations[i]);
                        }else{
                                extractSucces = this.extract(-mutations[i]);
				if(!extractSucces){
					System.out.printf("Kon geen elementen verwijderen\n");
					System.exit(1);
				}
                        }
                }

		return System.nanoTime() - startTime;
	}

	public long time(int[] mutations){
		if(mutations == null)
		mutations = DEFAULT_MUTATIONS;

		long startTime = System.nanoTime();
		for(int i=0;i <= mutations.length;i++){
			if(mutations[i]>0){
				this.insert(mutations[i]);
				System.out.printf("Ik doe een insert \n");
			}else{
				this.extract(-mutations[i]);
				System.out.printf("Ik doe een extract \n");
			}
		}

		return System.nanoTime() - startTime;
	}
}
