package model.actors;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;

/**
 * Actor who has DragAndDrop functionality.
 */
public class DragAndDropActor extends BaseActor {

  private final DragAndDrop dragAndDrop;

  /**
   * @param x
   *        x coordinate where the actor is placed.
   * @param y
   *        y coordinate where the actor is placed.
   * @param stage
   *        {@inheritDoc}
   */
  public DragAndDropActor(final float x, final float y, final Stage stage) {
    super(x, y, stage);
    this.dragAndDrop = new DragAndDrop();
    this.dragAndDrop.addSource(new Source(this) {

      @Override
      public Payload dragStart(final InputEvent event, final float x, final float y, final int pointer) {
        Payload payload = new Payload();
        payload.setDragActor(super.getActor());
        return payload;
      }
    });
  }
}
