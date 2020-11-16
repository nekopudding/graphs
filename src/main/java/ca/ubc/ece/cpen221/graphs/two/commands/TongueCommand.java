package ca.ubc.ece.cpen221.graphs.two.commands;

import ca.ubc.ece.cpen221.graphs.two.Food;
import ca.ubc.ece.cpen221.graphs.two.World;
import ca.ubc.ece.cpen221.graphs.two.items.Item;
import ca.ubc.ece.cpen221.graphs.two.items.LivingItem;

/**
 * An EatCommand is a {@link Command} which represents a {@link LivingItem}
 * eating a {@link Food}.
 */
public final class TongueCommand implements Command {

    private final LivingItem item;
    private final Item food;

    /**
     * Construct a {@link TongueCommand}, where <code> item </code> is the tonguer
     * and <code> food </code> is the food. The food must have a distance of less than or equal
     * to 2 for the item to be able to grab the food with its tongue.
     *
     * @param item the eater
     * @param food : the food
     */
    public TongueCommand(LivingItem item, Item food) {
        this.item = item;
        this.food = food;
    }

    @Override
    public void execute(World w) throws InvalidCommandException {
        if (item.getStrength() <= food.getStrength()) {
            throw new InvalidCommandException(
                "Invalid EatCommand: Food possesses too much strength");
        }
        if (food.getLocation().getDistance(item.getLocation()) > 2) {
            throw new InvalidCommandException("Invalid EatCommand: Food is not close enough");
        }

        item.eat(food);
        food.loseEnergy(Integer.MAX_VALUE);
    }

}
