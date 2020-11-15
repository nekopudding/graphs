package ca.ubc.ece.cpen221.graphs.two.ai;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.one.AdjacencyListGraph;
import ca.ubc.ece.cpen221.graphs.two.ArenaWorld;
import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.commands.BreedCommand;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;
import ca.ubc.ece.cpen221.graphs.two.commands.MoveCommand;
import ca.ubc.ece.cpen221.graphs.two.commands.TongueCommand;
import ca.ubc.ece.cpen221.graphs.two.commands.WaitCommand;
import ca.ubc.ece.cpen221.graphs.two.items.Item;
import ca.ubc.ece.cpen221.graphs.two.items.animals.ArenaAnimal;

import java.util.Set;
import java.util.TreeSet;

import static ca.ubc.ece.cpen221.graphs.two.Direction.*;


/**
 * Your Frog AI.
 */
public class FrogAI extends AbstractAI {
    /**
     *
     * @param world
     * @param animal
     * @return
     */
    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
        Location pos = animal.getLocation();
        Set<Item> nearby = world.searchSurroundings(animal);
        Set<Location> nearbyLoc = new TreeSet<>();
        Set<Item> nearbyGnats = new TreeSet<>();
        for (Item nearbyItems : nearby) {
            nearbyLoc.add(nearbyItems.getLocation());
            if (nearbyItems.getName().equals("Gnat")){
                nearbyGnats.add(nearbyItems);
            }
        }
        if (animal.getEnergy() > animal.getMinimumBreedingEnergy()) {
            Location n = new Location(pos, NORTH);
            if (!nearbyLoc.contains(n)) {
                return new BreedCommand(animal, n);
            }
            Location e = new Location(pos, EAST);
            if (!nearbyLoc.contains(e)) {
                return new BreedCommand(animal, e);
            }
            Location s = new Location(pos, SOUTH);
            if (!nearbyLoc.contains(s)) {
                return new BreedCommand(animal, s);
            }
            Location w = new Location(pos, WEST);
            if (!nearbyLoc.contains(s)) {
                return new BreedCommand(animal, w);
            }
        }
        Set<Item> closeGnats = edibleGnats(animal, nearbyGnats);
        if (!closeGnats.isEmpty()){
            return new TongueCommand(animal, closeGnats.iterator().next());
        }
        if (!nearbyGnats.isEmpty()){
            Item nearestGnat = getNearestGnat(animal, nearbyGnats);
            return runTowards (nearestGnat, animal);
        }
        return new WaitCommand();
    }

    /**
     *
     * @param animal
     * @param gnats
     * @return
     */
    private Set<Item> edibleGnats (ArenaAnimal animal, Set<Item> gnats){
        Set<Item> closeGnats = new TreeSet<>();
        for (Item gnat : gnats){
            if (animal.getLocation().getDistance(gnat.getLocation()) <= 2){
                closeGnats.add(gnat);
            }
        }
        return closeGnats;
    }

    /**
     *
     * @param animal
     * @param gnats
     * @return
     */
    private Item getNearestGnat(ArenaAnimal animal, Set<Item> gnats){
        int minDist = Integer.MAX_VALUE;
        Item nearestGnat = gnats.iterator().next();
        for (Item gnat : gnats){
            if (gnat.getLocation().getDistance(animal.getLocation()) < minDist){
                minDist = gnat.getLocation().getDistance(animal.getLocation());
                nearestGnat = gnat;
            }
        }
        return nearestGnat;
    }

    /**
     *
     * @param prey
     * @param animal
     * @return
     */
    private Command runTowards(Item prey, ArenaAnimal animal){
        Location preyLoc = prey.getLocation();
        Location currLoc = animal.getLocation();
        Location targetLoc;
        int xDist = Math.abs(currLoc.getX() - preyLoc.getX());
        int yDist = Math.abs(currLoc.getY() - preyLoc.getY());
        if (xDist > yDist){
          targetLoc = moveHoriz(currLoc, preyLoc);
        }
        else{
            targetLoc = moveVert(currLoc,preyLoc);
        }
        return new MoveCommand(animal, targetLoc);
    }

    /**
     *
     * @param currLoc
     * @param prey
     * @return
     */
    private Location moveHoriz(Location currLoc, Location prey){
        Location targetLoc;
        if(currLoc.getX() - prey.getX() > 0){
            targetLoc = new Location(currLoc, WEST);
        }
        else{
            targetLoc = new Location(currLoc, EAST);
        }
        return targetLoc;
    }

    /**
     *
     * @param currLoc
     * @param prey
     * @return
     */
    private Location moveVert(Location currLoc, Location prey){
        Location targetLoc;
        if(currLoc.getY() - prey.getY() > 0){
            targetLoc = new Location(currLoc, SOUTH);
        }
        else{
            targetLoc = new Location(currLoc, NORTH);
        }
        return targetLoc;
    }

}
