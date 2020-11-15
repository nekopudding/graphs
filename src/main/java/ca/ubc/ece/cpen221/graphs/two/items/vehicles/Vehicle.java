package ca.ubc.ece.cpen221.graphs.two.items.vehicles;

import ca.ubc.ece.cpen221.graphs.two.Actor;
import ca.ubc.ece.cpen221.graphs.two.Direction;
import ca.ubc.ece.cpen221.graphs.two.items.MoveableItem;

public interface Vehicle extends MoveableItem, Actor {

    /** Increase the speed of the vehicle in the direction it is going in.
     *
     */
    void accelerate();

    /** Decrease the speed of the vehicle int he direction it is going in.
     *
     *
     */
    void decelerate();

    /**
     * Get the maximum "turn speed" of the vehicle.
     * @return int that represents the max speed of the vehicle such that it can still change
     * direction.
     */
    int getTurnSpeed();


    /**
     *
     * @param dir represents the desired direction of the vehicle. Should be either
     *            NORTH, EAST, WEST, or SOUTH.
     * @return returns true if it modifies the vehicle's current direction.
     * returns false if it cannot make the direction change.
     * Can change direction iff the vehicle's current direction and dir are
     * not opposites (e.g. NORTH and SOUTH).
     *
     */
    boolean changeDirection(Direction dir);

    /**
     * getter method to get the vehicle's current direction.
     * @return Direction object.
     */
    Direction getDirection();

    /**
     * getter method to get the vehicle's minimum cooldown.
     * @return int representing this.MINIMUM_COOLDOWN
     */
    int getMinCooldown();

    /**
     * getter method to get the vehicle's deceleration.
     * @return int representing this.DECELERATION
     */
    int getDeceleration();

}
