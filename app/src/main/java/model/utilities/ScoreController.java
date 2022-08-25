package model.utilities;

/**
 * Utility class for score progress in game.
 */
public class ScoreController {

  private int score;

  /**
   * build an score controller .
   */
  public ScoreController() {
    this.score = 0;
  }

  /**
   * 
   * @return the current score.
   */
  public int getScore() {
    return score;
  }

  /**
   * Increment the current score by a parameter.
   * @param points
   *               the increment of the score.
   */
  public void increaseScore(final int points) {
    this.score = score + points;
  }
}
