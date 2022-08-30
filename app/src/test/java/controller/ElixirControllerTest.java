package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ElixirControllerTest {

  private ElixirController elixir;
  @BeforeEach
  public void setUp() {
    this.elixir = new ElixirController();
  }
  @Test
  void testElixir() {
    assertEquals(this.elixir.getElixirCount(), 0);
    try {
      Thread.sleep(5050);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    this.elixir.decrementElixir(5);
    assertEquals(this.elixir.getElixirCount(), 0);
    this.elixir.resetElixirCount();
    assertEquals(this.elixir.getElixirCount(), 0);
  }

}
