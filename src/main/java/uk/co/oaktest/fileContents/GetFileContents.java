package uk.co.oaktest.fileContents;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.oaktest.messages.jackson.TestMessage;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GetFileContents {

    public GetFileContents() {

    }

    public String getTestMessage(String messagePath) {
        String message;
        try {
            message = readFile(messagePath, Charset.defaultCharset());
        }
        catch (IOException ioEx) {
            throw new Error(ioEx);
        }

        return message;
    }

    public String readFile(String path) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, Charset.defaultCharset());
        }
        catch (Exception e) {
            throw new Error(e);
        }
    }

    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public TestMessage getMessageFromFile(String filePath) {
        String jsonDefinition = getTestMessage(filePath);

        ObjectMapper mapper = new ObjectMapper();
        TestMessage testMessage;
        try {
            testMessage = mapper.readValue(jsonDefinition, TestMessage.class);
        }
        catch (Exception jsonException) {
            System.out.println(jsonException.getMessage());
            return null;
        }

        return testMessage;
    }
}
