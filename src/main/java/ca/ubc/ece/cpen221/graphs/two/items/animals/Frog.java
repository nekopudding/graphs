package ca.ubc.ece.cpen221.graphs.two.items.animals;

import ca.ubc.ece.cpen221.graphs.two.Food;
import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.Util;
import ca.ubc.ece.cpen221.graphs.two.World;
import ca.ubc.ece.cpen221.graphs.two.ai.AI;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;
import ca.ubc.ece.cpen221.graphs.two.items.Grass;
import ca.ubc.ece.cpen221.graphs.two.items.LivingItem;

import javax.swing.ImageIcon;

/**
 * The Frog is an {@link ArenaAnimal} that eats {@link Gnat} and can
 * be eaten by {@link Fox}.
 */
public class Frog implements ArenaAnimal {

    private static final int INITIAL_ENERGY = 40;
    private static final int MAX_ENERGY = 60;
    private static final int STRENGTH = 60;
    private static final int MIN_BREEDING_ENERGY = 10;
    private static final int VIEW_RANGE = 3;
    private static final int COOLDOWN = 2;
    private static final ImageIcon frogImage = Util.loadImage("unknown.gif");

    private final AI ai;

    private Location location;
    private int energy;

    /**
     * Create a new {@link Frog} with an {@link AI} at
     * <code> initialLocation </code>. The <code> initialLoation
     * </code> must be valid and empty.
     *
     * @param frogAI        : The AI designed for frogs
     * @param initialLocation : the location where this frog will be created
     */
    public Frog(AI frogAI, Location initialLocation) {
        ai = frogAI;
        location = initialLocation;
        energy = INITIAL_ENERGY;
    }

    @Override
    public LivingItem breed() {
        Frog child = new Frog(ai, location);
        child.energy = energy / 2;
        this.energy = energy / 2;
        return child;
    }

    @Override
    public void eat(Food food) {
        // Note that energy does not exceed energy limit.
        energy = Math.min(MAX_ENERGY, energy + food.getPlantCalories());
    }

    @Override
    public int getCoolDownPeriod() {
        return COOLDOWN;
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public ImageIcon getImage() {
        return frogImage;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public int getMaxEnergy() {
        return MAX_ENERGY;
    }

    @Override
    public int getMeatCalories() {
        // The amount of meat calories it provides is equal to its current
        // energy.
        return energy;
    }

    @Override
    public int getMinimumBreedingEnergy() {
        return MIN_BREEDING_ENERGY;
    }

    @Override
    public int getMovingRange() {
        return 1; // Can only move to adjacent locations.
    }

    @Override
    public String getName() {
        return "Frog";
    }

    @Override
    public Command getNextAction(World world) {
        Command nextAction = ai.getNextAction(world, this);
        this.energy--; // Loses 1 energy regardless of action.
        return nextAction;
    }

    @Override
    public int getPlantCalories() {
        // This Frog is not a plant.
        return 0;
    }

    @Override
    public int getStrength() {
        return STRENGTH;
    }

    @Override
    public int getViewRange() {
        return VIEW_RANGE;
    }

    @Override
    public boolean isDead() {
        return energy <= 0;
    }

    @Override
    public void loseEnergy(int energyLoss) {
        this.energy -= energyLoss;
    }

    @Override
    public void moveTo(Location targetLocation) {
        location = targetLocation;

    }
}
