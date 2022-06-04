package edu.csc413.tankgame.model;


import edu.csc413.tankgame.Constants;

public class Walls extends Entity{
    private static int wallId=0;
    public Walls(double x, double y, double angle){
        super("wall"+wallId, x, y, angle);
        wallId++;
    }

    @Override
    public void move(GameWorld gameWorld){
    }

    @Override
    public void checkBounds(GameWorld gameWorld){
    }

    @Override
    public double outBoundsX(){
        return 0;
    }

    @Override
    public double outBoundsY(){
        return 0;
    }

}
