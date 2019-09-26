import oop.ex2.*;
import java.awt.Image;

/**
 * Human controlled:  Controlled by the user.
 * The keys are:  left-arrow and right-arrow toturn,  up-arrow  to  accelerate,  ’d’  to  fire  a  shot,
 * ’s’  to  turn  on  the  shield,  ’a’  to  teleport.
 * You can assume there will be at most one human controlled ship in a game,
 * but you’re notrequired to enforce this.
 * @author yoav
 */
public class HumanShip extends SpaceShip{

    /** The energy price of firing (CONST)*/
    private final int shieldPrice = 3;

    /** The energy price of firing (CONST)*/
    private final int firePrice = 19;

    /** The energy price of using the teleport (CONST)*/
    private final int teleportPrice = 140;

    /**
     * Creates a new instance of a HumanShip with the given params.
     *
     * @param maximalEnergy integer.
     * @param currentEenergy integer 0 - maximal energy
     * @param integer health level 0 - 22
     */
    public HumanShip(int maximalEnergy, int currentEenergy, int health) {
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
        if (super.shielded) return GameGUI.SPACESHIP_IMAGE_SHIELD;
        else return GameGUI.SPACESHIP_IMAGE;
    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        GameGUI gui = game.getGUI();
        super.doAction(game);
        if (gui.isTeleportPressed()) {
            super.teleport(); // Teleport
        }
        moveTheShip(gui); // Accelerate and turn
        if (gui.isShieldsPressed()) {
            super.shieldOn(); // Use Shield
        }
        else {
            super.shielded = false;
        }
        if (gui.isShotPressed()) {
            super.fire(game); // Fire!
        }
    }

    /**
     * This method moves the ship
     * @param gui GameGUI instance.
     */
    private void moveTheShip(GameGUI gui) {
        // ONLY LEFT IS PRESSED
        if (gui.isLeftPressed() && !gui.isRightPressed()) {
            super.physics.move(false, 1);
        }
        // ONLY RIGHT IS PRESSED
        if (gui.isRightPressed() && !gui.isLeftPressed()) {
            super.physics.move(false, -1);
        }
        if (gui.isUpPressed()) {
            super.physics.move(true, 0);
        }
        if (!gui.isRightPressed() && !gui.isLeftPressed() & !gui.isUpPressed()) {
            super.physics.move(false, 0);
        }
    }

    /**
     * Gets the physics object that controls this ship.
     *
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return super.getPhysics();
    }

}