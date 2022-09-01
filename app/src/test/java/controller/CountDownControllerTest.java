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
  void test() throws InterruptedException {
    assertEquals(this.count.getTime(), 90);
    Thread.sleep(5050);
    assertEquals(this.count.getTime(), 85);
    this.count.setTime();
    assertEquals(this.count.getTime(), 90);
  }
}
