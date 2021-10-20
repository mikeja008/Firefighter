package main.scenarios;

import main.api.*;
import main.api.exceptions.FireproofBuildingException;
import main.impls.CityImpl;
import org.junit.Assert;
import org.junit.Test;

public class ComplexScenarios {
  @Test
  public void singleFire() throws FireproofBuildingException {
    City basicCity = new CityImpl(15, 15, new CityNode(0, 0));
    FireDispatch fireDispatch = basicCity.getFireDispatch();

    CityNode fireNode = new CityNode(14, 14);
    Pyromaniac.setFire(basicCity, fireNode);

    fireDispatch.setFirefighters(1);
    fireDispatch.dispatchFirefighers(fireNode);
    Assert.assertFalse(basicCity.getBuilding(fireNode).isBurning());
    Firefighter firefighter = fireDispatch.getFirefighters().get(0);
    Assert.assertEquals(firefighter.distanceTraveled(),28);
    Assert.assertEquals(firefighter.getLocation(),fireNode);

  }

  @Test
  public void singleFireDistanceTraveledDiagonal() throws FireproofBuildingException {
    City basicCity = new CityImpl(30, 30, new CityNode(15, 15));
    FireDispatch fireDispatch = basicCity.getFireDispatch();

    // Set fire on opposite corner from Fire Station
    CityNode fireNode = new CityNode(0, 0);
    Pyromaniac.setFire(basicCity, fireNode);

    fireDispatch.setFirefighters(1);
    fireDispatch.dispatchFirefighers(fireNode);

    Firefighter firefighter = fireDispatch.getFirefighters().get(0);
    Assert.assertEquals(30, firefighter.distanceTraveled());
    Assert.assertEquals(fireNode, firefighter.getLocation());
  }


  @Test
  public void complexQuadFire() throws FireproofBuildingException {
    City basicCity = new CityImpl(100, 100, new CityNode(99, 99));
    FireDispatch fireDispatch = basicCity.getFireDispatch();


    CityNode[] fireNodes = {
            new CityNode(0, 0),
            new CityNode(10, 10),
            new CityNode(97, 97),
            new CityNode(98, 97),

    };
    Pyromaniac.setFires(basicCity, fireNodes);

    fireDispatch.setFirefighters(3);
    fireDispatch.dispatchFirefighers(fireNodes);

    Firefighter firefighter = fireDispatch.getFirefighters().get(0);
    Assert.assertEquals(99*2+10*2, firefighter.distanceTraveled());
    Assert.assertEquals(fireNodes[1], firefighter.getLocation());
    Assert.assertFalse(basicCity.getBuilding(fireNodes[1]).isBurning());
    Assert.assertFalse(basicCity.getBuilding(fireNodes[0]).isBurning());

    Firefighter firefighter2 = fireDispatch.getFirefighters().get(1);
    Assert.assertEquals(5, firefighter2.distanceTraveled());
    Assert.assertEquals(fireNodes[3], firefighter2.getLocation());
    Assert.assertFalse(basicCity.getBuilding(fireNodes[2]).isBurning());
    Assert.assertFalse(basicCity.getBuilding(fireNodes[3]).isBurning());

    Firefighter firefighter3 = fireDispatch.getFirefighters().get(2);
    Assert.assertEquals(0, firefighter3.distanceTraveled());
    Assert.assertEquals(new CityNode(99,99), firefighter3.getLocation());


  }


}
