package ca.ubc.ece.cpen221.graphs.two.ai;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.one.AdjacencyListGraph;
import ca.ubc.ece.cpen221.graphs.two.ArenaWorld;
import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.Util;
import ca.ubc.ece.cpen221.graphs.two.commands.BreedCommand;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;
import ca.ubc.ece.cpen221.graphs.two.commands.MoveCommand;
import ca.ubc.ece.cpen221.graphs.two.commands.TongueCommand;
import ca.ubc.ece.cpen221.graphs.two.commands.WaitCommand;
import ca.ubc.ece.cpen221.graphs.two.items.Item;
import ca.ubc.ece.cpen221.graphs.two.items.animals.ArenaAnimal;

import java.util.HashSet;
import java.util.Set;


import static ca.ubc.ece.cpen221.graphs.two.Direction.*;


/**
 * Your Frog AI.
 */
public class FrogAI extends AbstractAI {

    public FrogAI() {

    }
    /**
     *
     * @param world
     * @param animal
     * @return new BreedCommand() at an empty location (prioritize n,e,s,w) if frog has enough
     * energy. If not, new TongueCommand() at first Gnat found within two blocks of frog. If no such
     * gnats exist, new MoveCommand() towards first Gnat found within frog's vision range. If no
     * such gnats are around, generates a random MoveCommand() if random MoveCommand() is valid,
     * returns that MoveCommand. If not, returns WaitCommand().
     */
    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
        Location pos = animal.getLocation();
        Set<Item> nearby = world.searchSurroundings(animal);
        Set<Location> nearbyLoc = new HashSet<>();
        Set<Item> nearbyGnats = new HashSet<>();
        for (Item nearbyItems : nearby) {
            nearbyLoc.add(nearbyItems.getLocation());
            if (nearbyItems.getName().equals("Gnat")){
                nearbyGnats.add(nearbyItems);
            }
        }
        if (animal.getEnergy() > animal.getMinimumBreedingEnergy()) {
            Location n = new Location(pos, NORTH);
            if (!nearbyLoc.contains(n) && isValidLoc(n,world)) {
                return new BreedCommand(animal, n);
            }
            Location e = new Location(pos, EAST);
            if (!nearbyLoc.contains(e) && isValidLoc(n,world)) {
                return new BreedCommand(animal, e);
            }
            Location s = new Location(pos, SOUTH);
            if (!nearbyLoc.contains(s) && isValidLoc(n,world)) {
                return new BreedCommand(animal, s);
            }
            Location w = new Location(pos, WEST);
            if (!nearbyLoc.contains(s) && isValidLoc(n,world)) {
                return new BreedCommand(animal, w);
            }
        }
        Set<Item> closeGnats = edibleGnats(animal, nearbyGnats);
        if (!closeGnats.isEmpty()){
            return new TongueCommand(animal, closeGnats.iterator().next());
        }
        if (!nearbyGnats.isEmpty()){
            Item nearestGnat = getNearestGnat(animal, nearbyGnats);
            return runTowards (nearestGnat, animal, nearby, world);
        }
        Location wander = new Location(pos, Util.getRandomDirection());
        if (!containsItem(wander, nearby) && isValidLoc(wander, world)){
            return new MoveCommand(animal, wander);
        }
        return new WaitCommand();
    }

    /** Scans visible gnats from frog's position, and finds which ones are close enough to be
     * grabbed by the frog's tongue (distance less than or equal to 2).
     *
     * @param animal The frog that wants to eat the gnats.
     * @param gnats The set of items that the frog can see. Every item must be a gnat.
     * @return Set of items such that every item i is a Gnat, and is within the frog's tongue
     * distance (2).
     */
    private Set<Item> edibleGnats (ArenaAnimal animal, Set<Item> gnats){
        Set<Item> closeGnats = new HashSet<>();
        for (Item gnat : gnats){
            if (animal.getLocation().getDistance(gnat.getLocation()) <= 2){
                closeGnats.add(gnat);
            }
        }
        return closeGnats;
    }

    /**
     * Out of all visible gnats, this helper method will return the nearest one.
     *
     * @param animal the Frog that sees/eats the gnats.
     * @param gnats The set of items that the frog can see. Every item must be a gnat.
     * @return Item that represents the gnat which is nearest to the animal. If there is a tie,
     *       will return the one found first.
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
     * Helper method to find the correct location that the frog should move to.
     *
     * @param prey Prey item that the frog is chasing. Must be in the world.
     * @param animal Frog that has this FrogAI.
     * @return MoveCommand() such that the location is i) valid ii) does not contain another item
     * iii) decreases the distance between animal and prey. If no such location exists, returns
     * a WaitCommand().
     */
    private Command runTowards(Item prey, ArenaAnimal animal, Set<Item> nearby, ArenaWorld world){
        Location preyLoc = prey.getLocation();
        Location currLoc = animal.getLocation();
        Location targetLoc;
        int xDist = Math.abs(currLoc.getX() - preyLoc.getX());
        int yDist = Math.abs(currLoc.getY() - preyLoc.getY());
        if (xDist > yDist){
          targetLoc = moveHoriz(currLoc, preyLoc);
          if (containsItem(targetLoc, nearby)){
              targetLoc = moveVert(currLoc,preyLoc);
          }
        }
        else{
            targetLoc = moveVert(currLoc,preyLoc);
            if (containsItem(targetLoc, nearby)){
                targetLoc = moveHoriz(currLoc,preyLoc);
            }
        }
        if (containsItem(targetLoc, nearby) || !isValidLoc(targetLoc, world)){
            return new WaitCommand();
        }
        return new MoveCommand(animal, targetLoc);
    }

    /**
     * Helper method to find if the frog should move west or east.
     * @param currLoc
     * @param prey
     * @return Location object that represents the ideal adjacent location for the frog to move
     * such that it approaches a gnat and the frog moves horizontally.
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
     * Helper method to find if the frog should move north or south.
     * @param currLoc
     * @param prey
     * @return Location object that represents the ideal adjacent location for the frog to move
     * such that it approaches a gnat and the frog moves vertically.
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

    /**
     * Helper method that checks if specified location has any other items already on it.
     * @param loc the Location to be checked. Must be within the World.
     * @param nearby the Set of Items such that it contains all Items within the Frog's vision
     *               range.
     * @return false iff there is no Item i such that i.getLocation() returns the same Location as
     * loc. true if otherwise.
     */
    private boolean containsItem(Location loc, Set<Item> nearby){
        for (Item i : nearby){
            if (i.getLocation().equals(loc)){
                return true;
            }
        }
        return false;
    }

    /**
     *  Helper method that checks if specified location is within the world's borders.
     * @param loc the Location to be checked.
     * @param world the ArenaWorld that the Frog who has this FrogAI lives in.
     * @return true iff loc.getX() is greater than or equal to 0 and less than world.getWidth()
     * AND loc.getY() is greater than or equal to 0 and less than world.getHeight().
     */
    private boolean isValidLoc(Location loc, ArenaWorld world){
        if (loc.getX() >= world.getWidth()){
            return false;
        }
        if (loc.getX() < 0){
            return false;
        }
        if (loc.getY() >= world.getHeight()){
            return false;
        }
        if (loc.getY() < 0){
            return false;
        }
        return true;
    }

}
