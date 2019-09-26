import oop.ex2.*;
import java.awt.Image;
import java.util.Random;

/**
 * Drunkard: Its pilot had a tad too much to drink.
 * but it must include randomness and should definitely be amusingto fight against.
 * @author yoav
 */
public class DrunkardShip extends SpaceShip{

    /** This will decide where should the DrunkardShip turn*/
    private int randomTurn;

    /**
     * Creates a new instance of a DrunkardShip with the given params.
     *
     * @param maximalEnergy integer.
     * @param currentEenergy integer 0 - maximal energy
     * @param integer health level 0 - 22
     */
    public DrunkardShip(int maximalEnergy, int currentEenergy, int health) {
        super(maximalEnergy, currentEenergy, health);
        // Obtain a number between [-1 - 1].
        Random rand = new Random();
        randomTurn = rand.nextInt(3);
        randomTurn -= 1;
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
        super.physics.move(true, randomTurn);
        super.fire(game);
    }
}