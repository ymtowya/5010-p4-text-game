import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import controller.DungeonGameController;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;
import model.Direction;
import model.accessory.ItemHolder;
import model.creature.DungeonOtyugh;
import model.creature.LifeCondition;
import model.creature.Otyugh;
import model.creature.Smell;
import model.dungeon.DungeonMap;
import model.game.DungeonGameImpl;
import model.graph.Coordinate;
import model.randomhelper.DungeonRandomHelper;
import model.randomhelper.RandomHelper;
import org.junit.Before;
import org.junit.Test;

/**
 * This tests the dungeon game.
 *
 */
public class DungeonGameTest {
  
  private DungeonGameImpl game1;
  private DungeonGameImpl game2;
  private DungeonMap map1;

  /**
   * Set up the test.
   *
   * @throws IOException when errored
   */
  @Before
  public void setUp() throws IOException {
    game1 = new DungeonGameImpl(5, 6, 2, false, "Tonny", 0.7, 1);
    game2 = new DungeonGameImpl(6, 7, 1, true, "We", 0.2, 6);
  }

  @Test
  public void testOtyughAtEnd() {
    DungeonMap m = game1.getMap();
    Otyugh o = m.getOtyughAt(m.getEnd());
    assertFalse(o == null);
  }
  
  @Test
  public void testOtyughs() {
    DungeonMap m = game2.getMap();
    Set<Otyugh> os = m.getOtyughs();
    assertTrue(os.size() == 6);
  }
  
  @Test
  public void testSmell1() {
    map1 = game1.getMap();
    Coordinate end = map1.getEnd();
    map1.getOtyughAt(map1.getEnd());
    game1.movePlayerTo(end);
    for (Direction d : Direction.values()) {
      if (game1.playerCanWalk(d)) {
        game1.playerMove(d);
        break;
      }
    }
    assertEquals(Smell.STRONG, game1.getSmell());
  }
  
  @Test
  public void testSmell2() {
    map1 = game1.getMap();
    Coordinate end = map1.getEnd();
    game1.movePlayerTo(end);
    for (Direction d : Direction.values()) {
      if (game1.playerCanWalk(d)) {
        game1.playerMove(d);
        break;
      }
    }
    Otyugh o = new DungeonOtyugh(game1.getCoord());
    Set<Otyugh> os = new HashSet<>();
    os.add(o);
    map1.addOtyugh(os);
    assertEquals(Smell.STRONG, game1.getSmell());
  }
  
  @Test
  public void testArrow1() {
    map1 = game1.getMap();
    boolean tmp = false;
    for (int i = 0; i < map1.getRows(); ++i) {
      for (int j = 0; j < map1.getCols(); ++j) {
        Coordinate c = new Coordinate(i, j);
        ItemHolder ih = map1.getHolderAt(c);
        if (ih.hasArrow()) {
          tmp = true;
          break;
        }
      }
    }
    assertTrue(tmp);
  }
  
  @Test
  public void testArrow2() {
    map1 = game1.getMap();
    int cnt = game1.getArrowCount();
    for (int i = 0; i < map1.getRows(); ++i) {
      for (int j = 0; j < map1.getCols(); ++j) {
        Coordinate c = new Coordinate(i, j);
        ItemHolder ih = map1.getHolderAt(c);
        if (ih.hasArrow()) {
          game1.movePlayerTo(c);
          game1.playerPick();
        }
      }
    }
    assertTrue(game1.getArrowCount() > cnt);
  }
  
  @Test
  public void testArrow3() {
    map1 = game1.getMap();
    Coordinate end = map1.getEnd();
    map1.getOtyughAt(map1.getEnd());
    game1.movePlayerTo(end);
    Direction tmp = Direction.SOUTH;
    for (Direction d : Direction.values()) {
      if (game1.playerCanWalk(d)) {
        game1.playerMove(d);
        tmp = Direction.oppositeDir(d);
        break;
      }
    }
    String s = game1.shootArrow(tmp, 1);
    assertTrue(s != null);
  }
  
  @Test
  public void testOtyugh() {
    map1 = game1.getMap();
    Coordinate end = map1.getEnd();
    map1.getOtyughAt(map1.getEnd());
    game1.movePlayerTo(end);
    Direction tmp = Direction.SOUTH;
    for (Direction d : Direction.values()) {
      if (game1.playerCanWalk(d)) {
        game1.playerMove(d);
        tmp = Direction.oppositeDir(d);
        break;
      }
    }
    game1.playerMove(tmp);
    assertTrue(game1.isKilled());
  }
  
  @Test
  public void testEscape() {
    boolean hasEscape = false;
    boolean hasFailed = false;
    RandomHelper rh = new DungeonRandomHelper(1);
    for (int i = 0; i < 100; ++i) {
      if (rh.escape()) {
        hasEscape = true;
      } else {
        hasFailed = true;
      }
    }
    assertTrue(hasEscape);
    assertTrue(hasFailed);
  }
  
  @Test
  public void testOtyugh2() {
    map1 = game1.getMap();
    Coordinate end = map1.getEnd();
    final Otyugh o = map1.getOtyughAt(map1.getEnd());
    game1.movePlayerTo(end);
    Direction tmp = Direction.SOUTH;
    for (Direction d : Direction.values()) {
      if (game1.playerCanWalk(d)) {
        game1.playerMove(d);
        tmp = Direction.oppositeDir(d);
        break;
      }
    }
    game1.shootArrow(tmp, 1);
    game1.shootArrow(tmp, 1);
    game1.playerMove(tmp);
    assertTrue(o.getLifeCondition() == LifeCondition.DEAD);
    assertTrue(!game1.isKilled());
  }
  
  @Test
  public void testMove() {
    map1 = game1.getMap();
    Coordinate end = map1.getEnd();
    game1.movePlayerTo(end);
    Direction tmp = Direction.SOUTH;
    for (Direction d : Direction.values()) {
      if (game1.playerCanWalk(d)) {
        tmp = d;
        break;
      }
    }
    game1.playerMove(tmp);
    assertFalse(end.equals(game1.getCoord()));
    game1.playerMove(Direction.oppositeDir(tmp));
    assertTrue(end.equals(game1.getCoord()));
  }
  
  @Test
  public void testPick() {
    map1 = game1.getMap();
    for (int i = 0; i < map1.getRows(); ++i) {
      for (int j = 0; j < map1.getCols(); ++j) {
        Coordinate c = new Coordinate(i, j);
        ItemHolder ih = map1.getHolderAt(c);
        if (ih.hasArrow()) {
          game1.movePlayerTo(new Coordinate(i, j));
          break;
        }
      }
    }
    int c = game1.getArrowCount();
    game1.playerPick();
    assertTrue(c < game1.getArrowCount());
  }
  
  @Test
  public void testShoot() {
    map1 = game1.getMap();
    Coordinate end = map1.getEnd();
    map1.getOtyughAt(map1.getEnd());
    game1.movePlayerTo(end);
    Direction tmp = Direction.SOUTH;
    for (Direction d : Direction.values()) {
      if (game1.playerCanWalk(d)) {
        game1.playerMove(d);
        tmp = Direction.oppositeDir(d);
        break;
      }
    }
    String s = game1.shootArrow(tmp, 1);
    assertTrue(s != null);
  }
  
  @Test
  public void testQuit() {
    map1 = game1.getMap();
    Coordinate end = map1.getEnd();
    map1.getOtyughAt(map1.getEnd());
    game1.movePlayerTo(end);
    for (Direction d : Direction.values()) {
      if (game1.playerCanWalk(d)) {
        game1.playerMove(d);
        break;
      }
    }
    game1.quit();
    assertTrue(game1.isOver());
  }
  
  @Test
  public void testIoExcept() {
    String test = "6\n8s\n8\n5\ny\n10\nsa\n4\nq\n";
    Reader reader = new StringReader(test);
    BufferedReader br = new BufferedReader(reader);
    Writer writer = new StringWriter();
    BufferedWriter bw = new BufferedWriter(writer);
    DungeonGameController c = new DungeonGameController(br, bw);
    c.run();
  }
  
  @Test
  public void testError() {
    String test = "6\n89\n8\n5\ny\n10\nsa\n4\nq\n";
    Reader reader = new StringReader(test);
    BufferedReader br = new BufferedReader(reader);
    Writer writer = new StringWriter();
    BufferedWriter bw = new BufferedWriter(writer);
    DungeonGameController c = new DungeonGameController(br, bw);
    c.run();
  }

}
