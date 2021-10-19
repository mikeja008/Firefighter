package main.firefighters;

import main.api.City;
import main.api.CityNode;
import main.api.FireDispatch;
import main.api.Firefighter;
import main.api.exceptions.NoFireFoundException;
import main.impls.CityNodeSupplier;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.util.SupplierUtil;

import java.util.*;

public class FireDispatchImpl implements FireDispatch {

  private final City city;
  private final List<Firefighter> firefighters;


  public FireDispatchImpl(City city) {
    this.city = city;
    this.firefighters = new LinkedList<>();
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
    //1. create jgrapht - note: post writing this I realize copying a jgrapht isn't necessary, and a greedy approach probably would be better unless a jgrapht was provided in the first place instead of a 2d array.
    //This ends up being optimized for distance well, but is overall pretty inefficient
    CityNode fireStationLocation = city.getFireStation().getLocation();

    SimpleGraph<CityNode, DefaultWeightedEdge> buildingGraph = new SimpleGraph<>(new CityNodeSupplier(fireStationLocation,burningBuildings),  SupplierUtil.createDefaultWeightedEdgeSupplier(), true);
    new CompleteGraphGenerator<CityNode,DefaultWeightedEdge>(burningBuildings.length+1).generateGraph(buildingGraph,null);

    //build firestation edges
    for (CityNode building : burningBuildings) {
      buildingGraph.setEdgeWeight(fireStationLocation, building, fireStationLocation.getDistanceFromCityNode(building));
    }
    //build fire edges
    for(int i = 0; i < burningBuildings.length; i++){ //n^2
      for(int l = i+1; l < burningBuildings.length; l++){
        buildingGraph.setEdgeWeight(burningBuildings[i],burningBuildings[l],burningBuildings[i].getDistanceFromCityNode(burningBuildings[l]));
      }
    }

    //2. apply algo aka develop firefighters plan


    DijkstraShortestPath<CityNode, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(buildingGraph);
    ShortestPathAlgorithm.SingleSourcePaths<CityNode, DefaultWeightedEdge> paths = dijkstra.getPaths(fireStationLocation);
    HashMap<CityNode, Firefighter> firefighterHashMap = new HashMap<>();
    int q = 0;
    for (CityNode burningBuilding: burningBuildings) {
      GraphPath<CityNode, DefaultWeightedEdge> path = paths.getPath(burningBuilding);
      List<CityNode> vertexList = path.getVertexList();
      Firefighter firefighter = firefighters.get(q);
      boolean foundFirefighter = false;

      for (CityNode vertex : vertexList) {
        try {
          if(!firefighterHashMap.containsKey(vertex)) {
            city.getBuilding(vertex).extinguishFire();
            firefighterHashMap.put(path.getEndVertex(),firefighter);

            firefighterHashMap.put(vertex,firefighter);
          } else {
            firefighter = firefighterHashMap.get(vertex);
            foundFirefighter = true;
          }
        } catch (NoFireFoundException ignored) {
          //firestation
        }
      }
      if(!foundFirefighter){
        firefighter.setDistanceTraveled((int)path.getWeight());
        firefighter.setLocation(path.getEndVertex());
        q++;
      }
    }


    //minimize distance for firefighters
    
    
  }

}
