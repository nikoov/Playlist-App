package persistence;

import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonWriter {
    private PrintWriter writer;
    private String destination;

    public JsonWriter(String destination) {
        this.destination = destination;
    }

    public void open() throws IOException {
        writer = new PrintWriter(new FileWriter(destination));
    }

    public void write(JSONObject json) {
        writer.print(json.toString());
    }

    public void close() {
        writer.close();
    }
}
