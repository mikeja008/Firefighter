package main.firefighters;

import main.api.*;
import main.api.exceptions.NoFireFoundException;

import java.util.ArrayList;
import java.util.List;

public class FireDispatchImpl implements FireDispatch {

  private final City city;
  private final List<Firefighter> firefighters;


  public FireDispatchImpl(City city) {
    this.city = city;
    this.firefighters = new ArrayList<>();
  }

  @Override
  public void setFirefighters(int numFirefighters) {
    CityNode fireStationLocation = city.getFireStation().getLocation();
    for(int i = 0; i < numFirefighters; i++){
      firefighters.add(new FirefighterImpl(0,fireStationLocation));
    }
  }

  @Override
  public List<Firefighter> getFirefighters() {
    return firefighters;
  }

  @Override
  public void dispatchFirefighers(CityNode... burningBuildings) {
    if(firefighters.size() > 0) {
      for (CityNode burningBuilding : burningBuildings) {
        dispatchNearestFirefighter(burningBuilding);
        Building building = city.getBuilding(burningBuilding);
        if(building.isBurning()) {
          try {
            building.extinguishFire();
          } catch (NoFireFoundException ignored) {}
        }
      }
    }
  }

  private void dispatchNearestFirefighter(CityNode burningBuilding){
    Firefighter nearestFirefighter = null;
    int minPathDist = Integer.MAX_VALUE;

    for(Firefighter firefighter: firefighters){
      int currDist = firefighter.getLocation().getDistanceFromCityNode(burningBuilding);
      if(currDist < minPathDist){
        nearestFirefighter = firefighter;
        minPathDist = currDist;
      }
    }
    if (nearestFirefighter != null) {
      nearestFirefighter.setLocation(burningBuilding);
      nearestFirefighter.setDistanceTraveled(nearestFirefighter.distanceTraveled()+minPathDist);
    }
  }

}
