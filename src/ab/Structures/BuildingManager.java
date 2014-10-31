package ab.Structures;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import ab.vision.VisionMBR;

/**
 *
 *
 */
public class BuildingManager {
    public List<Rectangle> pigs;
    public List<Rectangle> stones;
    public List<Rectangle> wood;
    public List<Rectangle> ice;
    public List<Rectangle> tnt;

    public List<Building> buildings;
    public List<Building> impBuildings;
    public List<Structure> parts;

    @Override
    public String toString(){
        String str = "the following are important buildings !!\n";
        return str;
    }

    public BuildingManager(VisionMBR vision) {
        pigs = vision.findPigsMBR();
        stones = vision.findStonesMBR();
        wood = vision.findWoodMBR();
        ice = vision.findIceMBR();
        tnt = vision.findTNTsMBR();

        parts = new ArrayList<Structure>();
        for (Rectangle s : stones)
            parts.add(new Structure(s, Structure.Type.ROCK));
        for (Rectangle s : wood)
            parts.add(new Structure(s, Structure.Type.WOOD));
        for (Rectangle s : ice)
            parts.add(new Structure(s, Structure.Type.ICE));
        connectPieces(parts);
        buildings = findBuildings(parts);
        weighBuildings();
        impBuildings = importantBuildings();
    }

    public void weighBuildings() {
        weighBuildings(buildings);
    }

    public void weighBuildings(List<Building> buildings) {
        for (Building b : buildings) {
            b.weighParts();
        }
    }

    public List<Building> importantBuildings() {
        List<Building> targets = new ArrayList<Building>();
        for (Rectangle pig : pigs) {
            Rectangle bigPig = (Rectangle) pig.clone();
            bigPig.grow((int)(pig.height * 1.5), (int)(pig.width * 1.5));
            for (Building b : buildings) {
                for (Structure s : b.parts) {
                    if (bigPig.intersects(s.pos)) {
                        if (!targets.contains(b)) {
                            targets.add(b);
                        }
                        b.pigCount++;
                        break;
                    }
                }
            }
        }
        return targets;
    }

    private List<Building> findBuildings(List<Structure> pieces) {
        // The List of found structures
        List<Building> buildings = new ArrayList<Building>();
        // This list holds parts we discover to be part of the building (the
        // neighbors of parts in the building)
        List<Structure> neighborhood = new ArrayList<Structure>();
        // Structure parts we've visited before to avoid reapplying the same
        // pieces
        List<Structure> visited = new ArrayList<Structure>();

        for (Structure s : pieces) {
            if (visited.contains(s)) {
                continue;
            }
            Building building = new Building();
            neighborhood.add(s);
            while (!neighborhood.isEmpty()) { // Iterate until we've run out of
                // neighbors to expand
                Structure component = neighborhood.remove(0);
                if (!visited.contains(component)) {
                    if (!building.parts.contains(component)) {
                        building.parts.add(component);
                        neighborhood.addAll(component.neighbors);
                    }
                    visited.add(component);
                }
            }
            buildings.add(building);
        }
        return buildings;
    }

    private void connectPieces(List<Structure> pieces) {
        //Map<Rectangle, Integer> intersections = new Map<Rectangle, Integer>();
        for (Structure piece : pieces) {
            Rectangle enlarged = (Rectangle) piece.pos.clone();
            enlarged.grow(5, 5);

            for (Structure piece2 : pieces) {
                if (piece != piece2) {
                    if (enlarged.intersects(piece2.pos) && !piece.neighbors.contains(piece2)) {
                       /* Rectangle intersection = enlarged.intersection(piece2.pos);
                        for(Rectangle i : intersections){
                            if(i.intersects(intersection)){

                            }
                        }*/
                        piece.neighbors.add(piece2);
                        piece2.neighbors.add(piece);
                    }
                }
            }
        }
    }

    private void findStressPoints(List<Structure> peices){


    }
}