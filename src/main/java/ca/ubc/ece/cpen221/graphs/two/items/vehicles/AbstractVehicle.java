package ca.ubc.ece.cpen221.graphs.two.items.vehicles;

import ca.ubc.ece.cpen221.graphs.two.Direction;
import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.World;
import ca.ubc.ece.cpen221.graphs.two.ai.AI;
import ca.ubc.ece.cpen221.graphs.two.ai.VehicleAI;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;

import javax.swing.ImageIcon;

import static ca.ubc.ece.cpen221.graphs.two.Direction.*;

public abstract class AbstractVehicle implements Vehicle{

    private int INITIAL_ENERGY;
    private int MAX_ENERGY;
    private int MASS;
    private int INITIAL_COOLDOWN;
    private int MIN_COOLDOWN;
    private int MAX_COOLDOWN;
    private ImageIcon image;
    private int ACCELERATION;
    private int BRAKING;
    private int MIN_TURN_COOLDOWN;

    private VehicleAI ai;

    private Location location;
    private int energy = INITIAL_ENERGY;
    private int cooldown = INITIAL_COOLDOWN;
    private Direction direction = NORTH;
    private double strength = MASS * 1.0 / INITIAL_COOLDOWN;


    @Override
    public void loseEnergy(int energyLoss) {
        this.energy = this.energy - energyLoss;
    }

    @Override
    public int getPlantCalories(){
        return 0;
    }

    @Override
    public int getMeatCalories(){
        return 0;
    }

    @Override
    public int getMinCooldown(){ return this.MIN_COOLDOWN; }

    @Override
    public int getDeceleration(){ return this.BRAKING; }
}
