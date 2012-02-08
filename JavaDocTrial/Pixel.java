public class Pixel {
  private int r;
  private int g;
  private int b;

  public Pixel(int r, int g, int b){
    this.setRed(r);
    this.setGreen(g);
    this.setBlue(b);
  }

  private void setRed(int r){
    this.r = r;
  }

  public int getRed(){
    return this.r;
  }
  
  private void setGreen(int g){
    this.g = g;
  }
  
  public int getGreen(){
    return this.g;
  }
  
  private void setBlue(int b){
    this.b = b;
  }
  
  public int getBlue(){
    return this.b;
  }

  /**
   * Returns whether obj is equal to this pixel.
   * Two pixels are equal iff the RGB components
   * are equal. 
   *
   * @param obj Object to compare with
   * @return True if obj is equal to this pixel, else false.
   **/
  @Override
  public boolean equals(Object obj){
    // If method provided with self
    if(this == obj) return true;

    // If object is not a (subclass of) Pixel
    if(!(obj instanceof Pixel)) return false;
  
    // Else cast obj to a Pixel and
    // check if the RGB components are the same
    return (
      ((Pixel) obj).getRed() == this.getRed() &&
      ((Pixel) obj).getGreen() == this.getGreen() &&
      ((Pixel) obj).getBlue() == this.getBlue()
    );
  }
  
}
