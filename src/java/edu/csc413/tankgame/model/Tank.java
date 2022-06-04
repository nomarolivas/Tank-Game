package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

/** Entity class representing all tanks in the game. */
public abstract class Tank extends Entity {
    // TODO: Implement. A lot of what's below is relevant to all Entity types, not just Tanks. Move it accordingly to
    //       Entity class.

    public Tank(String id, double x, double y, double angle) {
        super(id,x,y,angle);
    }
}
