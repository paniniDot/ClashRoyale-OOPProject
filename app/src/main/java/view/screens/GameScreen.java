package view.screens;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import control.controller.Controller;
import control.controller.game.BotGameController;
import control.launcher.ClashRoyale;

import model.utilities.AnimationUtilities;

import view.actors.BaseActor;
import view.actors.CardActor;
import view.actors.TowerActor;

/**
 * In-game screen implementation.
 */
public class GameScreen extends BaseScreen {

  private SpriteBatch sprite;
  private BitmapFont gamefont;
  private List<CardActor> playerCards;
  private List<CardActor> botCards;
  private List<TowerActor> playerTowers;
  private List<TowerActor> botTowers;

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
    System.out.println(super.getMainStage());
    sprite = new SpriteBatch();
    gamefont = new BitmapFont(Gdx.files.internal("Fonts/font.fnt"));
    final var arena = new BaseActor(0, 0, super.getMainStage());
    arena.setAnimation(AnimationUtilities.loadTexture("arenas/arena1.png"));
    arena.setSize(ClashRoyale.WIDTH, ClashRoyale.HEIGHT);
    this.playerCards = ((BotGameController) super.getController()).loadPlayerActors(super.getMainStage());
    this.botCards = ((BotGameController) super.getController()).loadBotActors(super.getMainStage());
    this.playerTowers = ((BotGameController) super.getController()).loadPlayerTowers(super.getMainStage());
    this.botTowers = ((BotGameController) super.getController()).loadBotTowers(super.getMainStage());
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
    ((BotGameController) super.getController()).updateActorPositions(playerCards, botCards);
  }

  @Override
  public void render(final float dt) {
    super.render(dt);
    sprite.begin();
    gamefont.draw(sprite, "elisir " + ((BotGameController) super.getController()).getPlayerCurrentElixir(), 100, 100);
    gamefont.draw(sprite, "durata " + ((BotGameController) super.getController()).getLeftTime(), 100, 200);
    sprite.end();
    //RectDrawer.showDebugBoundingBoxes(this.map.getMap().vertexSet().stream().map(MapUnit::getUnitRectangle).collect(Collectors.toList()), Color.BLUE);
    //RectDrawer.showDebugBoundingBoxes(this.map.getMap().vertexSet().stream().filter(v -> v.getType().equals(MapUnit.Type.TERRAIN)).map(MapUnit::getUnitRectangle).collect(Collectors.toList()), Color.BLUE);
    //RectDrawer.showDebugBoundingBoxes(this.map.getMap().vertexSet().stream().filter(v -> v.getType().equals(MapUnit.Type.TOWER)).map(MapUnit::getUnitRectangle).collect(Collectors.toList()), Color.RED);
  }
}
