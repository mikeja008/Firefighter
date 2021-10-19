package main.impls;

import main.api.CityNode;

import java.util.function.Supplier;

public class CityNodeSupplier implements Supplier<CityNode> {
    CityNode fireStation;
    CityNode[] burningBuildings;
    int i = 0;

    public CityNodeSupplier(CityNode fireStation, CityNode[] burningBuildings) {
        this.fireStation = fireStation;
        this.burningBuildings = burningBuildings;
    }

    public CityNode get(){
        if(i < burningBuildings.length) {
            return burningBuildings[i++];
        }
        else {
            return fireStation;
        }

    }

}
