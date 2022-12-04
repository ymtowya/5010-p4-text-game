package model.game;

import java.util.Map;

import model.Direction;
import model.accessory.Treasure;
import model.creature.Smell;
import model.graph.Coordinate;

public interface DungeonGame {
  boolean playerCanWalk(Direction d);
  
  void playerMove(Direction d);
  
  void playerPickArrow();
  
  Integer getArrowCount();
  
  void playerPickTreasure();
  
  Map<Treasure, Integer> getTreasureMap();
  
  String shootArrow(Direction d, int caveCounts);
  
  void quit();
  
  String getPositionDes();
  
  Smell getSmell();
  
  boolean isOver();
  
  boolean isKilled();
  
  Coordinate getCoord();
}
