package model.utilities;

import java.util.Timer;
import java.util.TimerTask;
/**
 * Utility class for CountDown in game.
 */
public class CountDown {
  private final Timer timer;
  private int time;
  /**
   * default time.
   */
  public static final int TIMEDEFAULT = 60;

  /**
   * build an countdown.
   */
  public CountDown() {
    time = TIMEDEFAULT;
    timer = new Timer();
    timer.schedule(new MyTimerTask(), 0, 1000);

  }

  class MyTimerTask extends TimerTask {

    public void run() {
      System.out.println(time);
      if (time > 0) {
        time--;
      } else {
        timer.cancel();
      }
    }
  }

  /**
   * get time.
   * 
   * @return int
   */
  public int getTime() {
    return time;
  }

  /**
   * set time.
   */
  public void setTime() {
    time = TIMEDEFAULT;
  }
}
