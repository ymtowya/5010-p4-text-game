import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import model.graph.Coordinate;
import model.randomhelper.DungeonRandomHelper;

/**
 * A fake random helper for testing only.
 *
 */
public class FakeRandomGenerator extends DungeonRandomHelper {

  /**
   * Init with seed.
   *
   * @param seed the seed
   */
  public FakeRandomGenerator(int seed) {
    super(1);
  }
  
  @Override
  public Coordinate coordChoice(Collection<Coordinate> coors) {
    int index = 0;
    Iterator<Coordinate> iter = coors.iterator();
    for (int i = 0; i < index; i++) {
      iter.next();
    }
    return iter.next();
  }
  
  @Override
  public int randomInt(int left, int right) {
    return right;
  }
  
  @Override
  public void shuffleCoords(List<Coordinate> l) {
    return;
  }

}
