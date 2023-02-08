package persistance;

import model.Food;
import model.Pet;
import model.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {

    //Json reader class consist of a string of source file directory. It turns jsonObject into corresponding objects.
    //Then by re-adding these objects, we can reload player progress.

    private String sourceFile;

    public JsonReader(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    //EFFECTS: read player profile from source file and return it
    //Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git from JsonReader class
    public Player read() throws IOException {
        String jsonData = readFile(sourceFile);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseJsonToPlayer(jsonObject);
    }

    //EFFECTS: reads source file as string and returns it
    //Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git from JsonReader class
    public String readFile(String source) throws IOException {
        StringBuilder content = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(content::append);
        }

        return content.toString();
    }


    //EFFECTS: Convert JSONObject that consist of player's data into player object by recreating and
    // adding all necessary objects for player object.
    //Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git from JsonReader class
    public Player parseJsonToPlayer(JSONObject jsonObject) {
        String name = jsonObject.getString("Player Name");
        Player player = new Player(name);
        addPetsFromJson(player, jsonObject);
        addInventoryFromJson(player, jsonObject);
        return player;
    }

    //EFFECTS: convert JSONObject that consist of Inventory array in Json array to Player's inventory. Do this by
    // traverse through the array and add each corresponding food object from jsonObject and add it to
    // player's inventory.
    //Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git from JsonReader class
    private void addInventoryFromJson(Player player, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Inventory");
        for (Object json : jsonArray) {
            JSONObject nextFood = (JSONObject) json;
            addFoodFromJson(player,nextFood);
        }
    }

    //EFFECTS: convert JSONObject that consist of Food object's data to food object by recrating a food object with
    // data extracted from such json object.
    //Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git from JsonReader class
    private void addFoodFromJson(Player player, JSONObject jsonObject) {
        Food food = new Food(jsonObject.getString("name"),
                Integer.parseInt(jsonObject.getString("starvePointRecovered")));
        player.addFoodToInventory(food);
    }

    //EFFECTS: convert JSONObject that consist of arraylist pets and add each pet object from json object to
    // player's pets by traversing through the json array.
    //Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git from JsonReader class
    private void addPetsFromJson(Player player, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Pets");
        for (Object json : jsonArray) {
            JSONObject nextPet = (JSONObject) json;
            addPetFromJson(player,nextPet);
        }

    }

    //EFFECTS: Adding pet from json object by extracing data in such json object and then recreate another pet
    // object and have player add it.
    //Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git from JsonReader class
    private void addPetFromJson(Player player, JSONObject jsonObject) {

        Pet pet = new Pet(jsonObject.getString("animalName"),jsonObject.getString("type"));
        pet.getAnimal().setHealthLevel(Integer.parseInt(jsonObject.getString("healthLevel")));
        pet.getAnimal().setClosenessLevel(Integer.parseInt(jsonObject.getString("closenessLevel")));
        pet.getAnimal().setPoopMeter(Integer.parseInt(jsonObject.getString("poopMeter")));
        pet.getAnimal().setStarveMeter(Integer.parseInt(jsonObject.getString("starveMeter")));
        pet.getAnimal().setPetState(Integer.parseInt(jsonObject.getString("petState")));

        player.addPet(pet);

    }


}
