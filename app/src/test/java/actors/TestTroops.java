package actors;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.Vector2;

import model.actors.cards.troops.Troop;
import model.actors.cards.troops.Wizard;
import model.actors.users.User;

class TestTroops {

  private Troop wiz;

  @BeforeEach
  public void setUp() {
    final var panini = new User("Panini");
    this.wiz = Wizard.create(panini, new Vector2(0, 0));
  }

  @Test
  void simpleTest() {
    assertEquals(this.wiz.getCurrentHP(), 340);
    assertEquals(this.wiz.getDamage(), 130);
  }

}
