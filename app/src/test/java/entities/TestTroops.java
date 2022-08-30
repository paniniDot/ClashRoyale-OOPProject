package entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.Vector2;

import model.entities.cards.Card;
import model.entities.cards.troops.Archer;
import model.entities.cards.troops.Barbarian;
import model.entities.cards.troops.Giant;
import model.entities.cards.troops.Troop;
import model.entities.cards.troops.Valkyrie;
import model.entities.cards.troops.Wizard;
import model.entities.users.User;
import model.entities.users.UserLevel;

class TestTroops {

  private Troop myTroop;
  private Troop anotherTroop;
  private final User panini = new User("Panini");

  /**
   * Creating an archer troop and checking if the HP and damage are right.
   */
  @Test
  void createTroop() {
    this.myTroop = Archer.create(panini, new Vector2(0, 0));
    if (panini.getCurrentLevel().equals(UserLevel.LVL1)) {
      assertEquals(this.myTroop.getCurrentHP(), 125);
      assertEquals(this.myTroop.getDamage(), 33);
    }
  }

  /**
   * Check if the attack works.
   */
  @Test
  void attackTroop() {
    this.myTroop = Barbarian.create(panini, new Vector2(0, 0));
    this.anotherTroop = Giant.create(new User("Bianchi"), new Vector2(1, 1));
    final var healthyGiant = Giant.create(new User("Neri"), new Vector2(2, 2));

    myTroop.setCurrentTarget(anotherTroop);
    myTroop.attackCurrentTarget();

    assertTrue(healthyGiant.getCurrentHP() > anotherTroop.getCurrentHP());
    assertEquals(myTroop.getDamage(), healthyGiant.getCurrentHP() - anotherTroop.getCurrentHP());
  }

  /**
   * Check if the control of the life is corrected.
   */
  @Test
  void killTroop() {
    this.anotherTroop = Valkyrie.create(panini, new Vector2(0, 0));
    this.anotherTroop.reduceHPBy(this.anotherTroop.getCurrentHP());

    assertTrue(anotherTroop.isDead());
  }

  /**
   * Check if createAnother() method works as expected.
   */
  @Test
  void copyTroop() {
    this.myTroop = Wizard.create(panini, new Vector2(1, 1));
    final Card copy = this.myTroop.createAnother(new Vector2(5, 5));

    assertEquals(copy.getClass(), myTroop.getClass());
    assertEquals(copy.getOwner(), myTroop.getOwner());
  }
}
