package model.utilities;

/**
 * Utility class for score progress in game.
 */
public class ScoreController {
  private int score = 0;

  /**
   * build an score controller .
   */
  public ScoreController(final int score) {
    this.score = score;
  }
  
  /**
   * 
   * @return the current score.
   */
  public int getScore() {
    return score;
  }



  /**
  * @param point
   * increase current score.
   */
  public void increaseScore(final int point) {
    this.score = score + point;
  }
}
