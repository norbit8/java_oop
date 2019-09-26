import oop.ex2.*;
import java.awt.Image;

/**
 *
 * @author yoav
 */
public class SpecialShip extends SpaceShip{

    /** This will make the ship undeafeatable*/
    private final int godMode = 100;

    /** This will give the ship unlimited energey */
    private final int maxEnergy = 100;

    public SpecialShip(int maximalEnergy, int currentEenergy, int health) {
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
        super.changeHealth(godMode);
        super.changeEnergy(maxEnergy);
        super.fire(game);
    }
}