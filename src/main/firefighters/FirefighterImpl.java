package main.firefighters;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import main.api.CityNode;
import main.api.Firefighter;
@NoArgsConstructor
@AllArgsConstructor
public class FirefighterImpl implements Firefighter {
  private int distanceTraveled;
  private CityNode currLocation;

  @Override
  public CityNode getLocation() {
    return currLocation;
  }

  @Override
  public void setLocation(CityNode location) {
    currLocation = location;
  }

  @Override
  public int distanceTraveled() {
    return distanceTraveled;
  }

  @Override
  public void setDistanceTraveled(int distanceTraveled) {
    this.distanceTraveled = distanceTraveled;
  }

}
