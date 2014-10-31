package ab.Structures;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Structure {
    public enum Type { ROCK, WOOD, ICE, TNT }
    public Rectangle pos;
    public Type type;
    public double value;
    public ArrayList<Structure> neighbors;
    public Structure(Rectangle position, Type type){
        this.pos = position;
        this.type = type;
        value = 0.0;
        neighbors = new ArrayList<Structure>();
    }

    public String toString(){
        return "[" + type + " (X: " + pos.getCenterX() + ", Y: " + pos.getCenterY() + ") Value: " + value + "]";
    }

    public static void sortHighestValue(List<Structure> s){
        Collections.sort(s, new HighestValue());
    }
    public static void sortLowestXValue(List<Structure> s){
        Collections.sort(s, new LowestXValue());
    }
    public static void sortHighestPart(List<Structure> s){
        Collections.sort(s, new HighestFirst());
    }
    private static class HighestValue implements Comparator<Structure> {
        @Override
        public int compare(Structure arg0, Structure arg1){
            if (arg0.value > arg1.value){
                return -1;
            }else if (arg0.value < arg1.value){
                return 1;
            }else{
                return 0;
            }
        }
    }
    private static class LowestXValue implements Comparator<Structure> {
        @Override
        public int compare(Structure arg0, Structure arg1){
            if (arg0.pos.getCenterX() < arg1.pos.getCenterX()){
                return -1;
            }else if (arg0.pos.getCenterX() > arg1.pos.getCenterX()){
                return 1;
            }else{
                return 0;
            }
        }
    }
    private static class HighestFirst implements Comparator<Structure> {
        @Override
        public int compare(Structure arg0, Structure arg1){
            if (arg0.pos.getCenterY() < arg1.pos.getCenterY()){
                return -1;
            }else if (arg0.pos.getCenterY() > arg1.pos.getCenterY()){
                return 1;
            }else{
                return 0;
            }
        }
    }
}