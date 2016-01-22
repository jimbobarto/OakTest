package variables;

import org.junit.Test;
import uk.co.oaktest.variables.Translator;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by jamesbartlett on 20/12/15.
 */
public class Translate {

    @Test
    public void translateSimpleVariable() {
        Map<String, String> variables = new HashMap<String, String>();
        variables.put("variable", "new value");
        variables.put("variablePath", "{\"name\": \"found it\"}");
        String testString = "${variable}";
        String testPath = "${variablePath.name}";

        Translator translator = new Translator(variables);
        String translatedValue = translator.translate(testString);
        System.out.println("Variable: " + translatedValue);
        assertEquals("Simple variable should evaluate correctly", "new value", translatedValue);

        translatedValue = translator.translate(testPath);
        System.out.println("Variable path: " + translatedValue);
        assertEquals("Variable path should evaluate correctly", "found it", translatedValue);
    }
}
