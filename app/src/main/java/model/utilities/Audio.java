package model.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Utility class for music.
 */
public final class Audio {

  private static final float VOLUME = 0.1f;

  private final Music music;

  private Audio(final String name) {
    this.music = Gdx.audio.newMusic(Gdx.files.internal(name));
    this.music.setLooping(true);
  }

  /**
   * starts music.
   */
  public void play() {
    this.music.play();
    this.music.setVolume(VOLUME);
  }
  /**
   * stops music.
   */
  public void stop() {
    this.music.stop();
  }

  /**
   * plays menu music.
   * @return the menu audio. 
   */
  public static Audio getMenuMusic() {
    return new Audio("sounds/Menu.mp3");
  }
  /**
   * plays battle music.
   * @return the game audio. 
   */
  public static Audio getBattleMusic() {
    return new Audio("sounds/Battle.mp3");
  }
}
