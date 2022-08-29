package controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Controller class for music.
 */
public abstract class AudioController {

  private static final float VOLUME = 0.1f;

  private final Music music;
  /**
   * Constructor.
   * @param name name of the audio file.
   */
  public AudioController(final String name) {
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
}
