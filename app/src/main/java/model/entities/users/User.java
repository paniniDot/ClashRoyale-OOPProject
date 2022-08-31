package model.entities.users;

import java.util.Map;
import java.util.Objects;

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
  private int plays;
  private int wins;
  private int destroyedTowers;
  private static final int POINT = 5; 

  /**
   * @param name
   *           nickname of the user.
   */
  public User(final String name) {
    this.name = name;
    this.currentXP = 0;
    this.plays = 0;
    this.wins = 0;
    this.destroyedTowers = 0;
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
   * @param towersDestroy to be added.
   */
  public void awardXp(final int towersDestroy) {
    this.currentXP += towersDestroy * POINT;
    checkCurrentXP();
  }

  /**
   * score decrease in case of lose.
   */
  public void pointReduction() {
    this.currentXP -= POINT;
    if (this.currentXP < 0) {
      this.currentXP = 0;
    }
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

  /**
   *  Increment the wins counter of one.
   */
  public void addWin() {
    this.wins++;
  }

  /**
   * 
   * @return how many wins the player has.
   */
  public int getWins() {
    return this.wins;
  }

  /**
   * Increment the plays counter of one.
   */
  public void addPlay() {
    this.plays++;
  }

  /**
   * 
   * @return how many plays the player has.
   */
  public int getPlays() {
    return this.plays;
  }

  /**
   * Increment the number of destroyed towers. 
   * @param value
   *            the number of towers destroyed.
   */
  public void addDestroyedTowers(final int value) {
    this.destroyedTowers += value;
  }

  /**
   * 
   * @return how many towers the player has destroyed.
   */
  public int getDestroyedTowers() {
    return this.destroyedTowers;
  }

  @Override
  public int hashCode() {
    return Objects.hash(currentLevel, currentXP, destroyedTowers, name, plays, wins);
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
    return currentLevel == other.currentLevel && currentXP == other.currentXP
        && destroyedTowers == other.destroyedTowers && Objects.equals(name, other.name) && plays == other.plays
        && wins == other.wins;
  }

}

