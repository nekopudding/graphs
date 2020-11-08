package ca.ubc.ece.cpen221.graphs.two.ai;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;
import ca.ubc.ece.cpen221.graphs.one.AdjacencyListGraph;
import ca.ubc.ece.cpen221.graphs.two.ArenaWorld;
import ca.ubc.ece.cpen221.graphs.two.Direction;
import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;
import ca.ubc.ece.cpen221.graphs.two.commands.MoveCommand;
import ca.ubc.ece.cpen221.graphs.two.commands.WaitCommand;
import ca.ubc.ece.cpen221.graphs.two.items.Item;
import ca.ubc.ece.cpen221.graphs.two.items.animals.ArenaAnimal;

import java.awt.ItemSelectable;
import java.util.Set;

import static ca.ubc.ece.cpen221.graphs.two.Direction.*;


/**
 * Your Frog AI.
 */
public class FrogAI extends AbstractAI {
    private int closest = 5; // max number; greater than fox's view range
    private int temp;
    private boolean foxFound;
    private Graph decisionTree = new AdjacencyListGraph();

    public FrogAI() {

    }

    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
        foxFound = false;
        Location pos = animal.getLocation();
        Set<Item> nearby = world.searchSurroundings(animal);
        Item closestFox = null;
        int minDist = animal.getViewRange() + 1;
        for (Item i : nearby){
            if (i.getName().equals("Fox")){
                if (i.getLocation().getDistance(animal.getLocation()) < minDist){
                    minDist = i.getLocation().getDistance(animal.getLocation());
                    closestFox = i;
                    foxFound = true;
                }
                return runAway(i, animal, world);
            }
        }
        if (foxFound){
            return runAway(closestFox, animal, world);
        }

        return new WaitCommand();
    }

    private Command runAway(Item i, ArenaAnimal a, ArenaWorld world){
        Location predator = i.getLocation();
        Location currLoc = a.getLocation();
        Location targetLoc;
        int xDist = Math.abs(currLoc.getX() - predator.getX());
        int yDist = Math.abs(currLoc.getY() - predator.getY());
        if (xDist > yDist){
          targetLoc = moveVert(currLoc, predator, world);
        }
        else{
            targetLoc = moveHoriz(currLoc,predator,world);
        }
        return new MoveCommand(a, targetLoc);
    }

    private Location moveHoriz(Location currLoc, Location predator, ArenaWorld world){
        Location targetLoc;
        if(currLoc.getX() - predator.getX() > 0){
            if(currLoc.getX() == world.getWidth()){
                if (currLoc.getY() == world.getHeight()){
                    return new Location(currLoc, NORTH);
                }
                if (currLoc.getY() == 0){
                    return new Location(currLoc, SOUTH);
                }
                return moveVert(currLoc,predator,world);
            }
            targetLoc = new Location(currLoc, EAST);
        }
        else{
            if(currLoc.getX() == 0){
                if (currLoc.getY() == world.getHeight()){
                    return new Location(currLoc, NORTH);
                }
                if (currLoc.getY() == 0){
                    return new Location(currLoc, SOUTH);
                }
                return moveVert(currLoc,predator,world);
            }
            targetLoc = new Location(currLoc, WEST);
        }
        return targetLoc;
    }

    private Location moveVert(Location currLoc, Location predator, ArenaWorld world){
        Location targetLoc;
        if(currLoc.getY() - predator.getY() > 0){
            if(currLoc.getY() == world.getWidth()){
                return moveHoriz(currLoc,predator,world);
            }
            targetLoc = new Location(currLoc, SOUTH);
        }
        else{
            if(currLoc.getY() == 0){
                return moveHoriz(currLoc,predator,world);
            }
            targetLoc = new Location(currLoc, NORTH);
        }
        return targetLoc;
    }

}
