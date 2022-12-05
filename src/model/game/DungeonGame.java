package model.game;

import java.util.Map;

import model.Direction;
import model.accessory.Treasure;
import model.creature.Smell;
import model.graph.Coordinate;

public interface DungeonGame {
  boolean playerCanWalk(Direction d);
  
  String playerMove(Direction d);
  
  String playerPickArrow();
  
  Integer getArrowCount();
  
  String playerPickTreasure();
  
  String playerPick();
  
  Map<Treasure, Integer> getTreasureMap();
  
  String shootArrow(Direction d, int caveCounts);
  
  String quit();
  
  String getPositionDes();
  
  Smell getSmell();
  
  boolean isOver();
  
  boolean isKilled();
  
  Coordinate getCoord();
}
