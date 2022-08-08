package view.actors;

import java.util.Optional;
import java.util.UUID;

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

  /**
   * Constructor.
   * 
   * @param id
   *          the unique id for this actor.
   * @param x
   *          x coordinate where the actor is placed.
   * @param y
   *          y coordinate where the actor is placed.
   * @param stage
   *          {@inheritDoc}
   */
  public BaseActor(final float x, final float y, final Stage stage) {
    super.setPosition(x, y);
    this.origin = new Vector2(x, y);
    this.animation = Optional.empty();
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
    final TextureRegion region = this.animation.get().getKeyFrame(0);
    final float w = region.getRegionWidth();
    final float h = region.getRegionHeight();
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
    super.setPosition(x, y);
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
   */
  public void rotate(final Vector2 dst) {
    Vector2 src = getCenter();
    if (src.x < dst.x + 10 && src.x > dst.x - 10 && src.y < dst.y) {
      rotate = 0;
    } else if (src.x < dst.x + 10 && src.x > dst.x - 10 && src.y > dst.y) {
      rotate = 180;
    } else if (src.y < dst.y + 10 && src.y > dst.y - 10 && src.x < dst.x) {
      rotate = -90;
    } else if (src.y < dst.y + 10 && src.y > dst.y - 10 && src.x > dst.x) {
      rotate = 90;
    } else if (src.x < dst.x && src.y < dst.y) {
      rotate = -45;
    } else if (src.x > dst.x && src.y < dst.y) {
      rotate = 45;
    } else if (src.x < dst.x && src.y > dst.y) {
      rotate = 225;
    } else if (src.x > dst.x && src.y > dst.y) {
      rotate = 135;
    } 
  }


}
