package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CountDownControllerTest {
  private CountDownController count;
  @BeforeEach
  public void setUp() {
    this.count = new CountDownController();
  }
  @Test
  void testElixir() {
    assertEquals(this.count.getTime(), 60);
    try {
      Thread.sleep(5050);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    assertEquals(this.count.getTime(), 55);
    this.count.setTime();
    assertEquals(this.count.getTime(), 60);
  }
}
