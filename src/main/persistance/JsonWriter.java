package persistance;

import model.Player;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class JsonWriter {

    //Json writer class consist of a string of source file directory and a print writer. It writes data to a json file
    // by first converting all necessary objects of player object into json and write it as string to a file using
    // print writer.

    private String destinationFile;
    private PrintWriter printWriter;


    //EFFECTS: Constructor that writes to the destination file.
    //Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public JsonWriter(String destination) {
        this.destinationFile = destination;
    }

    //EFFECTS: Create a print writer that writes to destination. Throws FileNotFoundException if file is not found.
    //Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public void openPrintWriter() throws FileNotFoundException {
        printWriter = new PrintWriter(destinationFile);
    }

    //EFFECTS: Store specific player's profile as a JSON objects/arrays to destination file as string.
    //Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public void write(Player player) {
        JSONObject json = player.toJson();
        saveToFile(json.toString(4));
    }

    //EFFECTS: write the json file to destination file as string.
    //Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public void saveToFile(String jsonObject) {
        printWriter.print(jsonObject);

    }

    //EFFECTS: close print writer.
    //Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public void closePrintWriter() {
        printWriter.close();
    }


}
