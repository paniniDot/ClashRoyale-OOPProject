package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

/**
 * Used to save/read stats into/from a file.
 */
public class FileManager {

  private Optional<Integer> plays;
  private Optional<Integer> wins;
  private Optional<Integer> towersDestroyed;

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
    if (this.plays.isPresent()) { 
      return this.plays.get();
    } else {
    return 0;
    }
  }

  /**
   * Increment the games played counter.
   */
  public void addPlays() {
    var t = getPlays();
    t++;
    this.plays = Optional.of(t);
  }

  /**
   * @return the number of game won.
   */
  public int getWins() {
    if (this.wins.isPresent()) { 
      return this.wins.get();
    } else {
    return 0;
    }
  }

  /**
   * Increment the games played counter.
   */
  public void addWin() {
    var t = getWins();
    t++;
    this.wins = Optional.of(t);
  }

  /**
   * @return the number of towers destroyed.
   */
  public int getTowersDestroyed() {
    if (this.towersDestroyed.isPresent()) { 
      return this.towersDestroyed.get();
    } else {
    return 0;
    }
  }

  /**
   * @param towersDestroyed the number of towers destroyed.
   */
  public void addTowersDestroyed(final int towersDestroyed) {
    var t = getTowersDestroyed();
    t += towersDestroyed;
    this.towersDestroyed = Optional.of(t);
  }

  /**
   * Save the data on file.
   */
  public void save() {
    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FileManager.ABSOLUTEPATH))) {

      bufferedWriter.write(this.getPlays());
      bufferedWriter.write(this.getWins());
      bufferedWriter.write(this.getTowersDestroyed());

    } catch (IOException e) {
      System.err.println("IOException from FileManager.save()");
      e.printStackTrace();
    }
  }

  /**
   * Read the data from file and updates the field.
   */
  public final void read() {
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FileManager.ABSOLUTEPATH))) {

      var temp = bufferedReader.read();
      this.plays = Optional.of(temp);
      temp = bufferedReader.read();
      this.wins = Optional.of(temp);
      temp = bufferedReader.read();
      this.towersDestroyed = Optional.of(temp);

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
