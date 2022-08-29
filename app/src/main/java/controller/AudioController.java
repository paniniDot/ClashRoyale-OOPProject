package control.controller.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Utility class for music.
 */
public final class AudioController {

  private static final float VOLUME = 0.1f;

  private final Music music;

  private AudioController(final String name) {
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
  public static AudioController getMenuMusic() {
    return new AudioController("sounds/Menu.mp3");
  }
  /**
   * plays battle music.
   * @return the game audio. 
   */
  public static AudioController getBattleMusic() {
    return new AudioController("sounds/Battle.mp3");
  }
}
