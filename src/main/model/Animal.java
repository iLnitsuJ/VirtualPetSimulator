package model;

import persistance.Writable;

import java.util.Objects;


public abstract class Animal implements Writable {

    //Animal class contains the status for animal, pet state shows what state the pet is in. health level regens over
    // time and decrease according to starve meter. Starve meter increase when food is consumed, decrease overtime.
    // Poop meter increase when food is consumed, will only decrease to zero once hit 100 and pet will go into
    // dirty state. Closeness acts as the score of the game. increase as food is consumed, decreased if pet is dying.


    private int healthLevel;
    private int closenessLevel;
    private int poopMeter;
    private int starveMeter;
    private int petState;  // 0: normal  5:dirty (double health reduction under starve)
    private String animalName;
    private Action action;
    private final int dirtyState = 5;
    private final int dyingState = 4;
    private final int maxValueForMeter = 100;
    private final int minValueForMeter = 0;



    public Animal(String name) {
        this.healthLevel = 100;
        this.closenessLevel = 0;
        this.poopMeter = 0;
        this.animalName = name;
        this.action = new Action();
        this.starveMeter = 100;
        this.petState = 0;
    }

    //region getter/setters


    public int getPetState() {
        return petState;
    }

    public void setPetState(int petState) {
        this.petState = petState;
    }

    public int getStarveMeter() {
        return starveMeter;
    }

    public void setStarveMeter(int starveMeter) {
        this.starveMeter = starveMeter;
    }

    public int getHealthLevel() {
        return this.healthLevel;
    }

    public void setHealthLevel(int newHealth) {
        this.healthLevel = newHealth;
    }

    public int getClosenessLevel() {
        return this.closenessLevel;
    }

    public void setClosenessLevel(int closenessLevel) {
        this.closenessLevel = closenessLevel;
    }

    public int getPoopMeter() {
        return this.poopMeter;
    }

    public void setPoopMeter(int newPopMeter) {
        this.poopMeter = newPopMeter;
    }

    public Action getAction() {
        return this.action;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }
    //endregion


    // overiding equals to compare animal objects.
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Animal animal = (Animal) o;
        return healthLevel == animal.healthLevel && closenessLevel == animal.closenessLevel
                && poopMeter == animal.poopMeter && starveMeter == animal.starveMeter
                && petState == animal.petState && dirtyState == animal.dirtyState
                && dyingState == animal.dyingState && maxValueForMeter == animal.maxValueForMeter
                && minValueForMeter == animal.minValueForMeter && Objects.equals(animalName, animal.animalName)
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(healthLevel, closenessLevel, poopMeter, starveMeter, petState, animalName, dirtyState,
                dyingState, maxValueForMeter, minValueForMeter);
    }

    //MODIFIES: this
    //EFFECTS: Consuming food increase starve meter, poop meter, and closeness. Then does a food reaction.
    public void eat(Food food) {
        if (petState != dyingState) {
            this.starveMeter += food.getStarvePointRecovered();
            if (starveMeter >= maxValueForMeter) {
                starveMeter = maxValueForMeter;
            }

            this.poopMeter += 5;
            if (poopMeter >= maxValueForMeter) {
                poopMeter = maxValueForMeter;
            }

            this.closenessLevel += 5;

            foodReaction();
        } else {
            System.out.println("The pet is dying, unable to eat food");
        }
    }


    //EFFECTS: change pet state to dirty if it poop meter reaches max meter value. change poop meter to 0 afterwards and
    // return true. Else returns false.
    public boolean canPoop() {
        if (poopMeter >= maxValueForMeter) {
            setPetState(dirtyState);
            setPoopMeter(minValueForMeter);
            return true;
        } else {
            return false;
        }
    }


    //EFFECTS: change pet state to normal if it is in dirty state and returns true, else returns false.
    public boolean clean() {
        if (this.getPetState() == dirtyState) {
            setPetState(0);
            return true;
        } else {
            return false;
        }
    }

    //REQUIRES: parameter passed in as int.
    //EFFECTS: increase health level by specific value, if health level is over max value, set it to max value.
    public void increaseHealthBy(int increaseByThisValue) {
        this.healthLevel += increaseByThisValue;
        if (this.healthLevel >= maxValueForMeter) {
            this.healthLevel = maxValueForMeter;
        }
    }

    //REQUIRES: decreaseByThisValue is int.
    //EFFECTS: decrease health level by specific value, if health level is under min value, set it to min value.
    // Goes into dying state when health is zero
    public void decreaseHealthBy(int decreaseByThisValue) {
        this.healthLevel -= decreaseByThisValue;
        if (this.healthLevel <= minValueForMeter) {
            this.healthLevel = minValueForMeter;
            this.petState = dyingState;
        }
    }

    //REQUIRES: parameter passed in as int.
    //EFFECTS: increase starve by specific value, if starve is over max value, set it to max value.
    public void increaseStarveBy(int increaseByThisValue) {
        this.starveMeter += increaseByThisValue;
        if (this.starveMeter >= maxValueForMeter) {
            this.starveMeter = maxValueForMeter;
        }
    }

    //REQUIRES: decreaseByThisValue is int.
    //EFFECTS: decrease starve by specific value, if starve is under min value, set it to min value.

    public void decreaseStarveBy(int decreaseByThisValue) {
        this.starveMeter -= decreaseByThisValue;
        if (this.starveMeter <= minValueForMeter) {
            this.starveMeter = minValueForMeter;
        }
    }


//    public void sleep() {
//        System.out.println("Sleeping");
//        this.isSleep = true;
//    }


//    public void wakeUp() {
//        this.isSleep = false;
//    }

    public abstract void uniqueInteraction();

    public abstract void foodReaction();

    public abstract String getType();


    // EFFECTS: Calls all stat regen method. this acts as a way to simulate condition change overtime. return the state
    // of the pet
    public int animalConditionUpdate() {
        healthRegen();
        starveDeduction();
        canPoop();
        closenessIncreaseOverTime();

        return petState;
    }

    //MODIFIES: this
    //EFFECTS: increase closeness by 5
    private void closenessIncreaseOverTime() {
        closenessLevel += 5;
    }

    //MODIFIES: this
    //EFFECTS: increase health by 5
    public void healthRegen() {
        increaseHealthBy(5);
    }

    //MODIFIES: this
    //EFFECTS: reduce starve by 10, if starve below 50, also reduce health by 10
    public void starveDeduction() {
        decreaseStarveBy(10);
        if (starveMeter <= 50) {
            decreaseHealthBy(10);
        }

    }

}
