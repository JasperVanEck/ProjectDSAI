public abstract class Predator implements RobotBrain, VisualObject{
	private Pixel predator;
	Pixel prey = new Prey(new Pixel(100,100,100));


	public Predator(Pixel appearance){
		setVisualAppearance(appearance);
	}

	public Pixel getVisualAppearance(){
		return predator;
	}

	public void setVisualAppearance(Pixel visual){
		predator = visual;
	}

	protected void setPreyAppearance(Pixel prey){
		this.prey.setVisualAppearance(prey);
	}

	protected Pixel getPreyAppearance(){
		prey.getVisualAppearance();
	}


	public abstract boolean hasInSight(Pixel obj);

	protected abstract void findPrey();

	protected abstract int getPreyCoordinate();

	protected abstract void setPreyCoordinate(int coord);
}
