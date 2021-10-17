package main.firefighters;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.api.CityNode;
import main.api.Firefighter;
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class FirefighterImpl implements Firefighter {
  private int distanceTraveled;
  private CityNode currLocation;

  @Override
  public CityNode getLocation() {
    return currLocation;
  }

  @Override
  public int distanceTraveled() {
    return distanceTraveled;
  }
}
