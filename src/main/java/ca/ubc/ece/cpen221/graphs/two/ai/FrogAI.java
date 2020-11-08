package ca.ubc.ece.cpen221.graphs.two.ai;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;
import ca.ubc.ece.cpen221.graphs.one.AdjacencyListGraph;
import ca.ubc.ece.cpen221.graphs.two.ArenaWorld;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;
import ca.ubc.ece.cpen221.graphs.two.commands.WaitCommand;
import ca.ubc.ece.cpen221.graphs.two.items.animals.ArenaAnimal;

/**
 * Your Frog AI.
 */
public class FrogAI extends AbstractAI {
    private int closest = 5; // max number; greater than fox's view range
    private int temp;
    private boolean foxFound;
    private Graph decisionTree = new AdjacencyListGraph();

    public FrogAI() {
        Vertex
    }

    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
        // TODO: Change this. Implement your own AI to make decisions regarding
        // the next action.
        return new WaitCommand();
    }

}
