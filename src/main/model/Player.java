package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.util.ArrayList;
import java.util.Objects;

public class Player implements Writable {

    // Player class contains pets that represents player's pet collection, allowing
    // player to add multiple pets,
    // remove pet, etc. Player inventory can store food objects and then feed to pet
    // when choose to.

    protected ArrayList<Pet> pets;
    private String playerName;
    private ArrayList<Food> inventory;

    public Player(String name) {
        this.playerName = name;
        this.pets = new ArrayList<>();
        this.inventory = new ArrayList<>();
    }

    // region getter/setter

    public String getPlayerName() {
        return this.playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public ArrayList<Pet> getPets() {
        return this.pets;
    }

    public Pet getPetByNumber(int petNumber) {
        return this.getPets().get(petNumber);
    }

    // endregion

    // EFFECTS: add up all pets closeness level and return it as a score.
    public int getScore() {
        int tempScore = 0;
        for (Pet pet : this.pets) {
            tempScore += pet.getAnimal().getClosenessLevel();
        }
        return tempScore;
    }

    // MODIFIES: this.pets
    // EFFECTS: add the pet passed to this method to the player pets collection.
    public void addPet(Pet pet) {
        this.pets.add(pet);
        EventLog.getInstance().logEvent(new Event(pet.getAnimal().getAnimalName()
                + " is added to player's pet collection"));
    }

    // REQUIRES: player.pets not empty.
    // MODIFIES: this.pets
    // EFFECTS: remove the specific pet from player.pets.
    public void releasePet(Pet pet) {
        this.pets.remove(pet);
        EventLog.getInstance().logEvent(new Event(pet.getAnimal().getAnimalName()
                + "is removed from player's pet collection"));
    }

    // MODIFIES: this.inventory
    // EFFECTS:add food into player inventory.
    public void addFoodToInventory(Food food) {
        inventory.add(food);
    }

    // REQUIRES: player.inventory not empty
    // MODIFIES: this.inventory
    // EFFECTS: remove food from player's inventory.
    public void removeFoodFromInventory(Food food) {
        inventory.remove(food);
    }

    // REQUIRES: player.inventory not empty, food is in inventory
    // MODIFIES: this.inventory
    // EFFECTS: return specific food.
    public Food getFoodFromInventory(String foodName) {
        return this.inventory.get(this.getFoodIndexFromInventory(foodName));
    }

    public ArrayList<Food> getInventory() {
        return inventory;
    }

    // REQUIRES: player pet collection not empty
    // EFFECTS: use the food on pet with that petIndex number.
    public void useFood(Food food, int petIndex) {
        this.getPets().get(petIndex).getAnimal().eat(food);
        EventLog.getInstance().logEvent(new Event(this.playerName + " used " + food.getName()
                + " on " + this.getPets().get(petIndex).getAnimal().getAnimalName() + "."));
    }

    // EFFECTS: takes the name of the food and returns the index of the food from
    // player's inventory with the same name.
    // return -1 if food is not found.
    public int getFoodIndexFromInventory(String foodName) {
        for (int i = 0; i < inventory.size(); i++) {
            if (this.inventory.get(i).getName().equals(foodName)) {
                return i;
            }
        }
        return -1;
    }

    // REQUIRES: input is a string
    // EFFECTS: return true if there is food in player inventory with the same name,
    // else returns false.
    public boolean isFoodInInventory(String foodName) {
        for (Food food : inventory) {
            if (food.getName().equals(foodName)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: converting player object to json object
    // Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Player Name", this.playerName);
        json.put("Pets", petsToJson());
        json.put("Inventory", inventoryToJson());

        return json;
    }

    // EFFECTS: converting inventory object to json array
    // Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    private JSONArray inventoryToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Food food : inventory) {
            jsonArray.put(food.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: converting pets object to json array
    // Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    private JSONArray petsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Pet pet : pets) {
            jsonArray.put(pet.getAnimal().toJson());
        }

        return jsonArray;
    }
}
