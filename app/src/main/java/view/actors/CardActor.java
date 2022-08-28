package view.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Deployable card actor.
 */
public class CardActor extends DragAndDropActor {

  /**
   * Constructor.
   * 
   * @param x
   *          x coordinate where the actor has to be deployed.
   * @param y
   *          y coordinate where the actor has to be deployed.
   * @param s
   *          the stage in which the actor has to be deployed.
   * @param animation
   *          {@inheritDoc}
   */
  public CardActor(final float x, final float y, final Stage s, final Animation<TextureRegion> animation) {
    super(x, y, s, animation);
  }

}
