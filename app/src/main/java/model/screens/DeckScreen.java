package model.screens;

import java.util.stream.Stream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Desck screen implementation.
 */

public class DeckScreen extends BaseScreen {
  private TextureAtlas atlas;
  private Skin skin;
  private Table table;
  private List list;
  private ScrollPane scrollPane;
  private TextButton add,remove,exit;
  
  @Override
  public void initialize() {
    atlas = new TextureAtlas("buttons/button.pack");
    skin = new Skin(Gdx.files.internal("buttons/menuSkin.json"), atlas);    
    table = new Table(skin);
    table.debug();
    
    list = new List<String>(skin, "small");
    list.setItems(new String[] {"one", "two", "three"}); 
    scrollPane = new ScrollPane(list, skin);
    add = new TextButton("ADD", skin);
    add.pad(15);
    remove = new TextButton("REMOVE", skin);
    remove.pad(15);
    exit = new TextButton("EXIT", skin);
    exit.pad(10);
    table.add("SELECT DECK");
  }
  
  @Override
  public void dispose() {
    super.dispose();
    this.atlas.dispose();
    this.skin.dispose();
  }

  @Override
  public void update(float dt) {
    // TODO Auto-generated method stub
    
  }

}
