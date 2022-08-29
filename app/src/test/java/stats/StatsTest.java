package stats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.FileManager;

/**
 * Testing the saving and reading of statistics. 
 */
class StatsTest {

  private FileManager fm;
  private final File file = new File("saves" + File.separator + "stats.txt");

  /**
   * Delete and create a new stats.txt.
   */
  @BeforeEach
  void setUp() {

    if (file.exists()) {
      final var t = file.delete();
      assertTrue(t);
    }
    this.fm = new FileManager();
  }

  /**
   * Check if the file is created and is empty.
   */
  @Test
  void createFile() {
    this.fm.read();
    assertTrue(file.exists());
    assertEquals(0, this.fm.getPlays());
    assertEquals(0, this.fm.getWins());
    assertEquals(0, this.fm.getTowersDestroyed());
  }

  /**
   * Check if adds methods and getters works.
   */
  @Test
  void updateFile() {
    fm.addPlays();
    fm.addPlays();
    fm.addPlays();
    fm.addWin();
    fm.addTowersDestroyed(3);
    fm.addTowersDestroyed(2);

    assertEquals(3, fm.getPlays());
    assertEquals(1, fm.getWins());
    assertEquals(2 + 3, fm.getTowersDestroyed());
  }

  /**
   * Saves new data on an empty file and then read the data.
   */
  @Test
  void readUpdatedFile() {
    fm.addPlays();
    fm.addPlays();
    fm.addWin();
    fm.addTowersDestroyed(3);

    fm.save();
    fm = new FileManager();

    assertEquals(1, fm.getWins());
    assertEquals(2, fm.getPlays());
    assertEquals(3, fm.getTowersDestroyed());
  }
}
