package model;

import org.junit.jupiter.api.Test;
import persistance.JsonReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {

    private static final String JSON_Storage_NormalPlayerTest = "./data/testReadingNormalPlayer.json";
    private static final String JSON_Storage_InvalidName = "./data/123NonExistentFile.json";
    private static final String JSON_Storage_EmptyPlayerTest = "./data/testReadingEmptyPlayer.json";
    private JsonReader jsonReader;

//TODO later
    @Test
    void testReadingNonExistentFile(){

        try {
            jsonReader = new JsonReader(JSON_Storage_InvalidName);
            jsonReader.read();
            fail("Should not reach here");
        } catch (IOException e) {
            //expected exception
        }


    }

    @Test
    void testReadingEmptyPlayer(){
        jsonReader = new JsonReader(JSON_Storage_EmptyPlayerTest);
        try {
            Player savedPlayerProfile = jsonReader.read();
            assertEquals("empty",savedPlayerProfile.getPlayerName());
            assertEquals(0,savedPlayerProfile.pets.size());
            assertEquals("apple", savedPlayerProfile.getInventory().get(0).getName());
            assertEquals("10", Integer.toString(savedPlayerProfile.getInventory().get(0).getStarvePointRecovered()));
            assertEquals("banana", savedPlayerProfile.getInventory().get(1).getName());
            assertEquals("20", Integer.toString(savedPlayerProfile.getInventory().get(1).getStarvePointRecovered()));
            assertEquals("orange", savedPlayerProfile.getInventory().get(2).getName());
            assertEquals("30", Integer.toString(savedPlayerProfile.getInventory().get(2).getStarvePointRecovered()));
        } catch (IOException e) {
            fail("Unable to find game progress from: " + JSON_Storage_EmptyPlayerTest);
        }


    }

    @Test
    void testReadingNormalPlayer(){
        jsonReader = new JsonReader(JSON_Storage_NormalPlayerTest);

        try {
            Player savedPlayerProfile = jsonReader.read();
            assertEquals("normal",savedPlayerProfile.getPlayerName());
            assertEquals(2,savedPlayerProfile.pets.size());
            assertEquals("pet1",savedPlayerProfile.getPetByNumber(0).getAnimal().getAnimalName());
            assertEquals(100,savedPlayerProfile.getPetByNumber(0).getAnimal().getHealthLevel());
            assertEquals(100,savedPlayerProfile.getPetByNumber(0).getAnimal().getClosenessLevel());
            assertEquals(0,savedPlayerProfile.getPetByNumber(0).getAnimal().getPoopMeter());
            assertEquals("Cat",savedPlayerProfile.getPetByNumber(0).getAnimal().getType());

            assertEquals("apple", savedPlayerProfile.getInventory().get(0).getName());
            assertEquals("10", Integer.toString(savedPlayerProfile.getInventory().get(0).getStarvePointRecovered()));
            assertEquals("banana", savedPlayerProfile.getInventory().get(1).getName());
            assertEquals("20", Integer.toString(savedPlayerProfile.getInventory().get(1).getStarvePointRecovered()));
            assertEquals("orange", savedPlayerProfile.getInventory().get(2).getName());
            assertEquals("30", Integer.toString(savedPlayerProfile.getInventory().get(2).getStarvePointRecovered()));

            assertEquals("pet2",savedPlayerProfile.getPetByNumber(1).getAnimal().getAnimalName());

        } catch (IOException e) {
            fail("Unable to find game progress from: " + JSON_Storage_NormalPlayerTest);
        }

    }




}
