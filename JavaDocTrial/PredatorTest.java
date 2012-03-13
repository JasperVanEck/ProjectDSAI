import java.util.ArrayList;
import java.util.Random;

public class PredatorTest {
  
  public static void main(String[] args){
    // Create predator instance
    NaivePredator pred = new NaivePredator(new Pixel(100,100,100));
    PredatorTest tester = new PredatorTest();
    tester.test(pred,20,10);
  }

  /**
   * Generate random landscape of a specific size.
   * @param size The number of random Pixels in the landscape.
   **/
  public ArrayList<Pixel> getRandomLandscape(int size){
    Random r = new Random();
    ArrayList<Pixel> landscape = new ArrayList<Pixel>();
    for(int i = 0; i < size; i++){
      landscape.add(new Pixel(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
    }
    return landscape;
  }

  /**
   * Tests predator on random generated landscapes.
   * @param pred Predator to be tested
   * @param landscapeSize Size of the generated landscape
   * @param numExperiments Number of runs
   */
  public void test(Predator pred, int landscapeSize, int numExperiments){
    // Create test enviroment variables;
    ArrayList<Pixel> landscape;
    Pixel prey;
    Random r = new Random();
    int index, foundLocation;
    for(int i = 0; i < numExperiments; i++){
      // (Re)init
      pred.init();

      // Generate random landscape
      landscape = this.getRandomLandscape(landscapeSize);

      // Select a prey;
      prey = landscape.get(r.nextInt(landscapeSize));

      // Send the predator the new visual input
      pred.updateVisualData(landscape);

      // Provide the predator with the prey visual
      pred.setPreyAppearance(prey);

      // Issue the predator to find the prey
      pred.findPrey();

      // Retrieve the found location
      foundLocation = pred.getPreyCoordinate();

      // Display result
      if(foundLocation > -1){
        System.out.printf(
          "Found prey at %d (%s), which is %s\n",
          foundLocation,
          (
            foundLocation == (landscapeSize/2)
          ?
            "straight ahead"
          :
            (
              foundLocation < (landscapeSize/2)
            ?
              "left"
            :
              "right"
            )
          ),
          (
            landscape.get(foundLocation).equals(prey)
          ?
            "correct"
          :
            "incorrect"
          )
        );
      }else{
        System.out.printf(
          "Did not find prey, next move is %d\n", 
          pred.getNextMove()
        );
      }
    }
  }
}
