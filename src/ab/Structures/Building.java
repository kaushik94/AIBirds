package ab.Structures;

import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

public class Building {
    public List<Structure> parts;
    public int pigCount;
    public double highestWeight;
    public Point softPoint;
    public Building(){
        this(new ArrayList<Structure>());
    }
    public Building(List<Structure> parts){
        this.parts = parts;
        pigCount = 0;
        highestWeight = 0.0;
    }

    public void weighParts(){
        Structure.sortHighestPart(parts);
        for(Structure s : parts){
            s.value = 1.0;
            for(Structure n : s.neighbors){
                s.value += n.value;
            }
            if(s.value > highestWeight){
                highestWeight = s.value;
                softPoint = new Point(s.pos.x, s.pos.y);
            }
        }
    }

    public double leftMostX(){
        double leftx = 1000;
        for(Structure s : parts){
            if(s.pos.x < leftx){
                leftx = s.pos.x;
            }
        }
        return leftx;
    }
}