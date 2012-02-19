public interface Token{

	public String toString(){
		return  getClass().getName() + '@' + Integer.toHexString(hashCode());
	}


}