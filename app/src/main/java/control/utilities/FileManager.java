package control.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
    this.save();
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
    final var absolutePath = "saves" + File.separator + "PROVA.txt";
    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(absolutePath))) {

      bufferedWriter.write(this.getPlays());
      bufferedWriter.newLine();
      bufferedWriter.write(this.getWins());
      bufferedWriter.newLine();
      bufferedWriter.write(this.getTowersDestroyed());
      bufferedWriter.newLine();

    } catch (IOException e) {
      System.err.println("IOException from FileManager.save()");
      e.printStackTrace();
    }
  }

  /**
   * Read the data from file and updates the field.
   */
  public void read() {

  }
}
