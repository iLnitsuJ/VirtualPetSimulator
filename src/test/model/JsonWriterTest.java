package model;

import org.junit.jupiter.api.Test;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {
    private static final String JSON_Storage_NormalPlayerTest = "./data/testNormalPlayerProfileSave.json";
    private static final String JSON_Storage_InvalidName = "./data/\0\testIllegalFileName&2.json";
    private static final String JSON_Storage_EmptyPlayerTest = "./data/testEmptyPlayerProfileSave.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private Player normalPlayer;


    //region JsonWriter tests

    //Test that an exception is thrown when given an invalid file name for jsonWriter.
    //Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Test
    void testWriteToIllegalNameFile(){

        try {

            jsonWriter = new JsonWriter( JSON_Storage_InvalidName);
            jsonWriter.openPrintWriter();
            fail("exception was not thrown");
            jsonWriter.write(new Player("123"));


        }
        catch (IOException e) {
            System.out.println("Expected exception due to invalid name of given destination directory");
        }
    }

    //Test writing an empty player profile to destination.
    //Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Test
    void testEmptyPlayerProfileSave(){
        jsonReader = new JsonReader(JSON_Storage_EmptyPlayerTest);
        jsonWriter = new JsonWriter(JSON_Storage_EmptyPlayerTest);

        normalPlayer = new Player("emptyPlayer");


        try {
            jsonWriter.openPrintWriter();
            jsonWriter.write(normalPlayer);
            jsonWriter.closePrintWriter();
        } catch (FileNotFoundException e) {
            fail("Unable to save game progress to:" + JSON_Storage_EmptyPlayerTest);
        }
        try {
            Player savedPlayerProfile = jsonReader.read();
            assertEquals("emptyPlayer",savedPlayerProfile.getPlayerName());
            assertEquals(0,savedPlayerProfile.pets.size());

        } catch (IOException e) {
            fail("Unable to find game progress from: " + JSON_Storage_EmptyPlayerTest);
        }


    }


    //Test writing a normal player profile to destination .
    //Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Test
    void testNormalPlayerProfileSave(){
        jsonReader = new JsonReader(JSON_Storage_NormalPlayerTest);
        jsonWriter = new JsonWriter(JSON_Storage_NormalPlayerTest);

        normalPlayer = new Player("robot");
        normalPlayer.addPet(new Pet("Cat1","Cat"));
        normalPlayer.addPet(new Pet("Dog1","Dog"));
        normalPlayer.getInventory().add(new Food("apple", 10));
        normalPlayer.getInventory().add(new Food("banana", 20));
        normalPlayer.getInventory().add(new Food("orange", 30));


        try {
            jsonWriter.openPrintWriter();
            jsonWriter.write(normalPlayer);
            jsonWriter.closePrintWriter();
        } catch (FileNotFoundException e) {
            fail("Unable to save game progress to:" + JSON_Storage_NormalPlayerTest);
        }
        try {
            Player savedPlayerProfile = jsonReader.read();
            assertEquals("robot",savedPlayerProfile.getPlayerName());
            assertEquals(2,savedPlayerProfile.pets.size());
            assertEquals("Cat1",savedPlayerProfile.getPetByNumber(0).getAnimal().getAnimalName());
            assertEquals("Dog1",savedPlayerProfile.getPetByNumber(1).getAnimal().getAnimalName());

        } catch (IOException e) {
            fail("Unable to find game progress from: " + JSON_Storage_NormalPlayerTest);
        }

    }

    //endregion
}
