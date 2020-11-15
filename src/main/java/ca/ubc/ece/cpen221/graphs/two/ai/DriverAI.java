package ca.ubc.ece.cpen221.graphs.two.ai;

import ca.ubc.ece.cpen221.graphs.two.ArenaWorld;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;
import ca.ubc.ece.cpen221.graphs.two.commands.WaitCommand;
import ca.ubc.ece.cpen221.graphs.two.items.animals.ArenaAnimal;
import ca.ubc.ece.cpen221.graphs.two.items.vehicles.Vehicle;

/**
 * Driver AI (for vehicles)
 */
public class DriverAI implements VehicleAI {

    @Override
    public Command getNextAction(ArenaWorld world, Vehicle vehicle) {
        return new WaitCommand();
    }


}
