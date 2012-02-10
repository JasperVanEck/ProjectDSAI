public class QueueTimer extends CollectionTimer{
	private ArrayList<Queue<Integer>> queue;

	public QueueTimer(Queue<Integer> queue){
		this.queue = queue;
	}

	public QueueTimer(Queue<Integer> queue, long elemGenSeed){
		super(elemGenSeed);
		this.queue = queue;
	}

	public void addElem(Integer elem){
		queue.add(elem);
	}

	public int getSize(){
		return = queue.size();
	}

	public boolean isEmpty(){
		return queue.isEmpty();
	}

	public void removeElement(){
		if(isEmpty()){
			System.out.printf("Index out of bound");
			System.exit(1);
		}else{
			queue.remove();
		}
	}
}
