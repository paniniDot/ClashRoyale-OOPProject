package view.actors.towers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

import view.actors.BaseActor;

/**
 * Tower actor. 
 */
public class TowerActor extends BaseActor {

  /**
   * Constructor.
   *
   * @param x
   *          x coordinate of the tower.
   * @param y
   *          y coordinate of the tower.
   * @param stage
   *          the stage where the tower has to be deployed.
   * @param animation
   *          {@inheritDoc}
   */
  public TowerActor(final float x, final float y, final Stage stage, final Animation<TextureRegion> animation) {
    super(x, y, stage, animation);
  }

}
