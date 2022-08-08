package view.screens;

import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import control.controller.Controller;
import control.controller.GameController;
import control.launcher.ClashRoyale;

import model.actors.Attackable;
import model.actors.cards.Card;
import model.actors.towers.KingTower;
import model.actors.towers.QueenTower;
import model.utilities.AnimationUtilities;
import model.utilities.Pair;
import model.utilities.RectDrawer;
import model.utilities.ingame.MapUnit;

import view.actors.BaseActor;

/**
 * In-game screen implementation.
 */
public class GameScreen extends BaseScreen {

  private SpriteBatch sprite;
  private BitmapFont gamefont;

  /**
   * Constructor.
   * 
   * @param controller
   *                  {@inheritDoc}.
   */
  public GameScreen(final Controller controller) {
    super(controller);
  }

  @Override
  public void initialize() {
    super.getController().playMusic();
    sprite = new SpriteBatch();
    gamefont = new BitmapFont(Gdx.files.internal("Fonts/font.fnt"));
    final var arena = new BaseActor(0, 0, super.getMainStage());
    arena.setAnimation(AnimationUtilities.loadTexture("arenas/arena1.png"));
    arena.setSize(ClashRoyale.WIDTH, ClashRoyale.HEIGHT);
    ((GameController) super.getController()).loadActors();
    ((GameController) super.getController()).loadTowers();
  }

//  private void move(final List<Pair<Pair<Attackable, Attackable>, List<Vector2>>> spots) {
//      spots.forEach(s -> {
//        if (!s.getX().getX().getClass().equals(QueenTower.class) && !s.getX().getX().getClass().equals(KingTower.class)) {
//          if (!Gdx.input.isTouched()) {
//            if (this.map.getMap().containsVertex(this.map.getMapUnitFromPixels(s.getX().getX().getCenter())) && ((Card) s.getX().getX()).isDraggable() ) {
//                ((Card) s.getX().getX()).setDraggable(false);
//            } else if (!this.map.getMap().containsVertex(this.map.getMapUnitFromPixels(s.getX().getX().getCenter()))) {
//                ((Card) s.getX().getX()).setPosition(((Card) s.getX().getX()).getOrigin().x, ((Card) s.getX().getX()).getOrigin().y);
//            } else if (s.getY().size() <= 3 && s.getY().size() > 1) {
//                ((Card) s.getX().getX()).setRotation(s.getX().getY().getCenter());
//            } else if (s.getY().size() > 3) {
//                ((Card) s.getX().getX()).setRotation(s.getY().get(1));
//                ((Card) s.getX().getX()).moveTo(new Vector2(s.getY().get(1).x - ((Card) s.getX().getX()).getWidth() / 2, s.getY().get(1).y - ((Card) s.getX().getX()).getHeight() / 2));
//              }
//            } 
//          } 
//      });
//    }


  @Override
  public void update(final float dt) {
    super.getController().update(dt);
  }

  @Override
  public void render(final float dt) {
    super.render(dt);
    sprite.begin();
    gamefont.draw(sprite, "elisir " + ((GameController) super.getController()).getCurrentElixir(), 100, 100);
    gamefont.draw(sprite, "durata " + ((GameController) super.getController()).getLeftTime(), 100, 200);
    sprite.end();
    //RectDrawer.showDebugBoundingBoxes(this.map.getMap().vertexSet().stream().map(MapUnit::getUnitRectangle).collect(Collectors.toList()), Color.BLUE);
    //RectDrawer.showDebugBoundingBoxes(this.map.getMap().vertexSet().stream().filter(v -> v.getType().equals(MapUnit.Type.TERRAIN)).map(MapUnit::getUnitRectangle).collect(Collectors.toList()), Color.BLUE);
    //RectDrawer.showDebugBoundingBoxes(this.map.getMap().vertexSet().stream().filter(v -> v.getType().equals(MapUnit.Type.TOWER)).map(MapUnit::getUnitRectangle).collect(Collectors.toList()), Color.RED);
  }
}
