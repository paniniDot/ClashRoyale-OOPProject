package actors;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.actors.users.User;
import model.actors.users.UserLevel;

class TestUsers {

  private User panini;

  @BeforeEach
  public void setUp() {
    this.panini = new User("panini");
  }

  @Test
  public void simpleTest() {
    assertEquals(this.panini.getName(), "panini");
    assertEquals(this.panini.getCurrentXP(), 0);
    assertEquals(this.panini.getCurrentLevel(), UserLevel.LVL1);
  }

  @Test
  void testLevelUp() {
    this.panini.awardXp(50);
    assertEquals(this.panini.getCurrentLevel(), UserLevel.LVL1);
    this.panini.awardXp(50);
    assertEquals(this.panini.getCurrentLevel(), UserLevel.LVL2);
    this.panini.awardXp(300);
    assertEquals(this.panini.getCurrentLevel(), UserLevel.LVL3);
  }

  @Test
  void testMultipleLevelsUp() {
    this.panini.awardXp(400);
    assertEquals(this.panini.getCurrentLevel(), UserLevel.LVL3);
  }

  @Test
  void testOutOfBoundLevelUp() {
    this.panini.awardXp(3000);
    assertEquals(this.panini.getCurrentLevel(), UserLevel.LVL5);
  }
}
