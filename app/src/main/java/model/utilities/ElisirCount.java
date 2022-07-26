package model.utilities;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Utility class for elisir in game.
 */
public class ElisirCount {
  private final Timer timer;
  private int elisir;

  /**
   * build an elisircount.
   */
  public ElisirCount() {
    elisir = 0;
    timer = new Timer();
    timer.schedule(new MyTimerTask(), 0, 1000);
  }

  class MyTimerTask extends TimerTask {

    public void run() {
      System.out.println(elisir);
      if (elisir < 10) {
        elisir++;
      }
    }
  }

  /**
   * get elisir.
   * 
   * @return int
   */
  public int getElisir() {
    return elisir;
  }

  /**
   * set elisir.
   */
  public void setElisir() {
    elisir = 0;
  }

  /**
   * decrement elisir.
   * 
   * @param n
   */
  public void decrementElisir(final int n) {
    elisir = elisir - n;
  }
}
