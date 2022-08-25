package model.utilities;

/**
 * Utility class for score progress in game.
 */
public class ScoreController {
  private static final int POINT = 15;
  private int score;
  private int tmp;

 /**
  * 
  *build an score controller.
  *@param score
  */
  public ScoreController(final int score) {
    this.score = score;
    this.tmp = 0;
  }



  /**
   * 
   * @return the current score.
   */
  public int getScore() {
    return score;
  }


/**
 * increase your current score every 5 hits.
 */
  public void increaseScore() {
    tmp++;
    if (tmp == POINT) {
      this.score = score + 1;
      tmp = 0;
    }
  }
  
/**
 * reset score at the end of the game.
 */
  public void resetScore() {
    this.score = 0;
  }
}
