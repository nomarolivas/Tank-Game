package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

/**
 * A general concept for an entity in the Tank Game. This includes everything that can move or be interacted with, such
 * as tanks, shells, walls, power ups, etc.
 */
public abstract class Entity {
    public String id;
    public double x;
    public double y;
    public double angle;

    public Entity (String id, double x, double y, double angle) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    /** All entities can move, even if the details of their move logic may vary based on the specific type of Entity. */
    public abstract void move(GameWorld gameWorld);
    public abstract void checkBounds(GameWorld gameWorld);
    public abstract double outBoundsX();
    public abstract double outBoundsY();

    public String getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }

    protected void moveForward(double movementSpeed) {
        x += movementSpeed * Math.cos(angle);
        y += movementSpeed * Math.sin(angle);
    }

    protected void moveBackward(double movementSpeed) {
        x -= movementSpeed * Math.cos(angle);
        y -= movementSpeed * Math.sin(angle);
    }

    protected void turnLeft(double turnSpeed) {
        angle -= turnSpeed;
    }

    protected void turnRight(double turnSpeed) {
        angle += turnSpeed;
    }

    // The following methods will be useful for determining where a shell should be spawned when it
    // is created by this tank. It needs a slight offset so it appears from the front of the tank,
    // even if the tank is rotated. The shell should have the same angle as the tank.

    private static int shellID=0;
    public void fireShell(GameWorld gameWorld){
        Shell shell = new Shell("shell--"+shellID,getShellX(),getShellY(),getShellAngle());
        gameWorld.addTempEntity(shell);
        shellID++;

    }

    public double getShellX() {
        return getX() + Constants.TANK_WIDTH / 2 + 45.0 * Math.cos(getAngle()) - Constants.SHELL_WIDTH / 2;
    }

    public double getShellY() {
        return getY() + Constants.TANK_HEIGHT / 2 + 45.0 * Math.sin(getAngle()) - Constants.SHELL_HEIGHT / 2;
    }

    public double getShellAngle() {
        return getAngle();
    }
}
