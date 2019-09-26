import oop.ex2.*;


public class SpaceShipFactory {
    /** A maximal energy level*/
    public static final int MAXIMALENERGY = 210;

    /** A CURRENTENERGY level, which is between 0 and the maximal energy level*/
    public static final int CURRENTENERGY= 190;

    /** A HEALTH level between 0 and 22*/
    public static final int HEALTH = 22;

    public static SpaceShip[] createSpaceShips(String[] args) {
        int index = 0;
        SpaceShip[] spaceShipArray = new SpaceShip[args.length];
        // Iterating over the arguments array.
        for (String spaceShip: args) {
            switch (spaceShip){
                case "h":
                    // Human controlled ship.
                    spaceShipArray[index] = new HumanShip(MAXIMALENERGY,
                                                          CURRENTENERGY,
                                                          HEALTH);
                    index++;
                    break;
                case "r":
                    // Runner ship.
                    spaceShipArray[index] = new RunnerShip(MAXIMALENERGY,
                                                           CURRENTENERGY,
                                                           HEALTH);
                    index++;
                    break;
                case "b":
                    // Basher ship.
                    spaceShipArray[index] = new BasherShip(MAXIMALENERGY,
                                                           CURRENTENERGY,
                                                           HEALTH);
                    index++;
                    break;
                case "a":
                    // Agressive ship.
                    spaceShipArray[index] = new AggressiveShip(MAXIMALENERGY,
                                                           CURRENTENERGY,
                                                           HEALTH);
                    index++;
                    break;
                case "d":
                    // Drunkard ship.
                    spaceShipArray[index] = new DrunkardShip(MAXIMALENERGY,
                                                           CURRENTENERGY,
                                                           HEALTH);
                    index++;
                    break;
                case "s":
                    // Special ship.
                    spaceShipArray[index] = new SpecialShip(MAXIMALENERGY,
                                                           CURRENTENERGY,
                                                           HEALTH);
                    index++;
                    break;
            }
        }
        return spaceShipArray;
    }
}
