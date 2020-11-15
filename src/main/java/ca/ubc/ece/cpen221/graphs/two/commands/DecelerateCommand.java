package ca.ubc.ece.cpen221.graphs.two.commands;

import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.Util;
import ca.ubc.ece.cpen221.graphs.two.World;
import ca.ubc.ece.cpen221.graphs.two.items.Item;
import ca.ubc.ece.cpen221.graphs.two.items.MoveableItem;
import ca.ubc.ece.cpen221.graphs.two.items.vehicles.Vehicle;

/**
 * A DecelerateCommand is a {@link Command} which represents a {@link Vehicle}
 * moving and Decelerating. This Command moves that vehicle from one space to another and increases
 * its cooldown.
 */
public final class DecelerateCommand implements Command {

    private final Vehicle item;
    private final Location targetLocation;

    /**
     * Construct a {@link DecelerateCommand}, where <code>item</code> is the moving
     * vehicle and <code>targetLocation</code> is the location that
     * <code> item </code> is moving to. The target location must be within
     * <code>item</code>'s moving range and the target location must be empty
     * and valid. If the target location is outside the range of the world, the item is
     * killed. If there is another item in the target location, either that item or this item
     * will be destroyed, depending on their strengths. In the case of equal strengths, both items
     * are destroyed.
     *
     * @param item           the Item that is moving
     * @param targetLocation the location that Item is moving to
     */
    public DecelerateCommand(Vehicle item, Location targetLocation) {
        this.item = item;
        this.targetLocation = targetLocation;
    }

    @Override
    public void execute(World world) throws InvalidCommandException {
        // If the item is dead, then it will not move.
        if (item.isDead()) {
            return;
        }
        if (!Util.isValidLocation(world, targetLocation)) {
            item.loseEnergy(Integer.MAX_VALUE);
            return;
        }
        if (item.getMovingRange() < targetLocation.getDistance(item.getLocation())) {
            throw new InvalidCommandException(
                "Invalid MoveCommand: Target location farther than moving range");
        }
        if (!Util.isLocationEmpty(world, targetLocation)){
            Item block = world.searchSurroundings(targetLocation,0).iterator().next();
            if (block.getStrength() > item.getStrength()){
                item.loseEnergy(Integer.MAX_VALUE);
            }
            else if (block.getStrength() < item.getStrength()){
                block.loseEnergy(Integer.MAX_VALUE);
            }
            else {
                item.loseEnergy(Integer.MAX_VALUE);
                block.loseEnergy(Integer.MAX_VALUE);
            }
        }
        item.decelerate();
        item.moveTo(targetLocation);
    }
}

