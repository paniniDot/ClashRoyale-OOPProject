package actors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.entities.users.User;
import model.entities.users.UserLevel;

class TestUsers {

  private User panini;

  @BeforeEach
  void setUp() {
    this.panini = new User("panini");
  }

  @Test
  void simpleTest() {
    assertEquals(this.panini.getName(), "panini");
    assertEquals(this.panini.getCurrentXP(), 0);
    assertEquals(this.panini.getCurrentLevel(), UserLevel.LVL1);
  }

  @Test
  void testLevelUp() {
    final int firstAward = 50;
    final int secondAward = 300;
    this.panini.awardXp(firstAward);
    assertEquals(this.panini.getCurrentLevel(), UserLevel.LVL1);
    this.panini.awardXp(firstAward);
    assertEquals(this.panini.getCurrentLevel(), UserLevel.LVL2);
    this.panini.awardXp(secondAward);
    assertEquals(this.panini.getCurrentLevel(), UserLevel.LVL3);
  }

  @Test
  void testMultipleLevelsUp() {
    final int award = 400;
    this.panini.awardXp(award);
    assertEquals(this.panini.getCurrentLevel(), UserLevel.LVL3);
  }

  @Test
  void testOutOfBoundLevelUp() {
    final int award = 3000;
    this.panini.awardXp(award);
    assertEquals(this.panini.getCurrentLevel(), UserLevel.LVL5);
  }
}
