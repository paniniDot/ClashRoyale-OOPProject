package control.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Used to save/read stats into/from a file.
 */
public class FileManager {

  private int plays;
  private int wins;
  private int towersDestroyed;

  private static final String ABSOLUTEPATH = "saves" + File.separator + "stats.txt";

  /**
   * Constructor. 
   */
  public FileManager() {
    this.read();
  }

  /**
   * @return the number of game played.
   */
  public int getPlays() {
    return this.plays;
  }

  /**
   * Increment the games played counter.
   */
  public void addPlays() {
    this.plays++;
  }

  /**
   * @return the number of game won.
   */
  public int getWins() {
    return this.wins;
  }

  /**
   * Increment the games played counter.
   */
  public void addWin() {
    this.wins++;
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
    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FileManager.ABSOLUTEPATH))) {

      bufferedWriter.write(this.getPlays());
      bufferedWriter.write(this.getWins());
      bufferedWriter.write(this.getTowersDestroyed());

      //System.out.println(this.getPlays() +" "+ this.getTowersDestroyed() +" "+ this.getWins());

    } catch (IOException e) {
      System.err.println("IOException from FileManager.save()");
      e.printStackTrace();
    }
  }

  /**
   * Read the data from file and updates the field.
   */
  public void read() {
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FileManager.ABSOLUTEPATH))) {

      var temp = bufferedReader.read();
      this.plays = temp;
      temp = bufferedReader.read();
      this.wins = temp;
      temp = bufferedReader.read();
      this.towersDestroyed = temp;

    } catch (FileNotFoundException e) {
      this.initialize();
      this.read();
    } catch (IOException e) {
      System.err.println("IOException from FileManager.read()");
      e.printStackTrace();
    }
  }

  private void initialize() {
    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FileManager.ABSOLUTEPATH))) {
      for (var t = 0; t < 3; t++) {
        bufferedWriter.write(0);
      }
    } catch (IOException e) {
      System.err.println("IOException from FileManager.save()");
      e.printStackTrace();
    }
  }

}
