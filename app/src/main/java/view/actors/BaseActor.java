package view.actors;

import java.util.Optional;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Simple entity (I.e. Queen or King towers).
 */
public class BaseActor extends Actor {

  private Optional<Animation<TextureRegion>> animation;
  private float elapsedTime;
  private float rotate;
  private final Vector2 origin;
  private TextureRegion region;
  private float w;
  private float h;

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
    this.animation = Optional.of(animation);
    this.region = animation.getKeyFrame(0);
    this.w = region.getRegionWidth();
    this.h = region.getRegionHeight();
    super.setSize(w, h);
    this.origin = new Vector2(x, y);
    stage.addActor(this);
    this.elapsedTime = 0;
    super.setPosition(x, y);
  }

  /**
   * Allows to set an animation for the actor.
   * @param animation
   *          {@inheritDoc}
   */
  public void setAnimation(final Animation<TextureRegion> animation) {
    this.animation = Optional.of(animation);
    this.region = this.animation.get().getKeyFrame(0);
    this.w = region.getRegionWidth();
    this.h = region.getRegionHeight();
    super.setSize(w, h);
  }

  /**
   * @return a rectangle defining the boundaries of the actor.
   */
  public Rectangle getBoundaries() {
    return new Rectangle(super.getX(), super.getY(), super.getWidth(), super.getHeight());
  }

  /**
   * 
   * @return the center of the actor.
   */
  public Vector2 getCenter() {
    //System.out.println("Rettangolo = " + this.getBoundaries() + " Centro del rettangolo = " + this.getBoundaries().getCenter(new Vector2()));
    return this.getBoundaries().getCenter(new Vector2());
  }

  /**
   * 
   * @return the origin of the actor.
   */
  public Vector2 getOrigin() {
    return this.origin;
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
      batch.draw(this.animation.get().getKeyFrame(this.elapsedTime), 
          super.getX(), super.getY(), 
          this.getWidth() / 2, this.getHeight() / 2,
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
    super.setPosition(x - (w / 2), y - (h / 2));
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
  public void rotate(final Vector2 dst) {
    this.rotate = (float) Math.toDegrees(Math.atan2(dst.y - this.getY() - (h / 2), dst.x - this.getX() - (w / 2))) - 90;
  }
}
