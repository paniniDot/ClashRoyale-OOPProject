package model.entities.users;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import model.deck.PlayersDeck;


/**
 * Users that play the game.
 */
public class User {

  private static final Map<UserLevel, Integer> XP_PER_LEVEL = Map.of(
      UserLevel.LVL1, 0, 
      UserLevel.LVL2, 100, 
      UserLevel.LVL3, 300, 
      UserLevel.LVL4, 600, 
      UserLevel.LVL5, 1000);

  private final String name;
  private int currentXP;
  private UserLevel currentLevel;

  /**
   * @param name
   *           nickname of the user.
   */
  public User(final String name) {
    this.name = name;
    this.currentXP = 0;
    this.currentLevel = UserLevel.LVL1;
  }

  /**
   * @return the name of the user.
   */
  public String getName() {
    return this.name;
  }

  /**
   * @return current xp of the user (xp are needed to level up).
   */
  public int getCurrentXP() {
    return this.currentXP;
  }

  /**
   * @return the User level.
   */
  public UserLevel getCurrentLevel() {
    return this.currentLevel;
  }


  /**
   * Increase the current xp user owns.
   * @param xp to be added.
   */
  public void awardXp(final int xp) {
    this.currentXP += xp;
    this.checkCurrentXP();
  }

  private void checkCurrentXP() {
    if (this.currentXP >= User.XP_PER_LEVEL.get(this.getNextLevel())) {
      this.currentXP -= User.XP_PER_LEVEL.get(this.getNextLevel());
      this.levelUp();
    }
  }

  private UserLevel getNextLevel() {
    return this.currentLevel.equals(UserLevel.LVL5) ? this.currentLevel : UserLevel.values()[this.currentLevel.ordinal() + 1];
  }

  private void levelUp() {
    this.currentLevel = this.getNextLevel();
    this.checkCurrentXP();
  }
/**
 * 
 * @param currentXP
 */
  public void setCurrentXP(final int currentXP) {
    this.currentXP = currentXP;
  }

  /**
   * 
   * @param currentLevel
   */
  public void setCurrentLevel(final UserLevel currentLevel) {
    this.currentLevel = currentLevel;
  }

  @Override
  public String toString() {
    return "User [name=" + name + ", currentXP=" + currentXP + ", currentLevel=" + currentLevel + "]";
  }

  @Override
  public int hashCode() {
    return Objects.hash(currentLevel, currentXP, name);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final User other = (User) obj;
    return currentLevel == other.currentLevel && currentXP == other.currentXP && Objects.equals(name, other.name);
  }
}
