package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.badlogic.gdx.math.Vector2;

import gdxtests.GdxTest;

import model.entities.Attackable;
import model.entities.cards.troops.Wizard;
import model.entities.users.Bot;
import model.entities.users.User;
import model.utilities.ingame.GameMap;
import model.utilities.ingame.MapUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GameMapTest extends GdxTest {

  private GameMap map;

  @BeforeAll
  void setUpGameMap() {
    this.map = new GameMap();
  }

  @Test
  void emptyPathTest() {
    final var position = new Vector2(350, 350);
    final Attackable attackable = Wizard.create(new User("Panini"), position);
    final List<Attackable> enemy = List.of(Wizard.create(new Bot(), position));
    assertEquals(this.map.getNextPosition(attackable, enemy), position);
  }

  @Test
  void simplePathTest() {
    final var position = new Vector2(350, 350);
    final Attackable attackable = Wizard.create(new User("Panini"), position);
    final List<Attackable> enemy = List.of(Wizard.create(new Bot(), new Vector2(350, 400)));
    IntStream.range(0, 3).forEach(i -> attackable.setPosition(this.map.getNextPosition(attackable, enemy)));
    assertEquals(this.map.getMapUnitFromPosition(attackable.getPosition()), this.map.getMapUnitFromPosition(enemy.get(0).getPosition()));
  }

  @Test
  void isOutOfMapTest() {
    final var outOfMapPosition = new Vector2(20, 20);
    assertFalse(this.map.containsPosition(outOfMapPosition));
  }

  @Test
  void isTowerTest() {
    final var towerPosition = new Vector2(250, 350);
    assertEquals(this.map.getMapUnitFromPosition(towerPosition).getType(), MapUnit.Type.TOWER);
  }
}
