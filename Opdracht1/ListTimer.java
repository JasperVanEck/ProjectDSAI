import java.util.List;

public class ListTimer extends CollectionTimer{
	private ArrayList<List<Integer>> list;

	public ListTimer(List<Integer> list){
		this.list = list;
	}


	public ListTimer(List<Integer> list, long elemGenSeed){
		super(elemGenSeed);
		this.list = list;
	}

	//Wat is er aan de hand met de long van de derde constructor?
	//Nu maar weg gelaten.

	public void addElem(Integer Elem){
		list.add(Elem);
	}

	public int getSize(){
		return list.size();
	}

	public boolean isEmpty(){
		return list.isEmpty;
	}

	public void removeElement(){
		int last = getSize();
		if(last=> 0){
			list.remove(last);
		}else{
			System.out.printf("Index out of Bound");
			System.exit(1);
		}
	}
}
