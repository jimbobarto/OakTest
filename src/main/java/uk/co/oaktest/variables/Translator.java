package uk.co.oaktest.variables;

import com.jayway.jsonpath.JsonPath;
import uk.co.oaktest.messages.jackson.Variable;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jamesbartlett on 19/12/15.
 */
public class Translator {

    ArrayList<Variable> existingVariables;

    public Translator() {
    }

    public Translator(ArrayList<Variable> variables) {
        this.existingVariables = variables;
    }

    public void initialiseVariables(ArrayList<Variable> variables) throws Exception{
        if (this.existingVariables != null) {
            throw new Exception("Cannot initialise variables when they already exist");
        }
        this.existingVariables = variables;
    }

    public ArrayList<Variable> addVariable(String variableKey, String variableType, String variableValue) {
        this.existingVariables.add(new Variable(variableKey, variableType, variableValue));
        return this.existingVariables;
    }

    public boolean containsVariable(String testString) {
        Pattern pattern = Pattern.compile("\\$\\{.+\\}");
        Matcher matcher = pattern.matcher(testString);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    public String translate(String stringContainingVariable) {
        Pattern pattern = Pattern.compile("\\$\\{(.+)\\}");
        Matcher matcher = pattern.matcher(stringContainingVariable);
        while (matcher.find()) {
            String currentVariable = matcher.group(1);

            Pattern variableNamePattern = Pattern.compile("^\\w+$");
            Matcher variableMatcher = variableNamePattern.matcher(currentVariable);
            if (variableMatcher.find()) {
                // the variable is simply the name of a variable in turn, so look for it in the list
                stringContainingVariable = stringContainingVariable.replaceFirst("\\$\\{" + currentVariable + "\\}", getVariable(currentVariable));
            }
            else {
                Pattern variablePathPattern = Pattern.compile("^(\\w+)\\.(.+)$");
                Matcher variablePathMatcher = variablePathPattern.matcher(currentVariable);
                if (variablePathMatcher.find()) {
                    // the variable is a path! First things first, get the variable
                    String pathName = variablePathMatcher.group(1);
                    String path = variablePathMatcher.group(2);

                    String pathString = getVariable(pathName);
                    String evaluatedVariable = JsonPath.read(pathString, "$." + path);
                    stringContainingVariable = stringContainingVariable.replaceFirst("\\$\\{" + currentVariable + "\\}", evaluatedVariable);
                }

            }
        }

        return stringContainingVariable;
    }

    private String getVariable(String variableName) {
        for (Variable variable: this.existingVariables) {
            if (variable.getName().equals(variableName)) {
                return variable.getValue();
            }
        }

        return "";
    }
}
