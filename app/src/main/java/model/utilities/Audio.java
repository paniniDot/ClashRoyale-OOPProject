package model.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Utility class for music.
 */
public final class Audio {
  private final Music music;
  /**
   * builds an music.
   * @param name
   *            the name of of files used.
   */
  private Audio(final String name) {
    this.music = Gdx.audio.newMusic(Gdx.files.internal(name));
  }
  /**
   * play music.
   * @return
   */
  private void play() {
    this.music.play();
  }
  /**
   * stop music.
   * @return
   */
  public void stop() {
    this.music.stop();
  }
  /**
   * play menu music.
   * @return Audio 
   */
  public static Audio playMenuMusic() {
    final Audio audio = new Audio("sounds/Menu.mp3");
    audio.play();
    return audio; 
  }
  /**
   * play battle music.
   * @return Audio 
   */
  public static Audio playBattleMusic() {
    final Audio audio = new Audio("sounds/Battle.mp3");
    audio.play();
    return audio; 
  }
}
