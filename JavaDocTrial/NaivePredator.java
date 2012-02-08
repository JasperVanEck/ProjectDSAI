public class NaivePredator extends Predator{
	private List pixels = new ArrayList<pixel>;


	public NaivePredator(Pixel appearance){
		super(appearance);
	}

	public void updateVisualData(java.util.ArrayList<Pixel> pixels){
		this.pixels = pixels;
	}

	public java.util.ArrayList<pixel> getVisualData(){
		return pixels;
	}

	public boolean hasInSight(Pixel obj){
		if(pixels.contains(obj)) return true;
	}

	protected void findPrey(){
		/**
		 * use ArrayList.indexOf(obj) & predator.getPreyCoordinate()
		 */
	}
}
