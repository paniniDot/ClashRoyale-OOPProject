package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import controller.menu.AudioMenuController;
import gdxtests.GdxTest;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AudioControllerTest extends GdxTest {
 private AudioController audio;
 @BeforeAll
 public void setUp() {
   this.audio = new AudioMenuController();
 }
  @Test
  void test() {
    assertTrue(audio.isPlaying());
    this.audio.stop();
    assertFalse(audio.isPlaying());
  }

}
