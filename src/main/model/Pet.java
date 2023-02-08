package model;

import model.EventLog;


public class Pet  {
    //TODO: pet class seems unnecessary.
    //Acts as a wrapper class. Could remove and put methods into animal later

    private Animal animal;

    //MODIFIES: this
    //EFFECTS: create different subclass object depending on input string type. If type entered is not any case,
    // choose dog object instead.
    public Pet(String name, String type) {
        switch (type) {
            case "Dog":
                this.animal = new Dog(name);
                break;

            case "Cat":
                this.animal = new Cat(name);
                break;

            default:
                this.animal = new Dog(name);
                System.out.println("Type entered is invalid, picked Dog automatically ");

        }
    }

    public Animal getAnimal() {
        return this.animal;
    }

}