package controller.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Controller class for music.
 */
public class AudioController {

  private static final float VOLUME = 0.1f;

  private final Music music;
  private boolean playing;

  /**
   * Protected constructor in order to be unable to instantiate this controller outside its package.
   * @param name name of the audio file.
   */
  protected AudioController(final String name) {
    this.music = Gdx.audio.newMusic(Gdx.files.internal(name));
    this.music.setLooping(true);
    this.playing = false;
  }

  /**
   * starts music.
   */
  public void play() {
    this.music.play();
    this.playing = true;
    this.music.setVolume(VOLUME);
  }
  /**
   * stops music.
   */
  public void stop() {
    this.music.stop();
    this.playing = false;
  }

  /**
   * is Playing.
   * @return Boolean.
   */
  public Boolean isPlaying() {
    return this.playing;
  }
}
