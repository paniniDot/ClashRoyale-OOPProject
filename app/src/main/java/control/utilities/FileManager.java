package control.utilities;

/**
 * Used to save/read stats into/from a file.
 */
public class FileManager {

  private int plays;
  private int wins;
  private int towersDestroyed;

  /**
   * Constructor. 
   * 
   * @param plays the number of game played.
   * 
   * @param wins the number of game won.
   * 
   * @param towersDestroyed the number of towers destroyed.
   */
  public FileManager(final int plays, final int wins, final int towersDestroyed) {
    this.plays = plays;
    this.wins = wins;
    this.towersDestroyed = towersDestroyed;
  }

  /**
   * @return the number of game played.
   */
  public int getPlays() {
    return this.plays;
  }

  /**
   * @param plays the number of game played
   */
  public void addPlays(final int plays) {
    this.plays += plays;
  }

  /**
   * @return the number of game won.
   */
  public int getWins() {
    return this.wins;
  }

  /**
   * @param wins the number of game won.
   */
  public void addWins(final int wins) {
    this.wins += wins;
  }

  /**
   * @return the number of towers destroyed.
   */
  public int getTowersDestroyed() {
    return this.towersDestroyed;
  }

  /**
   * @param towersDestroyed the number of towers destroyed.
   */
  public void addTowersDestroyed(final int towersDestroyed) {
    this.towersDestroyed += towersDestroyed;
  }

  /**
   * Save the data on file.
   */
  public void save() {

  }

  /**
   * Read the data from file and updates the field.
   */
  public void read() {

  }
}
