package gdxtests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;

/**
 * Skeleton class to test Gdx applications using an headless implementation.
 */
public class GdxTest {
  private Application application;

  @BeforeAll
  void initApplication() {
    this.application = new HeadlessApplication(new ApplicationListener() {

      @Override
      public void resume() {
      }

      @Override
      public void resize(final int width, final int height) {
      }

      @Override
      public void render() {
      }

      @Override
      public void pause() {
      }

      @Override
      public void dispose() {
      }

      @Override
      public void create() {
      }
    });
    Gdx.gl20 = Mockito.mock(GL20.class);
    Gdx.gl = Gdx.gl20;
  }

  @AfterAll
  void cleanUp() {
      this.application.exit();
      this.application = null;
  }
}
