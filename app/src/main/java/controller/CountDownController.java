package controller;

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
  private boolean run;
  private int time;

  /**
   * build an countdown controller.
   */
  public CountDownController() {
    this.time = DEFAULT_TIME;
    this.run = true;
    this.timer = new Timer();
    final var task = new TimerTask() {
      @Override
      public void run() {
        //System.out.println(time);
        if (time > 0 && run) {
          time--;
        } else {
          timer.cancel();
          timer.purge();
       }
     }
     };
    this.timer.schedule(task, 0, 1000);
  }

   /**
    * set return to false.
    */
   public void setRunFalse() {
     this.run = false;
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
