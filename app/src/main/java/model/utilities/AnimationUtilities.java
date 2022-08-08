package model.utilities;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/** 
 * Utility class used to animate actors.
 */
public final class AnimationUtilities {

  private AnimationUtilities() {
  }

  /**
   * Builds an animation (a sequence of frames).
   * 
   * @param fileNames
   *              The names of files used.
   * @param frameDuration
   *              How much time each frame has to last.
   * @param loop
   *              Whether the animation has to repeat over and over once frame are finished or not.
   * @return the animation.
   */
  public static Animation<TextureRegion> loadAnimationFromFiles(final List<String> fileNames, final float frameDuration, final boolean loop) { 
    final int fileCount = fileNames.size();
    final var textureArray = new Array<TextureRegion>();
    for (int n = 0; n < fileCount; n++) {
      final String fileName = fileNames.get(n);
      final var texture = new Texture(Gdx.files.internal(fileName));
      texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
      textureArray.add(new TextureRegion(texture));
    }
    final var anim = new Animation<TextureRegion>(frameDuration, textureArray);
    if (loop) {
      anim.setPlayMode(Animation.PlayMode.LOOP);
    } else {
      anim.setPlayMode(Animation.PlayMode.NORMAL);
    }
    return anim;
  }

  /**
   * Builds an animation composed by one frame for static entities.
   * @param fileName
   *              the name of file to be used.
   * @return the animation.
   */
  public static Animation<TextureRegion> loadTexture(final String fileName) {
    final var fileNames = List.of(fileName);
    return AnimationUtilities.loadAnimationFromFiles(fileNames, 1, true);
  }
}
