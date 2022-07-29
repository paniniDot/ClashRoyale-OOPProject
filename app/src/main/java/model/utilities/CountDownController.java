package model.utilities;

import java.util.Timer;
import java.util.TimerTask;
/**
 * Utility class for CountDown in game.
 */
public class CountDownController {

  /**
   * Game will least 60 seconds.
   */
  public static final int DEFAULT_TIME = 60;

  private final Timer timer;
  private int time;

  /**
   * build an countdown controller.
   */
  public CountDownController() {
    this.time = DEFAULT_TIME;
    this.timer = new Timer();
    this.timer.schedule(new MyTimerTask(), 0, 1000);

  }

  class MyTimerTask extends TimerTask {

    public void run() {
      //System.out.println(time);
      if (time > 0) {
        time--;
      } else {
        timer.cancel();
      }
    }
  }

  /**
   * @return the remaining seconds before game ends.
   */
  public int getTime() {
    return this.time;
  }

  /**
   * Restart the timer.
   */
  public void setTime() {
    this.time = DEFAULT_TIME;
  }
}
