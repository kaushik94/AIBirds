package ab.Estimator;

import java.awt.Rectangle;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

import ab.Structures.BuildingManager;
import ab.demo.other.Shot;
import ab.planner.TrajectoryPlanner;
import ab.vision.ABType;
import ab.vision.Vision;
import ab.vision.VisionMBR;

public class BradPitt{

    BuildingManager BM ;
    VisionMBR vision;
    Rectangle sling;
    ABType birdType;
    Shot shot=null;
    Point refPoint=null;

    List<Rectangle> stone = null;
    List<Rectangle> ice = null;
    List<Rectangle> wood = null;
    TrajectoryPlanner tp = new TrajectoryPlanner();

    public BradPitt(BuildingManager _BM, VisionMBR _vision, ABType _birdType){
        vision = _vision;
        sling = vision.findSlingshotMBR();
        BM = _BM;
        birdType = _birdType;
    }

    public Shot plan(Point target){

        ArrayList<Point> pts = tp.estimateLaunchPoint(sling, target);
        System.out.println(pts);
        System.out.println(sling);
        refPoint = tp.getReferencePoint(sling);
        System.out.println(refPoint);
        if(refPoint == null){
            System.out.println("here bro !!");
            return null;
        }
        System.out.println("came here !!");
        return getShot(pts, refPoint, target);
    }

    public Point getRefPoint(){
        return refPoint;
    }
    public Shot getShot(ArrayList<Point> pts, Point refPoint, Point toHit){

        stone = vision.findStonesMBR();
        wood = vision.findWoodMBR();
        ice = vision.findIceMBR();
        int tapInterval=0;
        switch (birdType)
        {

            case RedBird:
                tapInterval = 0;
                findRedShot(pts, tapInterval, toHit);
                break;               // start of trajectory
            case YellowBird:
                tapInterval = 65;
                findYellowShot(pts, tapInterval, toHit);
                break; // 65-90% of the way
            case WhiteBird:
                tapInterval =  70;
                findWhiteShot(pts, tapInterval, toHit);
                break; // 70-90% of the way
            case BlackBird:
                tapInterval =  70;
                findBlackShot(pts, tapInterval, toHit);
                break; // 70-90% of the way
            case BlueBird:
                tapInterval =  65;
                findBlueShot(pts, tapInterval, toHit);
                break; // 65-85% of the way
            default:
                tapInterval =  60;
        }
        while(shot == null)
        {
            try
            {
                wait();
                //getLogger().info("No longer waiting");
            }
            catch (InterruptedException ie)
            {
                //getLogger().info("Wait threw InterruptedException");
            }
        }
        return shot;
    }

    /*public int tryDifferent(){
        List<Rectangle> pigs = vision.findPigsMBR();
        for(Rectangle pig: pigs){
            Point target = new Point(pig.getCenterX(), pig.getCenterY());
            ArrayList<Point> pts = tp.estimateLaunchPoint(sling, target);
            int danger = 0;
            for(Point p: pts){
                List<Point> obs = tp.predictTrajectory(sling, p);
                for(Point x: obs){
                    for(Rectangle r: obstracles){
                        if(r.intersects(x))
                            break;
                    }
                }
            }

        }
        return 1;
    }*/

    public void findRedShot(ArrayList<Point> pts, int tapInterval, Point toHit){

        double min = Integer.MAX_VALUE;
        Point best=null;
        List<Rectangle> Obstracles = stone;
        Obstracles.addAll(ice);
        Obstracles.addAll(wood);

        /*for(Point p: pts){
            int danger = 0;
            for(Rectangle R: Obstracles){
                if(R.contains(p) && !R.contains(toHit))
                    danger++;
            }
            if(danger<=min){
                min = danger;
                best = p;
            }
        }*/

      for(Point p: pts){

            double releaseAngle = tp.getReleaseAngle(sling,
                    p);
          if(releaseAngle < min){
              best = p;
              min = releaseAngle;
          }

        }

        int dx=0, dy=0;
        int tapTime = tp.getTapTime(sling, best, toHit, tapInterval);
        dx = (int)best.x - refPoint.x;
        dy = (int)best.y - refPoint.y;
        shot = new Shot(refPoint.x, refPoint.y, dx, dy, 0, tapTime);
        return;
    }

    public void findBlueShot(ArrayList<Point> pts, int tapInterval, Point toHit){

        double min = Integer.MAX_VALUE;
        Point best=null;
        List<Rectangle> Obstracles = stone;
        Obstracles.addAll(ice);
        Obstracles.addAll(wood);

        /*for(Point p: pts){
            int danger = 0;
            for(Rectangle R: Obstracles){
                if(R.contains(p) && !R.contains(toHit))
                    danger++;
            }
            if(danger<=min){
                min = danger;
                best = p;
            }
        }*/

        for(Point p: pts){

            double releaseAngle = tp.getReleaseAngle(sling,
                    p);
            if(releaseAngle < min){
                best = p;
                min = releaseAngle;
            }

        }

        int dx=0, dy=0;
        int tapTime = tp.getTapTime(sling, best, toHit, tapInterval);
        dx = (int)best.x - refPoint.x;
        dy = (int)best.y - refPoint.y;
        shot = new Shot(refPoint.x, refPoint.y, dx, dy, 0, tapTime);
        return;

    }

    public void findBlackShot(ArrayList<Point> pts, int tapInterval, Point toHit){

        double min = Integer.MAX_VALUE;
        Point best=null;
        List<Rectangle> Obstracles = stone;
        Obstracles.addAll(ice);
        Obstracles.addAll(wood);

        /*for(Point p: pts){
            int danger = 0;
            for(Rectangle R: Obstracles){
                if(R.contains(p) && !R.contains(toHit))
                    danger++;
            }
            if(danger<=min){
                min = danger;
                best = p;
            }
        }*/

        for(Point p: pts){

            double releaseAngle = tp.getReleaseAngle(sling,
                    p);
            if(releaseAngle < min){
                best = p;
                min = releaseAngle;
            }

        }

        int dx=0, dy=0;
        int tapTime = tp.getTapTime(sling, best, toHit, tapInterval);
        dx = (int)best.x - refPoint.x;
        dy = (int)best.y - refPoint.y;
        shot = new Shot(refPoint.x, refPoint.y, dx, dy, 0, tapTime);
        return;
    }

    public void findWhiteShot(ArrayList<Point> pts, int tapInterval, Point toHit){

        double min = Integer.MAX_VALUE;
        Point best=null;
        List<Rectangle> Obstracles = stone;
        Obstracles.addAll(ice);
        Obstracles.addAll(wood);

        /*for(Point p: pts){
            int danger = 0;
            for(Rectangle R: Obstracles){
                if(R.contains(p) && !R.contains(toHit))
                    danger++;
            }
            if(danger<=min){
                min = danger;
                best = p;
            }
        }*/

        for(Point p: pts){

            double releaseAngle = tp.getReleaseAngle(sling,
                    p);
            if(releaseAngle < min){
                best = p;
                min = releaseAngle;
            }

        }

        int dx=0, dy=0;
        int tapTime = tp.getTapTime(sling, best, toHit, tapInterval);
        dx = (int)best.x - refPoint.x;
        dy = (int)best.y - refPoint.y;
        shot = new Shot(refPoint.x, refPoint.y, dx, dy, 0, tapTime);
        return;
    }

    public void findYellowShot(ArrayList<Point> pts, int tapInterval, Point toHit){

        /*if(tryDiffrent() != 0)
            return;*/
        double min = Integer.MAX_VALUE;
        Point best=null;

        System.out.println("hello mofos");
        /*for(Point p: pts){
            int danger = 0;
            for(Rectangle R: Obstracles){
                if(R.contains(p) && !R.contains(toHit))
                    danger++;
            }
            if(danger<=min){
                min = danger;
                best = p;
            }
        }*/

        for(Point p: pts){

            double releaseAngle = tp.getReleaseAngle(sling,
                    p);
            if(releaseAngle < min){
                best = p;
                min = releaseAngle;
            }

        }

        int dx=0, dy=0;
        int tapTime = tp.getTapTime(sling, best, toHit, tapInterval);
        dx = (int)best.x - refPoint.x;
        dy = (int)best.y - refPoint.y;
        shot = new Shot(refPoint.x, refPoint.y, dx, dy, 0, tapTime);
        return;
    }
}