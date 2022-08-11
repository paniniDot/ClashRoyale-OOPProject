package view.actors;

import java.util.UUID;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Tower actor. 
 */
public class TowerActor extends BaseActor {

  private final UUID id;

  /**
   * Constructor.
   * 
   * @param id
   *          the unique identifier for this tower actor.
   * @param x
   *          x coordinate of the tower.
   * @param y
   *          y coordinate of the tower.
   * @param stage
   *          the stage where the tower has to be deployed.
   */
  public TowerActor(final UUID id, final float x, final float y, final Stage stage) {
    super(x, y, stage);
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
