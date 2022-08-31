package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.GlobalData;

class SaveControllerTest {

  private static final  String USER_DIR_PATH = System.getProperty("user.home") + File.separator + "royaleData" + File.separator + "user.json";
  final File file = new File(USER_DIR_PATH); 
  
  @BeforeEach
  void createNew() {
    if (file.exists()) {
      file.delete();
    }

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
