import java.util.Queue;

public class QueueTimer extends CollectionTimer{
	private Queue<Integer> queue;

	public QueueTimer(Queue<Integer> queue){
		this.queue = queue;
	}

	public QueueTimer(Queue<Integer> queue, long elemGenSeed){
		super(elemGenSeed);
		this.queue = queue;
	}

	//Wat is er met die derde constructor?


	@Override
	public void addElement(Integer elem){
		queue.add(elem);
	}

	@Override
	public int getSize(){
		return queue.size();
	}

	@Override
	public boolean isEmpty(){
		return queue.isEmpty();
	}

	@Override
	public void removeElement(){
		if(queue.isEmpty()){
			System.out.printf("Index out of bound");
			System.exit(1);
		}else{
			queue.remove();
		}
	}
}
