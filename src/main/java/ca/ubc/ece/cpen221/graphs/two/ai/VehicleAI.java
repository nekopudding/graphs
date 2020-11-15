package ca.ubc.ece.cpen221.graphs.two.ai;

import ca.ubc.ece.cpen221.graphs.two.ArenaWorld;
import ca.ubc.ece.cpen221.graphs.two.World;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;
import ca.ubc.ece.cpen221.graphs.two.items.animals.ArenaAnimal;
import ca.ubc.ece.cpen221.graphs.two.items.vehicles.Vehicle;

public interface VehicleAI {

    /**
     * Decides the next action to be taken, given the state of the World and the
     * vehicle.
     *
     * @param world  the current World
     * @param vehicle the vehicle waiting for the next action
     * @return the next action for animal
     */
    Command getNextAction(ArenaWorld world, Vehicle vehicle);

}
