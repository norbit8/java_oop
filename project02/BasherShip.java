import oop.ex2.*;
import java.awt.Image;

/**
 *
 * @author yoav
 */
public class BasherShip extends SpaceShip{

    /** Close range is 0.19 by definition*/
    private final double closeRange = 0.19;

    /**
     * Creates a new instance of a BasherShip with the given params.
     *
     * @param maximalEnergy integer.
     * @param currentEenergy integer 0 - maximal energy
     * @param integer health level 0 - 22
     */
    public BasherShip(int maximalEnergy, int currentEenergy, int health) {
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
        if (!super.shielded) return GameGUI.ENEMY_SPACESHIP_IMAGE;
        else return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
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
        if (super.physics.distanceFrom(otherPhysics) <= closeRange) {
            super.shieldOn();
        }
    }
}