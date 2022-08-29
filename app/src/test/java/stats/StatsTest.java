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

  @BeforeEach
  void setUp() {

    if (file.exists()) {
      final var t = file.delete();
      assertTrue(t);
    }
    this.fm = new FileManager();
  }

  @Test
  void createFile() {

    this.fm.read();
    assertTrue(file.exists());
  }

  @Test
  void updateFile() {
    fm.addPlays();
    fm.addPlays();
    fm.addPlays();

    assertEquals(3, fm.getPlays());

    fm.addWin();

    assertEquals(1, fm.getWins());
    assertEquals(0, fm.getTowersDestroyed());

    fm.addTowersDestroyed(2);
    fm.addTowersDestroyed(3);

    assertEquals(2 + 3, fm.getTowersDestroyed());
  }

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
