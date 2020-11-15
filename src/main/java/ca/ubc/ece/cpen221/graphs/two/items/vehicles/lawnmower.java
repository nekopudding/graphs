package ca.ubc.ece.cpen221.graphs.two.items.vehicles;

import ca.ubc.ece.cpen221.graphs.two.Direction;
import ca.ubc.ece.cpen221.graphs.two.Location;
import ca.ubc.ece.cpen221.graphs.two.Util;
import ca.ubc.ece.cpen221.graphs.two.World;
import ca.ubc.ece.cpen221.graphs.two.ai.VehicleAI;
import ca.ubc.ece.cpen221.graphs.two.commands.AccelerateCommand;
import ca.ubc.ece.cpen221.graphs.two.commands.ChangeDirectionCommand;
import ca.ubc.ece.cpen221.graphs.two.commands.Command;
import ca.ubc.ece.cpen221.graphs.two.commands.DecelerateCommand;
import ca.ubc.ece.cpen221.graphs.two.commands.MoveCommand;
import ca.ubc.ece.cpen221.graphs.two.commands.WaitCommand;

import javax.swing.ImageIcon;

import static ca.ubc.ece.cpen221.graphs.two.Direction.*;
import static ca.ubc.ece.cpen221.graphs.two.Direction.NORTH;

public class lawnmower extends AbstractVehicle{
    private final int INITIAL_ENERGY = 100;
    private final int INITIAL_COOLDOWN = 6;
    private final int MIN_COOLDOWN = 3;
    private final int MAX_COOLDOWN = 10;
    private final ImageIcon SUVImage = Util.loadImage("cars.gif");
    private final int ACCELERATION = 1;
    private final int BRAKING = 1;
    private final int MIN_TURN_COOLDOWN = 4;
    private final int MASS = 50;

    private Location location;
    private int energy;
    private int cooldown;
    private Direction direction;
    private double strength;

    public lawnmower(Location loc, Direction start){
        this.location = loc;
        this.direction = start;
        energy = INITIAL_ENERGY;
        cooldown = INITIAL_COOLDOWN;
        strength = MASS * 1.0 / cooldown;
    }

    public String getName(){ return "Lawnmower"; }

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
        if (this.direction.equals(dir)){
            return false;
        }

        switch (this.direction){
            case NORTH: if (dir == SOUTH) { return false; }
            case EAST: if (dir == WEST) { return false; }
            case WEST: if (dir == EAST) { return false; }
            case SOUTH: if (dir == NORTH) { return false; }
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

        Direction dir = Util.getRandomDirection();
        if (dir == this.direction){
            return new AccelerateCommand(this, new Location(this.getLocation(),dir));
        }
        else{
            if (this.cooldown >= this.MIN_TURN_COOLDOWN){
                return new ChangeDirectionCommand(this, dir);
            }
            else{
                return new DecelerateCommand(this, new Location(this.getLocation(), this.direction));
            }
        }
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

    @Override
    public int getMinCooldown(){ return this.MIN_COOLDOWN; }

    @Override
    public int getDeceleration(){ return this.BRAKING; }
}
