package model.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import launcher.BaseGame;
import launcher.ClashRoyale;
import model.actors.BaseActor;
import model.utilities.AnimationUtilities;

/**
 * Menu screen implementation.
 */
public class MenuScreen extends BaseScreen {

  @Override
  public void initialize() {
    final var background = new BaseActor(0, 0, super.getMainStage());
    background.setAnimation(AnimationUtilities.loadTexture("backgrounds/menuBackground.png"));
    background.setSize(ClashRoyale.WIDTH, ClashRoyale.HEIGHT);
  }

  @Override
  public void update(final float dt) {
    if (Gdx.input.isKeyJustPressed(Keys.S)) {
      BaseGame.setActiveScreen(new GameScreen());
    }
  }

}
