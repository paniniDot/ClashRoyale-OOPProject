package actors;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import model.actors.cards.troops.Troop;
import model.actors.cards.troops.Wizard;
import model.actors.users.User;

class TestTroops {

  private User panini;
  private Troop wiz;

  @BeforeEach
  public void setUp() {
    this.panini = new User("Panini");
    this.wiz = Wizard.create(panini, new Stage(), new Vector2(0,0));
  }

  @Test
  void simpleTest() {
    assertEquals(this.wiz.getCurrentHP(), 340);
    assertEquals(this.wiz.getDamage(), 130);
  }

}
