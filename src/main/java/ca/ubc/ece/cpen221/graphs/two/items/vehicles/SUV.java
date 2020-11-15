package ca.ubc.ece.cpen221.graphs.two.items.vehicles;

import ca.ubc.ece.cpen221.graphs.two.Direction;
import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.Util;
import ca.ubc.ece.cpen221.graphs.two.World;
import ca.ubc.ece.cpen221.graphs.two.ai.VehicleAI;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;

import javax.swing.ImageIcon;

import static ca.ubc.ece.cpen221.graphs.two.Direction.*;
import static ca.ubc.ece.cpen221.graphs.two.Direction.NORTH;

public class SUV extends AbstractVehicle{
    private final int INITIAL_ENERGY = 100;
    private final int INITIAL_COOLDOWN = 6;
    private final int MIN_COOLDOWN = 1;
    private final int MAX_COOLDOWN = 10;
    private final ImageIcon SUVImage = Util.loadImage("trucks.gif");
    private final int ACCELERATION = 1;
    private final int BRAKING = 2;
    private final int MIN_TURN_COOLDOWN = 4;
    private final int MASS = 900;

    private VehicleAI ai;

    private Location location;
    private int energy;
    private int cooldown;
    private Direction direction;
    private double strength;

    public SUV(Location loc, Direction start, VehicleAI vehai){
        this.location = loc;
        this.direction = start;
        this.ai = vehai;
        energy = INITIAL_ENERGY;
        cooldown = INITIAL_COOLDOWN;
        strength = MASS * 1.0 / cooldown;
    }

    public String getName(){ return "SUV"; }

    @Override
    public void accelerate(){
        this.cooldown = Math.max(this.MIN_COOLDOWN, this.cooldown - this.ACCELERATION);
        this.cooldown = Math.max(this.cooldown, 1);
        this.strength = MASS * 1.0 / cooldown;
    }

    @Override
    public void decelerate(){
        this.cooldown = Math.min(this.MAX_COOLDOWN, this.cooldown + this.BRAKING);
        this.strength = MASS * 1.0 / cooldown;
    }

    @Override
    public int getTurnSpeed(){ return this.MIN_TURN_COOLDOWN; }

    @Override
    public boolean changeDirection(Direction dir){
        if (this.direction == dir){
            return false;
        }
        if (this.direction == NORTH && dir == SOUTH){
            return false;
        }
        if (this.direction == EAST && dir == WEST){
            return false;
        }
        if (this.direction == WEST && dir == EAST){
            return false;
        }
        if (this.direction == SOUTH && dir == NORTH){
            return false;
        }
        this.direction = dir;
        return true;
    }

    @Override
    public void moveTo(Location targetLocation){
        this.location = targetLocation;
    }

    @Override
    public int getMovingRange(){ return 1; }

    @Override
    public int getCoolDownPeriod(){ return cooldown; }

    @Override
    public Command getNextAction(World world){
        Command nextAction = ai.getNextAction(world, this);
        this.energy--;
        return nextAction;
    }

    @Override
    public ImageIcon getImage(){ return SUVImage; }

    @Override
    public Location getLocation(){ return location; }

    @Override
    public int getStrength(){ return (int)strength; }

    @Override
    public boolean isDead() { return energy <= 0; }

    @Override
    public Direction getDirection() { return this.direction; }

    @Override
    public void loseEnergy(int energyLoss) {
        this.energy = this.energy - energyLoss;
    }

}
