import static org.junit.Assert.assertTrue;

import java.io.IOException;
import model.Direction;
import model.creature.DungeonPlayer;
import model.creature.LifeCondition;
import model.creature.Otyugh;
import model.creature.Player;
import model.dungeon.DungeonMap;
import model.game.GameCalculator;
import model.game.GameCalculatorImpl;
import model.graph.Coordinate;
import model.randomhelper.RandomHelper;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the game calculator.
 *
 *
 */
public class GameCalculatorTest {

  private GameCalculator gc;
  private DungeonMap dm;
  private Player p1;
  
  /**
   * Set up the game and maps.
   *
   * @throws Exception when errored
   */
  @Before
  public void setUp() throws IOException {
    RandomHelper r = new FakeRandomGenerator(1);
    gc = new GameCalculatorImpl(r);
    dm = gc.initGame(5, 8, 3, false, 0.5, 1);
    p1 = new DungeonPlayer(0, 0, "Terry");
  }
  
  /**
   * This uses the fake random helper, so the image of
   * the map should always be like this:
   * Otyugh is at (4, 3).
   *
   *    0 --(9)-- 0 -- 0 -- 0 --(9)--(9)--(9) 
        |    |                   |    |       
        |    |                   |    |       
        0 --(9)-- 0 -- 0 -- 0 --(9)--(9)--(9) 
                                 |            
                                 |            
        0 -- 0 -- 0 -- 0 -- 0 --(0)-- 0 --(0) 
        |                                     
        |                                     
       (0)-- 0 -- 0 -- 0 --(0)-- 0 -- 0 --(0) 
        |                   |                 
        |                   |                 
        0 -- 0 -- 0 --(M]   0 -- 0 -- 0 --[*) 
   *
   */
  @Test
  public void testArrow() {
    gc.enterGame(dm, p1);
    System.out.println(gc.getMapString(dm, p1));
    p1.setRow(2);
    p1.setCol(0);
    Otyugh otyugh = dm.getOtyughAt(new Coordinate(4, 3));
    assertTrue(otyugh.getLifeCondition() == LifeCondition.HEALTHY);
    gc.shootArrow(dm, p1, Direction.SOUTH, 3); // wrong number of caves
    assertTrue(otyugh.getLifeCondition() == LifeCondition.HEALTHY);
    gc.shootArrow(dm, p1, Direction.SOUTH, 2);
    assertTrue(otyugh.getLifeCondition() == LifeCondition.HURT);
    gc.shootArrow(dm, p1, Direction.SOUTH, 2);
    assertTrue(otyugh.getLifeCondition() == LifeCondition.DEAD);
  }
  

}
