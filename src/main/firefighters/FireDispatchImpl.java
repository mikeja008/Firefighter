package main.firefighters;

import main.api.*;

import java.util.Arrays;
import java.util.List;

public class FireDispatchImpl implements FireDispatch {

  private City city;
  private Firefighter[] firefighters;


  public FireDispatchImpl(City city) {
    this.city = city;
  }

  @Override
  public void setFirefighters(int numFirefighters) {
    CityNode fireStationLocation = city.getFireStation().getLocation();
    firefighters = new Firefighter[numFirefighters-1];
    for(int i = 0; i < numFirefighters; i++){
      firefighters[i] = new FirefighterImpl(0,fireStationLocation);
    }
  }

  @Override
  public List<Firefighter> getFirefighters() {
    return Arrays.stream(firefighters).toList();
  }

  @Override
  public void dispatchFirefighers(CityNode... burningBuildings) {
    CityNode fireStationLocation = city.getFireStation().getLocation();


  }

}
