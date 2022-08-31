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
  void test() throws InterruptedException {
    assertEquals(this.elixir.getElixirCount(), 0);
    Thread.sleep(12050);
    assertEquals(this.elixir.getElixirCount(), 10);
    this.elixir.decrementElixir(5);
    assertEquals(this.elixir.getElixirCount(), 5);
    this.elixir.resetElixirCount();
    assertEquals(this.elixir.getElixirCount(), 0);
  }

}
