package persistance;

import org.json.JSONObject;

public interface Writable {

    //Writable interface made sure all objects needed to be saved has a toJson method to convert such object type to
    // json type object.

    //EFFECTS: return as JSONObject.
    //Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    JSONObject toJson();

}
