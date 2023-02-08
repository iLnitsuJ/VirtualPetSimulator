package model;

import org.json.JSONObject;
import persistance.Writable;

public class Food implements Writable {

    //Food class contains the name of the food and the amount of health pet will recover.
    //Food objects can be eaten by pets, store in player's inventory, and later on sold in shop class.

    private int starvePointRecovered;
    private String name;

    public Food(String name, int starvePointRecovered) {
        this.name = name;
        this.starvePointRecovered = starvePointRecovered;
    }

    //region getter/setter

    public int getStarvePointRecovered() {
        return starvePointRecovered;
    }

    public void setStarvePointRecovered(int starvePointRecovered) {
        this.starvePointRecovered = starvePointRecovered;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //EFFECTS: converting food object to json object
    //Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",this.getName());
        jsonObject.put("starvePointRecovered",Integer.toString(this.getStarvePointRecovered()));
        return jsonObject;
    }

    //endregion


}
