package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import controller.audio.AudioController;
import controller.audio.AudioMenuController;
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
    assertTrue(this.audio.isPlaying());
    this.audio.stop();
    assertFalse(this.audio.isPlaying());
  }

}
