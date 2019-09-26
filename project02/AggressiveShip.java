import oop.ex2.*;
import java.awt.Image;
import java.lang.Math;
/**
 * The Aggressive ship - this ship pursues other ships and tries to fire at them. It will always accelerate,
 * and  turn  towards  the  nearest  ship.
 * When  its  angle  to  the  nearest  ship  is  less  than  0.21radians, it will try to fire.
 * @author yoav
 */
public class AggressiveShip extends SpaceShip{

    /** Close range is 0.19 by definition*/
    private final double fireAngle = 0.21;

    /**
     * Creates a new instance of a AggressiveShip with the given params.
     *
     * @param maximalEnergy integer.
     * @param currentEenergy integer 0 - maximal energy
     * @param integer health level 0 - 22
     */
    public AggressiveShip(int maximalEnergy, int currentEenergy, int health) {
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
        // if the angle is negative then turn right
        if (super.physics.angleTo(otherPhysics) < 0) super.physics.move(true, -1);
            // else turn left
        else super.physics.move(true, 1);
        if (super.physics.angleTo(otherPhysics) <= fireAngle) {
            super.fire(game);
        }
    }
}