package view.actors;

import java.util.UUID;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Deployable card actor.
 */
public class CardActor extends DragAndDropActor {

  private final UUID id;

  /**
   * Constructor.
   * 
   * @param id
   *          the unique identifier for this actor.
   * @param x
   *          x coordinate where the actor has to be deployed.
   * @param y
   *          y coordinate where the actor has to be deployed.
   * @param s
   *          the stage in which the actor has to be deployed.
   */
  public CardActor(final UUID id, final float x, final float y, final Stage s, final Animation<TextureRegion> animation){
    super(x, y, s, animation);
    this.id = id;
  }

  /**
   * 
   * @return the unique id for this actor.
   */
  public UUID getSelfId() {
    return this.id;
  }

}
