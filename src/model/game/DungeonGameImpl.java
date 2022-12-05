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

public class DungeonGameImpl implements DungeonGame{

  private RandomHelper rh;
  private Player p;
  private DungeonMap m;
  private GameCalculator gc;
  
  public DungeonGameImpl(int r, int c, int conn, boolean isWrap,
      String playerName, double percent, int otyughNum) {
    rh = new DungeonRandomHelper(1);
    gc = new GameCalculatorImpl(rh);
    m = gc.initGame(r, c, conn, isWrap, percent, otyughNum);
    p = new DungeonPlayer(m.getStart().getRow(),
        m.getStart().getCol(), playerName);
    gc.enterGame(m, p);
    System.out.println(gc.getMapString(m, p));
  }

  @Override
  public boolean playerCanWalk(Direction d) {
    if (p.isAlive()) {
      return m.canWalk(getCoord(), d);
    }
    return false;
  }

  @Override
  public String playerMove(Direction d) {
    StringBuilder sb = new StringBuilder();
    gc.walkPlayer(m, p, d);
    Coordinate newCoor = getCoord();
    sb.append("The player has moved to the new location: ");
    sb.append(newCoor);
    sb.append("\n");
    if (m.hasOtyughAt(newCoor)) {
      Otyugh o = m.getOtyughAt(newCoor);
      switch (o.getLifeCondition()) {
        case HEALTHY:
          p.setLife(false);
          sb.append("Momb, Momb, Engurh, you are eaten by the Otyugh\n"
              + "Better luck next time~\n");
          break;
        case HURT:
          Boolean escape = rh.escape();
          p.setLife(escape);
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
    if (m.getEnd().equals(newCoor) && p.isAlive()) {
      sb.append("Congratulations! You've made it to the destination!\n" +
          "See you next time~\n");
    }
    return sb.toString();
  }

  @Override
  public String playerPickArrow() {
    Coordinate c = getCoord();
    ItemHolder ih = m.getHolderAt(c);
    if (ih.hasArrow()) {
      Integer nums = ih.arrowCount();
      gc.pickArrow(m, p);
      return "You picked up " + nums.toString() + " arrows;\n";
    }
    return "There are no arrows found here.\n";
  }

  @Override
  public Integer getArrowCount() {
    return p.arrowCount();
  }

  @Override
  public String playerPickTreasure() {
    Coordinate c = getCoord();
    ItemHolder ih = m.getHolderAt(c);
    Integer count = ih.getTotalTreasure();
    if (count > 0) {
      gc.pickTreasure(m, p);
      return "You picked up " + count.toString() + " treasures;\n";
    }
    return "There are no treasures found here.\n";
  }

  @Override
  public Map<Treasure, Integer> getTreasureMap() {
    return p.getTreasures();
  }

  @Override
  public String shootArrow(Direction d, int caveCounts) {
    gc.shootArrow(m, p, d, caveCounts);
    return "";
  }

  @Override
  public String quit() {
    p.setLife(false);
    return "Game is over.\nSee you next time.\n";
  }

  @Override
  public String getPositionDes() {
    StringBuilder sb = new StringBuilder();
    sb.append(gc.getLocationString(m, getCoord()));
    sb.append("The smell you feel here is : ");
    sb.append(getSmell().name());
    sb.append("\n");
    return sb.toString();
  }

  @Override
  public Smell getSmell() {
    return gc.getSmellAt(m, p);
  }

  @Override
  public boolean isOver() {
    return isKilled() || (p.isAlive() && getCoord().equals(m.getEnd()));
  }

  @Override
  public boolean isKilled() {
    return !p.isAlive();
  }

  @Override
  public Coordinate getCoord() {
    return p.getCoord();
  }

  @Override
  public String playerPick() {
    StringBuilder sb = new StringBuilder();
    sb.append(playerPickTreasure());
    sb.append(playerPickArrow());
    return sb.toString();
  }
  
  
}
