package view.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Simple entity (I.e. Queen or King towers).
 */
public class BaseActor extends Actor {

  private Animation<TextureRegion> animation;
  private float elapsedTime;
  private float rotate;
  private final Vector2 origin;
  private final float w;
  private final float h;

  /**
   * Constructor.
   * 
   * @param x
   *          x coordinate where the actor is placed.
   * @param y
   *          y coordinate where the actor is placed.
   * @param stage
   *          {@inheritDoc}
   */ 
  public BaseActor(final float x, final float y, final Stage stage, final Animation<TextureRegion> animation) {
    this.animation = animation;
    final TextureRegion region = animation.getKeyFrame(0);
    this.w = region.getRegionWidth();
    this.h = region.getRegionHeight();
    super.setSize(w, h);
    this.origin = new Vector2(x, y);
    this.elapsedTime = 0;
    super.setPosition(x, y);
    stage.addActor(this);
  }

  /**
   * Allows to set an animation for the actor.
   * @param animation
   *          {@inheritDoc}
   */
  public void setAnimation(final Animation<TextureRegion> animation) {
    this.animation = animation;
    final TextureRegion region = animation.getKeyFrame(0);
    super.setSize(region.getRegionWidth(), region.getRegionHeight());
  }

  /**
   * 
   * @return the center of the actor.
   */
  public Vector2 getCenter() {
    return new Vector2(super.getX() + (this.w / 2), super.getY() + (this.h / 2));
  }

  /**
   * 
   * @return the origin of the actor.
   */
  public Vector2 getOrigin() {
    return this.origin;
  }

  /**
   * @return whether the animation is finished or not.
   */
  public boolean isAnimationFinished() {
    return this.animation.isAnimationFinished(this.elapsedTime);
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
      batch.draw(this.animation.getKeyFrame(this.elapsedTime), 
          super.getX(), super.getY(), 
          this.w / 2, this.h / 2,
          super.getWidth(), super.getHeight(), 
          1.0f, 1.0f,
          this.rotate);
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
    super.setPosition(x - (this.w / 2), y - (this.h / 2));
  }

  /**
   * 
   * @return a vector2 containing the updated x,y positions of the actor.
   */
  public Vector2 getPosition() {
    return new Vector2(super.getX(), super.getY());
  }

  /**
   * update angle of the actor.
   * 
   * @param dst
   *            the destination position of this actor. Used to evaluate its rotation.
   */
  public void setRotation(final Vector2 dst) {
    this.rotate = (float) Math.toDegrees(Math.atan2(dst.y - super.getY() - (this.h / 2), dst.x - super.getX() - (this.w / 2))) - 90;
  }

  /**
   * update angle of the actor.
   * 
   * @param r
   *            the destination position of this actor. Used to evaluate its rotation.
   */
  public void setAngle(final float r) {
    this.rotate = r;
  }
}
