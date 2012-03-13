public class Prey implements VisualObject{
	private Pixel prey;

	public Prey(Pixel appearance){
		setVisualAppearance(appearance);
	}

	public Pixel getVisualAppearance(){
		return prey;
	}

	public void setVisualAppearance(Pixel visual){
		prey = visual;
	}
}
