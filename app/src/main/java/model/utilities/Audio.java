package model.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Utility class for music.
 */
public class Audio {
  private Music music;
  /**
   * builds an music.
   * @param name
   *            the name of of files used.
   */
  public Audio(final String name) {
    super();
    this.music = Gdx.audio.newMusic(Gdx.files.internal(name));
    //music = 
  }
  /**
   * play music.
   * @return 
   */
  public void play() {
    music.play();
  }
  /**
   * stop music.
   * @return 
   */
  public void stop() {
    music.stop();
  }
}
