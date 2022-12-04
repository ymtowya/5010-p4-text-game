package model.game;

import java.util.Map;
import java.util.Set;

import model.Direction;
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
  }

  @Override
  public boolean playerCanWalk(Direction d) {
    if (p.isAlive()) {
      return m.canWalk(getCoord(), d);
    }
    return false;
  }

  @Override
  public void playerMove(Direction d) {
    gc.walkPlayer(m, p, d);
    Coordinate newCoor = getCoord();
    if (m.hasOtyughAt(newCoor)) {
      Otyugh o = m.getOtyughAt(newCoor);
      switch (o.getLifeCondition()) {
        case HEALTHY:
          p.setLife(false);
          break;
        case HURT:
          p.setLife(rh.escape());
          break;
        default:
          break;
      }
    }
  }

  @Override
  public void playerPickArrow() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Integer getArrowCount() {
    return p.arrowCount();
  }

  @Override
  public void playerPickTreasure() {
    gc.pickTreasure(m, p);
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
  public void quit() {
    p.setLife(false);
  }

  @Override
  public String getPositionDes() {
    return gc.getLocationString(m, getCoord());
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
  
  
}
