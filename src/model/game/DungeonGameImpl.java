package model.game;

import java.util.Map;
import model.Direction;
import model.accessory.ItemHolder;
import model.accessory.Treasure;
import model.creature.DungeonPlayer;
import model.creature.Otyugh;
import model.creature.Player;
import model.creature.Smell;
import model.dungeon.DungeonMap;
import model.graph.Coordinate;
import model.randomhelper.DungeonRandomHelper;
import model.randomhelper.RandomHelper;

/**
 * This is one implementation of the dungeon game interface.
 *
 *
 */
public class DungeonGameImpl implements DungeonGame {

  private RandomHelper rh;
  private Player pl;
  private DungeonMap ma;
  private GameCalculator gc;
  
  /**
   * Initialize the game.
   *
   * @param r row
   * @param c column
   * @param conn connectivity
   * @param isWrap is wrapped
   * @param playerName player name
   * @param percent percent of treasure caves
   * @param otyughNum number of otyughs
   */
  public DungeonGameImpl(int r, int c, int conn, boolean isWrap,
      String playerName, double percent, int otyughNum) {
    rh = new DungeonRandomHelper(1);
    gc = new GameCalculatorImpl(rh);
    ma = gc.initGame(r, c, conn, isWrap, percent, otyughNum);
    pl = new DungeonPlayer(ma.getStart().getRow(),
        ma.getStart().getCol(), playerName);
    gc.enterGame(ma, pl);
  }
  
  public DungeonMap getMap() {
    return this.ma;
  }
  
  public void movePlayerTo(Coordinate c) {
    pl.setRow(c.getRow());
    pl.setCol(c.getCol());
  }

  @Override
  public boolean playerCanWalk(Direction d) {
    if (pl.isAlive()) {
      return ma.canWalk(getCoord(), d);
    }
    return false;
  }

  @Override
  public String playerMove(Direction d) {
    StringBuilder sb = new StringBuilder();
    gc.walkPlayer(ma, pl, d);
    Coordinate newCoor = getCoord();
    sb.append("The player has moved to the new location: ");
    sb.append(newCoor);
    sb.append("\n");
    if (ma.hasOtyughAt(newCoor)) {
      Otyugh o = ma.getOtyughAt(newCoor);
      switch (o.getLifeCondition()) {
        case HEALTHY:
          pl.setLife(false);
          sb.append("Momb, Momb, Engurh, you are eaten by the Otyugh\n"
              + "Better luck next time~\n");
          break;
        case HURT:
          Boolean escape = rh.escape();
          pl.setLife(escape);
          sb.append("Whoooops! You run into a hurt otyugh.\n");
          if (escape) {
            sb.append("Luckily, you escape the cave successfully!\n");
          } else {
            sb.append("However, it still managed to eat you.\n"
                + "Better luck next time~\n");
          }
          break;
        default:
          break;
      }
    }
    if (ma.getEnd().equals(newCoor) && pl.isAlive()) {
      sb.append("Congratulations! You've made it to the destination!\n"
          + "See you next time~\n");
    }
    return sb.toString();
  }

  @Override
  public String playerPickArrow() {
    Coordinate c = getCoord();
    ItemHolder ih = ma.getHolderAt(c);
    if (ih.hasArrow()) {
      Integer nums = ih.arrowCount();
      gc.pickArrow(ma, pl);
      return "You picked up " + nums.toString() + " arrows;\n";
    }
    return "There are no arrows found here.\n";
  }

  @Override
  public Integer getArrowCount() {
    return pl.arrowCount();
  }

  @Override
  public String playerPickTreasure() {
    Coordinate c = getCoord();
    ItemHolder ih = ma.getHolderAt(c);
    Integer count = ih.getTotalTreasure();
    if (count > 0) {
      gc.pickTreasure(ma, pl);
      return "You picked up " + count.toString() + " treasures;\n";
    }
    return "There are no treasures found here.\n";
  }

  @Override
  public Map<Treasure, Integer> getTreasureMap() {
    return pl.getTreasures();
  }

  @Override
  public String shootArrow(Direction d, int caveCounts) {
    return gc.shootArrow(ma, pl, d, caveCounts);
  }

  @Override
  public String quit() {
    pl.setLife(false);
    return "Game is over.\nSee you next time.\n";
  }

  @Override
  public String getPositionDes() {
    StringBuilder sb = new StringBuilder();
    sb.append(gc.getLocationString(ma, getCoord()));
    sb.append("The smell you feel here is : ");
    sb.append(getSmell().name());
    sb.append("\n");
    sb.append("There are ");
    sb.append(pl.arrowCount());
    sb.append(" arrows in your pocket now.\n");
    return sb.toString();
  }

  @Override
  public Smell getSmell() {
    return gc.getSmellAt(ma, pl);
  }

  @Override
  public boolean isOver() {
    return isKilled() || (pl.isAlive() && getCoord().equals(ma.getEnd()));
  }

  @Override
  public boolean isKilled() {
    return !pl.isAlive();
  }

  @Override
  public Coordinate getCoord() {
    return pl.getCoord();
  }

  @Override
  public String playerPick() {
    StringBuilder sb = new StringBuilder();
    sb.append(playerPickTreasure());
    sb.append(playerPickArrow());
    return sb.toString();
  }

  @Override
  public String result() {
    if (!isKilled() && isOver()) {
      return "Result: You win!\n";
    }
    return "Result: You didn't win.\n";
  }

  @Override
  public String playerString() {
    return gc.getPlayerString(pl);
  }
  
  
}
