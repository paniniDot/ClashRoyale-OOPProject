package model.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Utility class for music.
 */
public final class Audio {
  private final Music music;

  private Audio(final String name) {
    this.music = Gdx.audio.newMusic(Gdx.files.internal(name));
  }

  private void play() {
    this.music.play();
    this.music.setVolume(0.1f);
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
  public static Audio playMenuMusic() {
    final Audio audio = new Audio("sounds/Menu.mp3");
    audio.play();
    return audio; 
  }
  /**
   * plays battle music.
   * @return the game audio. 
   */
  public static Audio playBattleMusic() {
    final Audio audio = new Audio("sounds/Battle.mp3");
    audio.play();
    return audio; 
  }
}
