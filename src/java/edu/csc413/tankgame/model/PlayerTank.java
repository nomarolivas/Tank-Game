package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;
import edu.csc413.tankgame.KeyboardReader;

public class PlayerTank extends Tank{
    public PlayerTank(String id, double x, double y, double angle){
        super(id,x,y,angle);
    }

    @Override
    public void move(GameWorld gameWorld){
        KeyboardReader keyboard = KeyboardReader.instance();

        if(keyboard.upPressed()){
            moveForward(Constants.TANK_MOVEMENT_SPEED);
        }
        if(keyboard.downPressed()){
            moveBackward(Constants.TANK_MOVEMENT_SPEED);
        }
        if(keyboard.leftPressed()){
            turnLeft(Constants.TANK_TURN_SPEED);
        }
        if(keyboard.rightPressed()){
            turnRight(Constants.TANK_TURN_SPEED);
        }
        if(keyboard.spacePressed()){
            coolDown(gameWorld);
        }

        checkBounds(gameWorld);

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

    private int timer= 100000;
    private void coolDown(GameWorld gameWorld){
        if(timer %20==0){
            fireShell(gameWorld);
        }
        timer--;
    }


}
