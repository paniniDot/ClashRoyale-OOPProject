package model.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Utility class for music.
 */
public class Audio {
  private final Music music;
  /**
   * builds an music.
   * @param name
   *            the name of of files used.
   */
  public Audio(final String name) {
    this.music = Gdx.audio.newMusic(Gdx.files.internal(name));
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
