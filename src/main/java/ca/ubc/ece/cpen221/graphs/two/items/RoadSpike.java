package ca.ubc.ece.cpen221.graphs.two.items;

import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.Util;

import javax.swing.ImageIcon;

/**
 * Road spikes will be placed at the creation of the world. If any vehicle tries to run over a
 * road spike, both the vehicle and the road spike will be destroyed.
 */
public class RoadSpike implements Item {
    private final static ImageIcon spikeImage = Util.loadImage("spike.gif");

    private Location location;
    private boolean isDead;

    /**
     * Plant a roadspike at <code> location </code>. The location must be valid and
     * empty
     *
     * @param location : the location where this grass will be created
     */
    public RoadSpike(Location location) {
        this.location = location;
        this.isDead = false;
    }

    @Override
    public ImageIcon getImage() {
        return spikeImage;
    }

    @Override
    public String getName() {
        return "RoadSpike";
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public int getPlantCalories() {
        return 0;
    }

    @Override
    public int getMeatCalories() {
        return 0;
    }

    @Override
    public void loseEnergy(int energy) {
        isDead = true;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public int getStrength() {
        isDead = true;
        return Integer.MAX_VALUE;
    }

}