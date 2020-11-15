package ca.ubc.ece.cpen221.graphs.two.commands;

import ca.ubc.ece.cpen221.graphs.two.Direction;
import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.Util;
import ca.ubc.ece.cpen221.graphs.two.World;
import ca.ubc.ece.cpen221.graphs.two.items.MoveableItem;
import ca.ubc.ece.cpen221.graphs.two.items.vehicles.Vehicle;

import static ca.ubc.ece.cpen221.graphs.two.Direction.*;

/**
 * A MoveCommand is a {@link Command} which represents a {@link MovableItem}
 * moving. This Command moves that Item from one space in the world to another.
 */
public final class ChangeDirectionCommand implements Command {

    private final Vehicle item;
    private Direction targetDirection;

    /**
     * Construct a ChangeDirectionCommand, where <code>item</code> is the moving
     * item and <code>targetDir</code> is the direction that
     * <code> item </code> is turning to. The item must have a high enough cooldown to be able
     * to turn. If the item cannot turn due to the target direction being opposite or the same
     * as the item's current direction, the ChangeDirectionCommand will turn the item 90degrees
     * counter clockwise.
     *
     * @param item      The vehicle to change direction of. Must have a low enough speed (high
     *                  enough cooldown) to be able to turn according to vehicle's MAX_TURN_SPEED.
     * @param targetDir the desired direction for the car to turn to.
     */
    public ChangeDirectionCommand(Vehicle item, Direction targetDir) {
        this.item = item;
        this.targetDirection = targetDir;
    }

    @Override
    public void execute(World world) throws InvalidCommandException {
        // If the item is dead, then it will not move.
        if (item.isDead()) {
            return;
        }
        if (item.getTurnSpeed() > item.getCoolDownPeriod()) {
            throw new InvalidCommandException(
                "Invalid Change Turn Command: Vehicle moving too fast.");
        }
        if(!item.changeDirection(targetDirection)){
            switch (targetDirection){
                case NORTH : item.changeDirection(WEST);
                case WEST : item.changeDirection(SOUTH);
                case SOUTH : item.changeDirection(EAST);
                case EAST : item.changeDirection(NORTH);
            }

        }
    }
}

