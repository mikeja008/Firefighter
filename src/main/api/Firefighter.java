package main.api;

public interface Firefighter {

  /**
   * Get the firefighter's current location. Initially, the firefighter should be at the FireStation
   *
   * @return {@link CityNode} representing the firefighter's current location
   */
  CityNode getLocation();

  void setLocation(CityNode location);

  /**
   * Get the total distance traveled by this firefighter. Distances should be represented using TaxiCab
   * Geometry: https://en.wikipedia.org/wiki/Taxicab_geometry AKA go via "edge" route not like a bird
   *
   * @return the total distance traveled by this firefighter
   */
  int distanceTraveled();

  void setDistanceTraveled(int distanceTraveled);

  }
