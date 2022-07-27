package model.actors;

import java.util.Optional;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Simple entity (I.e. Queen or King towers).
 */
public class BaseActor extends Actor {

  private Optional<Animation<TextureRegion>> animation;
  private Optional<Rectangle> boundaries;
  private float elapsedTime;

  /**
   * 
   * @param x
   *          x coordinate where the actor is placed.
   * @param y
   *          y coordinate where the actor is placed.
   * @param stage
   *          {@inheritDoc}
   */
  public BaseActor(final float x, final float y, final Stage stage) {
    super();
    super.setPosition(x, y);
    this.animation = Optional.empty();
    this.boundaries = Optional.empty();
    stage.addActor(this);
    this.elapsedTime = 0;
  }

  /**
   * Allows to set an animation for the actor.
   * @param animation
   *          {@inheritDoc}
   */
  public void setAnimation(final Animation<TextureRegion> animation) {
    this.animation = Optional.of(animation);
    TextureRegion region = this.animation.get().getKeyFrame(0);
    float w = region.getRegionWidth();
    float h = region.getRegionHeight();
    super.setSize(w, h);
    this.boundaries = Optional.of(new Rectangle(super.getX(), super.getY(), w, h));
  }

  /**
   * @return a rectangle defining the boundaries of the actor.
   */
  public Rectangle getBoundaries() {
    return this.boundaries.get();
  }

  /**
   * @param other
   *        {@inheritDoc}
   * @return whether this actor overlaps with the one passed as argument.
   */
  public boolean overlaps(final BaseActor other) {
    return this.getBoundaries().overlaps(other.getBoundaries());
  }

  /**
   * @return whether the animation is finished or not.
   */
  public boolean isAnimationFinished() {
    return this.animation.get().isAnimationFinished(this.elapsedTime);
  }

  @Override
  public void act(final float dt) {
    super.act(dt);
    this.elapsedTime += dt;
  }

  @Override 
  public void draw(final Batch batch, final float parentAlpha) {
    super.draw(batch, parentAlpha);
    if (super.isVisible()) {
      batch.draw(this.animation.get().getKeyFrame(this.elapsedTime), super.getX(), super.getY());
    }
  }

  /**
   * Updates the coordinates of the troop.
   * 
   * @param x
   *        the new x coordinate.
   * @param y 
   *        the new y coordinate. 
   */
  public void setPosition(final float x, final float y) {
    super.setPosition(x, y);
  }

}
