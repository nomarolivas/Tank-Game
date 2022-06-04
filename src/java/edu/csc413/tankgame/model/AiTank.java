package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;
import edu.csc413.tankgame.KeyboardReader;

public class AiTank extends Tank {
    public AiTank(String id, double x, double y, double angle){
        super(id,x,y,angle);

    }

    @Override
    public void move(GameWorld gameWorld){
        moveForward(Constants.TANK_MOVEMENT_SPEED-1.25);
        Entity playerTank= gameWorld.getEntity(Constants.PLAYER_TANK_ID);
        double dx= playerTank.getX()- getX();
        double dy= playerTank.getY()- getY();
        double angleToPlayer = Math.atan2(dy, dx);
        double angleDifference= getAngle()-angleToPlayer;

        angleDifference-= Math.floor(angleDifference /Math.toRadians(360.0) +0.5)
                * Math.toRadians(360.0);

        if (angleDifference < -Math.toRadians(3.0)) {
            turnRight(Constants.TANK_TURN_SPEED);
        } else if (angleDifference > Math.toRadians(3.0)) {
            turnLeft(Constants.TANK_TURN_SPEED);
        }
        coolDown(gameWorld);

    }

    @Override
    public void checkBounds(GameWorld gameWorld){

        if(getX() < Constants.TANK_X_LOWER_BOUND) {
            x= Constants.TANK_X_LOWER_BOUND;
        }

        if(getX() > Constants.TANK_X_UPPER_BOUND){
            x= Constants.TANK_X_UPPER_BOUND;
        }

        if(getY() < Constants.TANK_Y_LOWER_BOUND){
            y= Constants.TANK_Y_LOWER_BOUND;
        }

        if(getY() >  Constants.TANK_Y_UPPER_BOUND){
            y= Constants.TANK_Y_UPPER_BOUND;
        }

    }

    @Override
    public double outBoundsX(){
        return getX()+ Constants.TANK_WIDTH;
    }

    @Override
    public double outBoundsY(){
        return getY() +Constants.TANK_HEIGHT;
    }




    private int timer= 20000;
    private void coolDown(GameWorld gameWorld){
        if(timer %100==0){
            fireShell(gameWorld);
        }
        timer--;
    }
}
