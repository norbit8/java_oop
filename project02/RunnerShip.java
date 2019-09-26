import oop.ex2.*;
import java.awt.Image;
import java.lang.Math;

/**
 *  This spaceship attempts to run away from the fight.
 *  It will always accelerate, andwill constantly turn away from the closest ship.
 *  If the nearest ship is closer than 0.25 units,
 *  and if its angle to the Runner is less than 0.23 radians,
 *  the Runner feels threatened and willattempt to teleport.
 * @author yoav
 */
public class RunnerShip extends SpaceShip{

    /** The maximum angle that the ship will turn for*/
    private final double maxAngle = 0.23;

    /** The maximum distance that the ship will turn for*/
    private final double maxDistance = 0.25;

    /** turn left */
    private final int leftTrun = 1;

    /** turn right */
    private final int rightTrun = -1;

    /**
     * Creates a new instance of a RunnerShip with the given params.
     *
     * @param maximalEnergy integer.
     * @param currentEenergy integer 0 - maximal energy
     * @param integer health level 0 - 22
     */
    public RunnerShip(int maximalEnergy, int currentEenergy, int health) {
        super(maximalEnergy, currentEenergy, health);
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public Image getImage(){
        return GameGUI.ENEMY_SPACESHIP_IMAGE;
    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        super.doAction(game);
        SpaceShip closestShip = game.getClosestShipTo(this);
        SpaceShipPhysics otherPhysics = closestShip.getPhysics();
        double angleToClosestShip = super.physics.angleTo(otherPhysics);
        int turn = 0;
        if ((Math.abs(angleToClosestShip) < maxAngle) &&
           (super.physics.distanceFrom(otherPhysics) < maxDistance)) {
            super.teleport();
        }
        if (angleToClosestShip >  0) turn = this.rightTrun;
        else turn = this.leftTrun;
        super.physics.move(true, turn);
    }
}