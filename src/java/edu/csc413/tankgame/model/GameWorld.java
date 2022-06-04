package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

import java.util.*;

/**
 * GameWorld holds all of the model objects present in the game. GameWorld tracks all moving entities like tanks and
 * shells, and provides access to this information for any code that needs it (such as GameDriver or entity classes).
 */
public class GameWorld {
    // TODO: Implement. There's a lot of information the GameState will need to store to provide contextual information.
    //       Add whatever instance variables, constructors, and methods are needed.
    private List<Entity> entityList;
    private List<Entity> tempList;

    public GameWorld() {
        // TODO: Implement.
        entityList= new ArrayList<>();
        tempList= new ArrayList<>();
    }

    /** Returns a list of all entities in the game. */
    public List<Entity> getEntities() {
        // TODO: Implement.
        return entityList;
    }

    /** Adds a new entity to the game. */
    public void addEntity(Entity entity) {
        // TODO: Implement.
        entityList.add(entity);
    }

    /** Returns the Entity with the specified ID. */
    public Entity getEntity(String id) {
        // TODO: Implement.
        for(Entity entity: entityList){
            if(entity.getId().equals(id)){
                return entity;
            }
        }
        return null;
    }

    /** Removes the entity with the specified ID from the game. */
    public void removeEntity(String id) {
        // TODO: Implement.
        entityList.remove(getEntity(id));
    }

    public void addTempEntity(Entity entity){
        tempList.add(entity);
    }

    public List<Entity> getTempEntities(){
        return tempList;
    }

    public void clearTempList(){
         tempList.clear();
    }

    public boolean collisionDetection(Entity entity1, Entity entity2){
        if(entity1==entity2){
            return false;
        }
        if( !(entity2.getX() > entity1.outBoundsX()) && !(entity1.getX() > entity2.outBoundsX()) &&
                !(entity2.getY() > entity1.outBoundsY()) && !(entity1.getY() > entity2.outBoundsY())){
            return true;
        }
        return false;
    }

    public Entity iterator(int x) {
        int counter = 0;
        for (Entity entity : entityList) {
            if(counter++ == x)
                return entity;
        }
        return null;
    }

    public void handleCollision(Entity entity1, Entity entity2){
            double moveLeft= entity1.outBoundsX()-entity2.getX();
            double moveRight= entity2.outBoundsX()-entity1.getX();
            double moveUp= entity1.outBoundsY()-entity2.getY();

        if(entity1 instanceof Tank && entity2 instanceof AiTank){

            if(entity1.outBoundsX() < entity2.getX()){
                entity1.turnLeft(Constants.TANK_TURN_SPEED+moveLeft);
                entity2.turnRight(Constants.TANK_TURN_SPEED+moveRight);
            }
            else if(entity1.getX()> entity2.outBoundsX()){
                entity1.turnRight(Constants.TANK_TURN_SPEED+moveRight);
            }
            else if(entity1.outBoundsY() < entity2.getY()){
                entity1.moveBackward(Constants.TANK_MOVEMENT_SPEED+moveUp);

            }


        }else if(entity1 instanceof Tank && entity2 instanceof Shell){

        }else if(entity1 instanceof Shell && entity2 instanceof Tank){

        }else if(entity1 instanceof Tank && entity2 instanceof Walls){

        }else if(entity1 instanceof Shell && entity2 instanceof Walls){

        }
    }
}
