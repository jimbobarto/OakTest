package json;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GetJson {

    public GetJson() {

    }

    public String getTestMessage() {
        String message;
        try {
            message = readFile("src/test/resources/testMessage.json", Charset.defaultCharset());
        }
        catch (IOException ioEx) {
            throw new Error(ioEx);
        }

        return message;
    }

    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
