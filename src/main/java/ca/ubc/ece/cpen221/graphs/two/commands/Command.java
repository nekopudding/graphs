package ca.ubc.ece.cpen221.graphs.two.commands;

import ca.ubc.ece.cpen221.graphs.two.World;

/**
 * A Command represents an action that affects a {@link World}.
 */
public interface Command {

    /**
     * Execute a command in the world. If a command violates a rule or invariant
     * in the World (for example, moving to a nonempty location), it throws an
     * {@link InvalidCommandException}
     *
     * @param world the current World
     * @throws InvalidCommandException if the command violates a rule
     */
    void execute(World world) throws InvalidCommandException;

}