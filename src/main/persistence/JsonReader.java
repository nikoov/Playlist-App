package persistence;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    public JSONObject read() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(source)));
        return new JSONObject(content);
    }
}
