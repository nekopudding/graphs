package ca.ubc.ece.cpen221.graphs.two.ai;

import ca.ubc.ece.cpen221.graphs.two.*;
import ca.ubc.ece.cpen221.graphs.two.commands.*;
import ca.ubc.ece.cpen221.graphs.two.items.Item;
import ca.ubc.ece.cpen221.graphs.two.items.MoveableItem;
import ca.ubc.ece.cpen221.graphs.two.items.animals.ArenaAnimal;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static ca.ubc.ece.cpen221.graphs.two.Direction.*;
import static ca.ubc.ece.cpen221.graphs.two.Direction.WEST;
import static ca.ubc.ece.cpen221.graphs.two.Util.*;

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
        adjLocations.add(new Location(pos, EAST)); //east
        adjLocations.add(new Location(pos, SOUTH)); //south
        adjLocations.add(new Location(pos, WEST)); //west
        Set<Item> nearby = world.searchSurroundings(animal);
        Set<Location> nearbyLoc = new HashSet<>();
        for (Item nearbyItem : nearby) {
            //System.out.println(nearbyItem);
            nearbyLoc.add(nearbyItem.getLocation());
        }
        //move out of the vehicle path
        /*command = moveAction(world, animal, nearby, pos, adjLocations);
        if (command != null) {
            return command;
        }*/

        //breed
        command = breedAction(world, animal, nearbyLoc, pos, adjLocations);
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
            //return command;
        }
        while (true) {
            Direction dir = Util.getRandomDirection();
            Location l = new Location(pos, dir);
            if (isValidLocation(world, l) && !nearbyLoc.contains(l))
                return new MoveCommand(animal, l);
        }
        //return new WaitCommand();
    }

    private Command breedAction(ArenaWorld world, ArenaAnimal animal, Set<Location> nearbyLoc, Location pos, Set<Location> adjLocations) {
        if (animal.getEnergy() > animal.getMinimumBreedingEnergy()) {
            for (Location l : adjLocations) {
                if (!nearbyLoc.contains(l) && isValidLocation(world, l)) {
                    return new BreedCommand(animal, l);
                }
            }
        }
        return null;
    }

    private Command eatAction(ArenaWorld world, ArenaAnimal animal, Set<Item> nearby, Location pos, Set<Location> adjLocations) {
        for (Item nearbyItem : nearby) {
            if (adjLocations.contains(nearbyItem.getLocation()) &&
                nearbyItem.getName().equals("grass")
            )
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
                    nearbyItem.getName().equals("grass")
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
                nearbyItem.getName().equals("grass")
                )
                if (nearestGrass == null ||
                    nearbyItem.getLocation().getDistance(pos) < nearestGrass.getDistance(pos)
                    )
                    nearestGrass = nearbyItem.getLocation();
        }
        //get the direction which would lead to the closest distance and move that way
        if(nearestGrass != null) {
            Direction dir = getDirectionTowards(pos, nearestGrass);
            return new MoveCommand(animal, new Location(pos, dir));
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
question - is it better to run away from foxes, or ignore it completely?

only eat if energy is less than max energy

you can't move diagonally --> diagonal doesn't count as adjacent

 */
