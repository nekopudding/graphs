package ca.ubc.ece.cpen221.graphs.two.ai;

import ca.ubc.ece.cpen221.graphs.two.ArenaWorld;
import ca.ubc.ece.cpen221.graphs.two.Direction;
import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.commands.AccelerateCommand;
import ca.ubc.ece.cpen221.graphs.two.commands.ChangeDirectionCommand;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;
import ca.ubc.ece.cpen221.graphs.two.items.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ca.ubc.ece.cpen221.graphs.two.Direction.*;

/**
 * Driver AI (for vehicles)
 * Driver will try to leave the arena as fast as possible. Will detect the closest edge, and then
 * will begin accelerating towards the exit. As soon as the vehicle reaches the border, it will be
 * destroyed.
 *
 */
public class DriverAI implements VehicleAI {

    @Override
    public Command getNextAction(ArenaWorld world, Vehicle vehicle) {
        Location vehicleLoc = vehicle.getLocation();
        int northDist = vehicleLoc.getY();
        int southDist = world.getHeight() - vehicleLoc.getY();
        int westDist = vehicleLoc.getX();
        int eastDist = world.getWidth() - vehicleLoc.getX();
        Direction min = smallestDirection(northDist,southDist,westDist,eastDist);
        Location targetLoc = new Location(vehicleLoc, min);
        if (vehicle.getDirection() != min){
            return new ChangeDirectionCommand(vehicle, min);
        }
        return new AccelerateCommand(vehicle, targetLoc);
    }

    /**
     * Helper method that takes four distances, and returns the direction that corresponds with
     * the shortest direction.
     * @param n int that represents the distance of the item to the north border (y = 0).
     *         Must be greater than 0 and less than world height.
     * @param s int that represents the distance of the item to the south border (y = world height).
     *         Must be greater than 0 and less than world height.
     * @param w int that represents the distance of the item to the west border (x = 0).
     *          Must be greater than 0 and less than world width.
     * @param e int that represents the distance of the item to the east border (x = world width).
     *          Must be greater than 0 and less than world width.
     * @return NORTH iff n is the smallest distance. SOUTH iff s is the smallest distance.
     * WEST iff w is the smallest distance. EAST iff e is the smallest distance. In case of a tie,
     * the priority in descending order is NORTH, SOUTH, WEST, EAST.
     */
    private Direction smallestDirection(int n, int s, int w, int e){
        List<Integer> sorter = new ArrayList<>();
        sorter.add(n);
        sorter.add(s);
        sorter.add(w);
        sorter.add(e);
        Collections.sort(sorter);
        if (sorter.get(0) == n) {
            return NORTH;
        }
        if (sorter.get(0) == s) {
            return SOUTH;
        }
        if (sorter.get(0) == w) {
            return WEST;
        }
        if (sorter.get(0) == e) {
            return EAST;
        }
        return NORTH;
    }

}
