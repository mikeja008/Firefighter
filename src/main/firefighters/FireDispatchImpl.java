package main.firefighters;

import main.api.City;
import main.api.CityNode;
import main.api.FireDispatch;
import main.api.Firefighter;
import main.impls.CityNodeSupplier;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.util.SupplierUtil;

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
    firefighters = new Firefighter[numFirefighters];
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
    //1. create jgrapht - note: post writing this I realize copying a jgrapht isn't necessary, and a greedy approach probably would be better unless a jgrapht was provided in the first place instead of a 2d array.
    //This ends up being optimized for distance well, but is overall pretty inefficient
    CityNode fireStationLocation = city.getFireStation().getLocation();

    SimpleGraph<CityNode, DefaultWeightedEdge> buildingGraph = new SimpleGraph<>(new CityNodeSupplier(fireStationLocation,burningBuildings),  SupplierUtil.createDefaultWeightedEdgeSupplier(), true);
    new CompleteGraphGenerator<CityNode,DefaultWeightedEdge>(burningBuildings.length+1).generateGraph(buildingGraph,null);

    for(int i = 0; i < burningBuildings.length; i++){
      for(int l = i+1; l < burningBuildings.length; l++){
        buildingGraph.setEdgeWeight(burningBuildings[i],burningBuildings[l],burningBuildings[i].getDistanceFromCityNode(burningBuildings[l]));
      }
    }

    //2. apply algo


    double[] burningBuildingWeights = new double[burningBuildings.length];
    DijkstraShortestPath<CityNode, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(buildingGraph);
    ShortestPathAlgorithm.SingleSourcePaths<CityNode, DefaultWeightedEdge> paths = dijkstra.getPaths(fireStationLocation);

    for (int i = 0; i < burningBuildings.length; i++) {
      CityNode burningBuilding = burningBuildings[i];
      burningBuildingWeights[i] = paths.getWeight(burningBuilding);
      System.out.println(burningBuildingWeights[i]);
    }

    //kruskal
  }

}
