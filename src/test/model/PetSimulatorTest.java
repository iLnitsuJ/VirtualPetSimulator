package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PetSimulatorTest {
    Player justin;
    Stopwatch testWatch;


    @BeforeEach
        void runBefore(){
        justin = new Player("Justin");
        justin.addPet(new Pet("Cattie","Cat"));
        justin.addPet(new Pet("Doggie","Dog"));
        testWatch = new Stopwatch();
        justin.getInventory().add(new Food("apple", 10));
        justin.getInventory().add(new Food("banana", 20));
        justin.getInventory().add(new Food("orange", 30));

    }
    @Test

    void testParseInt(){
        assertEquals(7,Integer.parseInt("7"));
        assertNotEquals(7,Integer.parseInt("8"));
    }

    //region Action Tests
    @Test
    void testPerformsAction(){
        justin.getPetByNumber(0).getAnimal().getAction().performsAction(0);
        assertEquals(0,justin.getPetByNumber(0).getAnimal().getAction().getCurrentAction());

        justin.getPetByNumber(0).getAnimal().getAction().performsAction(1);
        assertEquals(1,justin.getPetByNumber(0).getAnimal().getAction().getCurrentAction());

        justin.getPetByNumber(0).getAnimal().getAction().performsAction(2);
        assertEquals(2,justin.getPetByNumber(0).getAnimal().getAction().getCurrentAction());

        justin.getPetByNumber(0).getAnimal().getAction().performsAction(3);
        assertEquals(3,justin.getPetByNumber(0).getAnimal().getAction().getCurrentAction());

        justin.getPetByNumber(0).getAnimal().getAction().performsAction(100);
        assertEquals(-1,justin.getPetByNumber(0).getAnimal().getAction().getCurrentAction());
    }

    @Test

    void testRandomNumber(){

        assertNotEquals(-1,Action.randomNumber(0,3));
        assertNotEquals(4,Action.randomNumber(0,3));

    }
    //endregion

    //region Animal Tests


    @Test
    void testEqualsAndHash() {
        justin.addPet(new Pet("1","Cat"));
        justin.addPet(new Pet("1","Cat"));

        assertNotEquals(justin.getPetByNumber(0).getAnimal(), justin.getPetByNumber(1).getAnimal());
        assertEquals(justin.getPetByNumber(2).getAnimal(), justin.getPetByNumber(3).getAnimal());
        assertEquals(justin.getPetByNumber(2).getAnimal(), justin.getPetByNumber(2).getAnimal());
        assertNotEquals(justin.getPetByNumber(2).getAnimal(), justin.getPetByNumber(3));
        assertNotEquals(justin.getPetByNumber(2).getAnimal(), null);

        assertEquals(justin.getPetByNumber(2).getAnimal().hashCode(),justin.getPetByNumber(2).getAnimal().hashCode());

    }

    @Test
    void testCanPoop(){
        justin.getPets().get(0).getAnimal().setPoopMeter(100);
        assertTrue(justin.getPets().get(0).getAnimal().canPoop());
        justin.getPets().get(0).getAnimal().setPoopMeter(80);
        assertFalse(justin.getPets().get(0).getAnimal().canPoop());
    }

    @Test
    void testClean(){
        assertFalse(justin.getPets().get(0).getAnimal().clean());
        justin.getPets().get(0).getAnimal().setPetState(5);
        assertTrue(justin.getPets().get(0).getAnimal().clean());
    }

    @Test
    void testIncreaseHealthBy(){
        justin.getPets().get(0).getAnimal().setHealthLevel(50);
        justin.getPets().get(0).getAnimal().increaseHealthBy(10);
        assertEquals(60,justin.getPets().get(0).getAnimal().getHealthLevel());
        justin.getPets().get(0).getAnimal().increaseHealthBy(70);
        assertEquals(100,justin.getPets().get(0).getAnimal().getHealthLevel());
    }

    @Test
    void testDecreaseHealthBy(){
        justin.getPets().get(0).getAnimal().setHealthLevel(50);
        justin.getPets().get(0).getAnimal().decreaseHealthBy(10);
        assertEquals(40,justin.getPets().get(0).getAnimal().getHealthLevel());
        justin.getPets().get(0).getAnimal().decreaseHealthBy(70);
        assertEquals(0,justin.getPets().get(0).getAnimal().getHealthLevel());
    }

    @Test
    void testIncreaseDecreaseStarveBy(){
        justin.getPets().get(0).getAnimal().setStarveMeter(50);
        justin.getPets().get(0).getAnimal().increaseStarveBy(10);
        assertEquals(60,justin.getPets().get(0).getAnimal().getStarveMeter());
        justin.getPets().get(0).getAnimal().increaseStarveBy(100);
        assertEquals(100,justin.getPets().get(0).getAnimal().getStarveMeter());
        justin.getPets().get(0).getAnimal().decreaseStarveBy(70);
        assertEquals(30,justin.getPets().get(0).getAnimal().getStarveMeter());
        justin.getPets().get(0).getAnimal().decreaseStarveBy(700);
        assertEquals(0,justin.getPets().get(0).getAnimal().getStarveMeter());
    }

    @Test
    void testAnimalConditionUpdate(){
        justin.getPets().get(0).getAnimal().animalConditionUpdate();
        justin.getPets().get(0).getAnimal().setStarveMeter(30);
        justin.getPets().get(0).getAnimal().animalConditionUpdate();
        assertEquals(20,justin.getPets().get(0).getAnimal().getStarveMeter());
        assertEquals(90,justin.getPets().get(0).getAnimal().getHealthLevel());
    }




    @Test
    void testGetClosenessLevel(){
       assertEquals(0,justin.getPetByNumber(0).getAnimal().getClosenessLevel());
    }

    @Test
    void testSetClosenessLevel(){
        justin.getPetByNumber(0).getAnimal().setClosenessLevel(1000);
        assertEquals(1000,justin.getPetByNumber(0).getAnimal().getClosenessLevel());
    }

    @Test
    void testGetPoopMeter(){
        assertEquals(0,justin.getPetByNumber(0).getAnimal().getPoopMeter());
    }

    @Test
    void testSetPoopMeter(){
        justin.getPetByNumber(0).getAnimal().setPoopMeter(50);
        assertEquals(50,justin.getPetByNumber(0).getAnimal().getPoopMeter());
    }

    @Test
    void testGetAnimalName(){
        assertEquals("Cattie",justin.getPetByNumber(0).getAnimal().getAnimalName());
    }

    @Test
    void testSetAnimalName(){
        justin.getPetByNumber(0).getAnimal().setAnimalName("123");
        assertEquals("123",justin.getPetByNumber(0).getAnimal().getAnimalName());
    }

    @Test
    void testEat(){

        Food food = new Food("Strawberry", 10);
        justin.getPetByNumber(0).getAnimal().eat(food);
        assertEquals(100,justin.getPetByNumber(0).getAnimal().getStarveMeter());
        justin.getPetByNumber(1).getAnimal().setPoopMeter(99);
        justin.getPetByNumber(1).getAnimal().eat(food);
        assertEquals(100, justin.getPetByNumber(1).getAnimal().getPoopMeter());
        justin.getPetByNumber(1).getAnimal().setPetState(4);
        justin.getPetByNumber(1).getAnimal().eat(food);
    }

    @Test
    void testFoodReaction(){

        Food food = new Food("Strawberry", 10);

    }

    //endregion

    //region Food Tests
    @Test
    void testSetAmountStarveRecovered(){
        Food temp = new Food("temp", 20);    //creates temp food that recovers 20 hp.
        temp.setStarvePointRecovered(10);    //sets temp food recover amount to 10


        //verify that after eating temp food , hp goes to 110

        justin.getPets().get(0).getAnimal().setStarveMeter(70);
        justin.getPets().get(0).getAnimal().eat(temp);
        assertEquals(80, justin.getPetByNumber(0).getAnimal().getStarveMeter());
    }

    @Test

    void testSetName(){
        Food temp = new Food("temp", 20);
        temp.setName("123");
        assertEquals("123", temp.getName());

    }
    //endregion

    //region player Tests

    @Test
    void testGetScore(){
        assertEquals(0,justin.getScore());
    }

    @Test
    void testReleasePet(){
        Pet tempPet = justin.getPetByNumber(0);
        justin.releasePet(justin.getPetByNumber(0));
        assertNotEquals(tempPet,justin.getPetByNumber(0));
    }

    @Test
    void testAddFoodToInventory(){
        Food tempFood = new Food("temp",10);
        justin.addFoodToInventory(tempFood);
        assertEquals("temp",justin.getFoodFromInventory("temp").getName());
    }

    @Test
    void testRemoveFoodToInventory(){
        justin.removeFoodFromInventory(justin.getFoodFromInventory("apple"));
        assertFalse(justin.isFoodInInventory("apple"));
    }

    @Test
    void testUseFood(){
        Food tempFood = new Food("temp",10);
        justin.getPets().get(0).getAnimal().setStarveMeter(90);
        justin.useFood(tempFood,0);
        assertEquals(100,justin.getPets().get(0).getAnimal().getStarveMeter());
    }

    @Test
    void testIsFoodInInventory(){
        assertTrue(justin.isFoodInInventory("apple"));
        assertFalse(justin.isFoodInInventory("123"));

    }

    @Test
    void testGetInventory(){
        ArrayList<Food> sample = new ArrayList<>();
        sample.add(new Food("apple", 10));
        sample.add(new Food("banana", 20));
        sample.add(new Food("orange", 30));
        assertEquals(sample.get(0).getName(),justin.getInventory().get(0).getName());
        assertEquals(sample.get(1).getName(),justin.getInventory().get(1).getName());
        assertEquals(sample.get(2).getName(),justin.getInventory().get(2).getName());
    }

    @Test
    void testGetFoodIndexFromInventory(){
        assertEquals(-1,justin.getFoodIndexFromInventory("123"));
        assertEquals(0,justin.getFoodIndexFromInventory("apple"));
    }

    @Test
    void testGetPlayerName(){
        assertEquals("Justin",justin.getPlayerName());
    }

    @Test
    void testSetPlayerName(){
        justin.setPlayerName("hi");
        assertEquals("hi",justin.getPlayerName());
    }

    //endregion

    //region Stopwatch Tests

    @Test
    void testElapsedTime(){

        testWatch.startTimer();
        while ((System.currentTimeMillis() - testWatch.getStartTime()) < 2000){
            //timer for 2 secs
        }
        testWatch.endTimer();
        assertEquals(2000,testWatch.elapsedTime());
    }


    //testing both start and end timer
    @Test
    void testTimer(){
        testWatch.startTimer();
        while ((System.currentTimeMillis() - testWatch.getStartTime()) < 2000) {
            //timer for 2 secs
        }
        testWatch.endTimer();
        assertEquals(2000,testWatch.getEndTime() - testWatch.getStartTime());

    }

    @Test
    void testIsTick(){
        testWatch.startTimer();
        while ((System.currentTimeMillis() - testWatch.getStartTime()) < 1000) {
            //timer for 1 secs
        }
        assertTrue(testWatch.isTick(1));

        while ((System.currentTimeMillis() - testWatch.getStartTime()) < 2000) {
            //timer for 2 secs
        }
        assertFalse(testWatch.isTick(3));
    }

    //endregion

    //region Pet Tests
    @Test
    void testPetDefaultType(){
        Pet test = new Pet("petName", "123");
        assertEquals("Dog", test.getAnimal().getType());

        Pet test1 = new Pet("petName", "Dog");
        assertEquals("Dog", test1.getAnimal().getType());

        Pet test2 = new Pet("petName", "Cat");
        assertEquals("Cat", test2.getAnimal().getType());

    }
    //endregion














}