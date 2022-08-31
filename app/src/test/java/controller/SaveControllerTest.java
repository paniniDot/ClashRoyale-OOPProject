package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import gdxtests.GdxTest;
import model.GlobalData;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SaveControllerTest extends GdxTest{

  private static final  String USER_DIR_PATH = System.getProperty("user.home") + File.separator + "royaleData" + File.separator + "user.json";
  private final File file = new File(USER_DIR_PATH); 
  
  @BeforeAll
  void createNew() {
    SaveController.loadUser();
    assertTrue(file.exists());
  }
  
  @Test
  void loadFile() {
    assertEquals(GlobalData.USER, SaveController.loadUser());
  }
  
  @Test
  void updateFile() {
    
  }
}
