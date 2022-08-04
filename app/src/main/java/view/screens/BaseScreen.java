package model.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Abstract class that extends Screen class functionalities.
 */
public abstract class BaseScreen implements Screen {

  private final Stage mainStage;
  private final Stage uiStage;

  /** 
   * Constructor.
   */
  public BaseScreen() {
    this.mainStage = new Stage();
    this.uiStage = new Stage();

    this.initialize();
  }

  /**
   * 
   * @return the main stage.
   */
  public Stage getMainStage() {
    return this.mainStage;
  }

  /**
   * 
   * @return a secondary stage.
   */
  public Stage getUiStage() {
    return this.uiStage;
  }

  /**
   * Initialize all components and stages.
   */
  public abstract void initialize();

  /**
   * Update all components and stages once each dt.
   * @param dt
   *          delta time how often a screen have to be updated.
   */
  public abstract void update(float dt);

  @Override
  public void render(final float dt) {
    // act methods
    this.uiStage.act(dt);
    this.mainStage.act(dt);

    // defined by user
    this.update(dt);

    // clear the screen
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // draw the graphics
    this.mainStage.draw();
    this.uiStage.draw();
  }

  @Override
  public void show() {
  }

  @Override
  public void resize(final int width, final int height) {
    this.mainStage.getViewport().update(width, height, true);
    this.uiStage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void hide() {
  }

  @Override
  public void dispose() {
    this.mainStage.dispose();
    this.uiStage.dispose();
  }

}
