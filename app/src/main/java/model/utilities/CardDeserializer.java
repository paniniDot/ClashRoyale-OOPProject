package model.utilities;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import model.actors.cards.Card;
import model.actors.cards.buildings.InfernoTower;
import model.actors.cards.troops.Barbarian;
import model.actors.cards.troops.Giant;
import model.actors.cards.troops.Wizard;

/**
 *
 * Custom serializer and deserializer for abstract Card class.
 * 
 */
public class CardDeserializer implements JsonSerializer<Card>, JsonDeserializer<Card> { 

  private static final Map<String, Class<?>> CLASSES = Map.of("Wizard", Wizard.class, "Barbarian", Barbarian.class, "Giant", Giant.class, "InfernoTower", InfernoTower.class );

@Override
public Card deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) 
  throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();
    String type = jsonObject.get("MyType").getAsString();
    try {
        // String thePackage = "com.onetwentyonegwatt.MeasurementLib";
        return context.deserialize(json, Class.forName(type));
    } catch (ClassNotFoundException e) {
        throw new JsonParseException("Unknown element type: " + type, e);
    }

}

@Override
public JsonElement serialize(final Card src, final Type typeOfSrc, final JsonSerializationContext context) {
  final JsonObject member = new JsonObject();
  member.addProperty("type", src.getClass().getName());
  member.add("data", context.serialize(src));
  return member;
}



}
