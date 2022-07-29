package model.utilities;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Utility class for elixir in game.
 */
public class ElixirController {
  private final Timer timer;
  private int elixir;

  /**
   * build an elixir controller .
   */
  public ElixirController() {
    this.elixir = 0;
    this.timer = new Timer();
    this.timer.schedule(new MyTimerTask(), 0, 1000);
  }

  class MyTimerTask extends TimerTask {

    public void run() {
      //System.out.println(elixir);
      if (elixir < 10) {
        elixir++;
      }
    }
  }

  /**
   * 
   * @return the current elixir owned.
   */
  public int getElixirCount() {
    return this.elixir;
  }

  /**
   * set elixir value to 0.
   */
  public void resetElixirCount() {
    this.elixir = 0;
  }

  /**
   * decrement elixir.
   * 
   * @param n
   *        the amount of elixir to be taken.
   */
  public void decrementElixir(final int n) {
    this.elixir -= n;
  }
}
