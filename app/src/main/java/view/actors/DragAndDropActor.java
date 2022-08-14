package view.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Actor who has DragAndDrop functionality.
 */
public class DragAndDropActor extends BaseActor {

  private final DragAndDropActor self;
  private float grabOffsetX;
  private float grabOffsetY;
  private boolean isDraggable;


  /**
   * Constructor.
   * 
   * @param x
   *        x coordinate where the actor is placed.
   * @param y
   *        y coordinate where the actor is placed.
   * @param s
   *        {@inheritDoc}.
   */
  public DragAndDropActor(final float x, final float y, final Stage s) {
    super(x, y, s);
    self = this;
    isDraggable = true;
    this.addListener(new InputListener() {

      @Override
      public boolean touchDown(final InputEvent event, final float eventOffsetX, final float eventOffsetY, final int pointer, final int button) {
        if (!self.isDraggable()) {
          return false;
        }
        self.grabOffsetX = eventOffsetX;
        self.grabOffsetY = eventOffsetY;
        self.toFront();
        self.onDragStart();
        return true; // returning true indicates other touch methods are called
      }

      @Override
      public void touchDragged(final InputEvent event, final float eventOffsetX, final float eventOffsetY, final int pointer) {
        final float deltaX = eventOffsetX - self.grabOffsetX;
        final float deltaY = eventOffsetY - self.grabOffsetY;
        self.moveBy(deltaX, deltaY);
      }

      @Override
      public void touchUp(final InputEvent event, final float eventOffsetX, final float eventOffsetY, final int pointer, final int button) {
        // return object to original size when dropped by player
        self.onDrop();
      }
    });

  }

  /**
   * Set whether this actor can be dragged.
   * 
   * @param d
   *        true if actor can be dragged, false otherwise.
   */
  public void setDraggable(final boolean d) {
    isDraggable = d;
  }

  /**
   * Check if this actor can be dragged.
   * 
   * @return whether the actor is draggable or not.
   */
  public boolean isDraggable() {
    return isDraggable;
  }

  /**
   * Called when drag begins; extending classes may override this method.
   */
  public void onDragStart() {
  }

  /**
   * Called when drop occurs; extending classes may override this method.
   */
  public void onDrop() {
    super.setPosition(this.getPosition().x, this.getPosition().y);
    System.out.println("Screen: " + super.getX() + ", " + super.getY());
  }

  /**
   * Place actor in new coordinates.
   * 
   * @param spot
   *            new coordinates.
   */
  public void moveTo(final Vector2 spot) {
    this.addAction(Actions.moveTo(spot.x, spot.y, 2f));
    System.out.println("Posizione da dragAndDropScreen :" + super.getPosition());
  }

  /**
   * update angle of the troop.
   * 
   * @param dst
   *          the destination position of this actor, used to evaluate the rotation of the animation. 
   */
  public void setRotation(final Vector2 dst) {
    super.rotate(dst);
  }

}
