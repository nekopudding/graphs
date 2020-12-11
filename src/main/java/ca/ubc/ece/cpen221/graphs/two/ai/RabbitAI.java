package ca.ubc.ece.cpen221.graphs.two.ai;

import ca.ubc.ece.cpen221.graphs.two.ArenaWorld;
import ca.ubc.ece.cpen221.graphs.two.Direction;
import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.Util;
import ca.ubc.ece.cpen221.graphs.two.commands.*;
import ca.ubc.ece.cpen221.graphs.two.items.Item;
import ca.ubc.ece.cpen221.graphs.two.items.MoveableItem;
import ca.ubc.ece.cpen221.graphs.two.items.animals.ArenaAnimal;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static ca.ubc.ece.cpen221.graphs.two.Direction.*;
import static ca.ubc.ece.cpen221.graphs.two.Direction.WEST;
import static ca.ubc.ece.cpen221.graphs.two.Util.isValidLocation;

/**
 * Your Rabbit AI.
 */
public class RabbitAI extends AbstractAI {

    private int closest = 10; // max number; greater than rabbit's view range
    private int temp;
    private boolean foxFound;

    public RabbitAI() {
    }

    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
        // TODO: Change this. Implement your own AI rules.
        Command command = null;
        Location pos = animal.getLocation();
        Set<Location> adjLocations = new HashSet<>();
        adjLocations.add(new Location(pos, NORTH)); //north
        adjLocations.add(new Location(new Location(pos, NORTH),EAST)); //northeast
        adjLocations.add(new Location(pos, EAST)); //east
        adjLocations.add(new Location(new Location(pos, SOUTH),EAST)); //southeast
        adjLocations.add(new Location(pos, SOUTH)); //south
        adjLocations.add(new Location(new Location(pos, SOUTH),WEST)); //southwest
        adjLocations.add(new Location(pos, WEST)); //west
        adjLocations.add(new Location(new Location(pos, NORTH),WEST)); //northwest
        Set<Item> nearby = world.searchSurroundings(animal);
        Set<Location> nearbyLoc = new HashSet<>();
        for (Item nearbyItem : nearby) {
            nearbyLoc.add(nearbyItem.getLocation());
        }
        //move out of the vehicle path
        command = moveAction(world, animal, nearby, pos, adjLocations);
        if (command != null) {
            return command;
        }

        //breed
        command = breedAction(world, animal, nearbyLoc, pos);
        if (command != null) {
            return command;
        }

        //eat grass
        command = eatAction(world, animal, nearby, pos, adjLocations);
        if (command != null) {
            return command;
        }

        //move towards grass, away from foxes
        command = moveAction2(world, animal, nearby, pos, adjLocations);
        if (command != null) {
            return command;
        }


        return new WaitCommand();
    }

    private Command breedAction(ArenaWorld world, ArenaAnimal animal, Set<Location> nearbyLoc, Location pos) {
        if (animal.getEnergy() > animal.getMinimumBreedingEnergy()) {
            Location n = new Location(pos, NORTH); //north
            if (!nearbyLoc.contains(n) && isValidLocation(world,n)) {
                return new BreedCommand(animal, n);
            }
            Location ne = new Location(n, EAST); //northeast
            if (!nearbyLoc.contains(ne) && isValidLocation(world,ne)) {
                return new BreedCommand(animal, ne);
            }
            Location e = new Location(pos, EAST);
            if (!nearbyLoc.contains(e) && isValidLocation(world,e)) {
                return new BreedCommand(animal, e);
            }
            Location se = new Location(e, SOUTH); //southeast
            if (!nearbyLoc.contains(se) && isValidLocation(world,se)) {
                return new BreedCommand(animal, se);
            }
            Location s = new Location(pos, SOUTH);
            if (!nearbyLoc.contains(s) && isValidLocation(world,s)) {
                return new BreedCommand(animal, s);
            }
            Location sw = new Location(s, WEST); //southwest
            if (!nearbyLoc.contains(sw) && isValidLocation(world,sw)) {
                return new BreedCommand(animal, sw);
            }
            Location w = new Location(pos, WEST);
            if (!nearbyLoc.contains(w) && isValidLocation(world,w)) {
                return new BreedCommand(animal, w);
            }
            Location nw = new Location(w, NORTH); //northwest
            if (!nearbyLoc.contains(nw) && isValidLocation(world,nw)) {
                return new BreedCommand(animal, nw);
            }
        }
        return null;
    }

    private Command eatAction(ArenaWorld world, ArenaAnimal animal, Set<Item> nearby, Location pos, Set<Location> adjLocations) {
        for (Item nearbyItem : nearby) {
            if (adjLocations.contains(nearbyItem.getLocation()) && nearbyItem.getName().equals("Grass"))
                return new EatCommand(animal, nearbyItem);
        }
        return null;
    }

    /**
     * Moves out of vehicle path
     * @param world
     * @param animal
     * @param nearby
     * @param pos
     * @param adjLocations
     * @return
     */
    private Command moveAction(ArenaWorld world, ArenaAnimal animal, Set<Item> nearby, Location pos, Set<Location> adjLocations) {
        Location nearestGrass = null;
        for (Item nearbyItem : nearby) {
            //if grass is in view but is not within reach, get the closest grass tile
            if (!adjLocations.contains(nearbyItem.getLocation()) &&
                    nearbyItem.getName().equals("Grass")
            )
                if (nearestGrass == null ||
                        nearbyItem.getLocation().getDistance(pos) < nearestGrass.getDistance(pos)
                )
                    nearestGrass = nearbyItem.getLocation();
        }
        //get the direction which would lead to the closest distance and move that way
        Location minDistLocation = new Location(pos, NORTH);
        if(nearestGrass != null) {
            for (Location l : adjLocations) {
                if (l.getDistance(nearestGrass) < minDistLocation.getDistance(nearestGrass))
                    minDistLocation = l;
            }
            return new MoveCommand(animal, minDistLocation);
        }
        return null;
    }

    /**
     * Moves towards grass, or away from foxes if there are no grass
     * @param world
     * @param animal
     * @param nearby
     * @param pos
     * @param adjLocations
     * @return
     */
    private Command moveAction2(ArenaWorld world, ArenaAnimal animal, Set<Item> nearby, Location pos, Set<Location> adjLocations) {
        Location nearestGrass = null;
        for (Item nearbyItem : nearby) {
            //if grass is in view but is not within reach, get the closest grass tile
            if (!adjLocations.contains(nearbyItem.getLocation()) &&
                nearbyItem.getName().equals("Grass")
                )
                if (nearestGrass == null ||
                    nearbyItem.getLocation().getDistance(pos) < nearestGrass.getDistance(pos)
                    )
                    nearestGrass = nearbyItem.getLocation();
        }
        //get the direction which would lead to the closest distance and move that way
        Location minDistLocation = new Location(pos, NORTH);
        if(nearestGrass != null) {
            for (Location l : adjLocations) {
                if (l.getDistance(nearestGrass) < minDistLocation.getDistance(nearestGrass))
                    minDistLocation = l;
            }
            return new MoveCommand(animal, minDistLocation);
        }
        return null;
    }
}



/*
Rabbits and foxes move in random directions


priority 1: if rabbit can breed, breed
if the rabbit finds a vehicle, move in the direction perpendicular to the vehicle
priority 2: if rabbit finds grass, move in that direction
priority 3: if rabbit finds a fox, move in the opposite direction


only one action can be executed at once, Breed, Move, Wait, or Eat
use the commands to command movement

move towards the direction of most empty squares, as grass is more likely to spawn there
move away from vehicles and other items as they will destroy grass or prevent grass spawn

rabbits and foxes move at the same speed, we want to maximize breeding

foxes and grass in the same set, then compare directions, pick the grass tile furthest away form
the closest fox

 */
