package model.screens;

import java.util.stream.Stream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Desck screen implementation.
 */

public class DeckScreen extends BaseScreen {
  private TextureAtlas atlas;
  private Skin skin;
  private Table table;
  private List list;
  
  @Override
  public void initialize() {
    atlas = new TextureAtlas("buttons/button.pack");
    skin = new Skin(Gdx.files.internal("buttons/menuSkin.json"), atlas);    
    table = new Table(skin);
    
 //   list = new List(skin, new String[]{"carta1","carta2","carta3"}); 
  
    
    table.add("SELECT DECK");
  }

  @Override
  public void update(float dt) {
    // TODO Auto-generated method stub
    
  }

}
