package ca.ubc.ece.cpen221.graphs.two.ai;

import ca.ubc.ece.cpen221.graphs.two.*;
import ca.ubc.ece.cpen221.graphs.two.commands.*;
import ca.ubc.ece.cpen221.graphs.two.items.Item;
import ca.ubc.ece.cpen221.graphs.two.items.animals.ArenaAnimal;

import java.util.HashSet;
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
    private Direction lastDirection = null;

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

        Set<Direction> directions = new HashSet<>();
        directions.add(NORTH);
        directions.add(EAST);
        directions.add(SOUTH);
        directions.add(WEST);

        Set<Item> nearby = world.searchSurroundings(animal);
        Set<Location> nearbyLoc = new HashSet<>();
        for (Item nearbyItem : nearby) {
            //System.out.println(nearbyItem);
            nearbyLoc.add(nearbyItem.getLocation());
        }
        //move out of the vehicle path
        command = moveVehicle(world, animal, nearby, pos, adjLocations);
        if (command != null) {
            return command;
        }

        //breed
        command = breedAction(world, animal, nearbyLoc, pos, adjLocations);
        if (command != null) {
            return command;
        }
        //eat grass
        if (animal.getEnergy() < animal.getMaxEnergy()) {
            command = eatGrass(world, animal, nearby, pos, adjLocations);
            if (command != null) {
                return command;
            }
        }

        //move towards grass
        command = moveGrass(world, animal, nearby, pos, directions, nearbyLoc);
        if (command != null) {
            return command;
        }
        //move away from foxes
        command = moveFox(world, animal, nearby, pos, nearbyLoc);
        if (command != null) {
            return command;
        }

        //move away from other rabbits
        command = moveRabbit(world, animal, nearby, pos, nearbyLoc);
        if (command != null) {
            return command;
        }

        //move in the same direction as it did last time
        Location loc = null;
        if (lastDirection != null)
            loc = new Location(pos, lastDirection);
        if (loc != null && !nearbyLoc.contains(loc) && isValidLocation(world, loc)) {
            return new MoveCommand(animal, loc);
        }
        //move in a random direction
        Location randLoc = Util.getRandomEmptyAdjacentLocation((World) world, pos);
        if (randLoc != null) {
            return new MoveCommand(animal, randLoc);
        }
        return new WaitCommand();
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

    private Command eatGrass(ArenaWorld world, ArenaAnimal animal, Set<Item> nearby, Location pos, Set<Location> adjLocations) {
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
     */
    private Command moveVehicle(ArenaWorld world, ArenaAnimal animal, Set<Item> nearby, Location pos, Set<Location> adjLocations) {
        return null;
    }

    /**
     * Moves towards grass if there is grass
     */
    private Command moveGrass(ArenaWorld world, ArenaAnimal animal, Set<Item> nearby, Location pos, Set<Direction> directions, Set<Location> nearbyLoc) {
        Location nearestGrass = null;
        for (Item nearbyItem : nearby) {
            //if grass is in view but is not within reach, get the closest grass tile
            if (nearbyItem.getName().equals("grass"))
                if (nearestGrass == null ||
                    nearbyItem.getLocation().getDistance(pos) < nearestGrass.getDistance(pos)
                    )
                    nearestGrass = nearbyItem.getLocation();
        }
        //move towards the nearest grass
        if(nearestGrass != null) {
            return moveTowards(world, nearestGrass, pos, nearbyLoc, animal, directions);
        }
        return null;
    }

    /**
     * Move Away from rabbits if there are rabbits
     */
    private Command moveRabbit(ArenaWorld world, ArenaAnimal animal, Set<Item> nearby, Location pos, Set<Location> nearbyLoc) {
        Location nearestRabbit = null;
        for (Item nearbyItem : nearby) {
            //if grass is in view but is not within reach, get the closest rabbit tile
            if (nearbyItem.getName().equals("Rabbit"))
                if (nearestRabbit == null ||
                        nearbyItem.getLocation().getDistance(pos) < nearestRabbit.getDistance(pos)
                )
                    nearestRabbit = nearbyItem.getLocation();
        }
        //move away from the nearest rabbit
        if(nearestRabbit != null) {
            return moveAway(world, nearestRabbit, pos, nearbyLoc, animal);
        }
        return null;
    }

    private Command moveFox(ArenaWorld world, ArenaAnimal animal, Set<Item> nearby, Location pos, Set<Location> nearbyLoc) {
        Location nearestFox = null;
        for (Item nearbyItem : nearby) {
            //if grass is in view but is not within reach, get the closest rabbit tile
            if (nearbyItem.getName().equals("Fox"))
                if (nearestFox == null ||
                        nearbyItem.getLocation().getDistance(pos) < nearestFox.getDistance(pos)
                )
                    nearestFox = nearbyItem.getLocation();
        }
        //move away from the nearest fox
        if(nearestFox != null) {
            return moveAway(world, nearestFox, pos, nearbyLoc, animal);
        }
        return null;
    }

    /**
     * Move towards the targetted location if not blocked, else move perpendicularly towards it.
     * if all movement towards it is blocked, return null
     */
    private Command moveTowards(ArenaWorld world, Location target, Location pos, Set<Location> nearbyLoc, ArenaAnimal animal, Set<Direction> directions) {
        Location closestTo = null;
        for (Direction d : directions) {
            Location l = new Location(pos, d);
            if (closestTo == null || l.getDistance(target) < closestTo.getDistance(target)) {
                if (isValidLocation(world, l) && !nearbyLoc.contains(l) &&
                        pos.getDistance(target) > l.getDistance(target)) {
                    lastDirection = d;
                    closestTo = l;
                }
            }
        }
        if (closestTo != null)
            return new MoveCommand(animal, closestTo);
        return null;
    }

    /**
     * Move away from the target object
     */
    private Command moveAway(ArenaWorld world, Location target, Location pos, Set<Location> nearbyLoc, ArenaAnimal animal) {
        Location farthestAway = null;
        //move in the opposite direction from the target
        Location north = new Location(pos, NORTH);
        Location south = new Location(pos, SOUTH);
        Location east = new Location(pos, EAST);
        Location west = new Location(pos, WEST);
        if (NORTH == getDirectionTowards(pos, target)) {
            if (isValidLocation(world, south) && !nearbyLoc.contains(south) &&
                    pos.getDistance(target) < south.getDistance(target)) {
                lastDirection = SOUTH;
                farthestAway = south;
            }
        }
        else if (SOUTH == getDirectionTowards(pos, target)) {
            if (isValidLocation(world, north) && !nearbyLoc.contains(north) &&
                    pos.getDistance(target) < north.getDistance(target)) {
                lastDirection = NORTH;
                farthestAway = north;
            }
        }
        else if (EAST == getDirectionTowards(pos, target)) {
            if (isValidLocation(world, west) && !nearbyLoc.contains(west) &&
                    pos.getDistance(target) < west.getDistance(target)) {
                lastDirection = WEST;
                farthestAway = west;
            }
        }
        else if (WEST == getDirectionTowards(pos, target)) {
            if (isValidLocation(world, east) && !nearbyLoc.contains(east) &&
                    pos.getDistance(target) < east.getDistance(target)) {
                lastDirection = EAST;
                farthestAway = east;
            }
        }
        if (farthestAway != null)
            return new MoveCommand(animal, farthestAway);
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

want to move away from other rabbits in order to find food

 */
