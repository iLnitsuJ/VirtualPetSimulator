package model;

import org.json.JSONObject;


public class Cat extends Animal {

    //Cat class is just one of the pet type player could choose. It extends Animal and have specific food reaction and
    //will have unique interaction later on.
    private String type = "Cat";

    public Cat(String name) {
        super(name);
    }

    //have not implemented yet
    @Override
    public void uniqueInteraction() {

    }

    //EFFECTS: prints out reaction after eaten food.
    @Override
    public void foodReaction() {
        System.out.println("Meowwwwww");
    }

    @Override
    public String getType() {
        return this.type;
    }

    //EFFECTS: converting Cat object to json object
    //Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("animalName",this.getAnimalName());
        jsonObject.put("healthLevel",Integer.toString(this.getHealthLevel()));
        jsonObject.put("closenessLevel",Integer.toString(this.getClosenessLevel()));
        jsonObject.put("poopMeter",Integer.toString(this.getPoopMeter()));
        jsonObject.put("starveMeter",Integer.toString(this.getStarveMeter()));
        jsonObject.put("petState",Integer.toString(this.getPetState()));
        jsonObject.put("type",this.getType());

        return  jsonObject;
    }


}


