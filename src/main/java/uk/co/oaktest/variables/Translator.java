package uk.co.oaktest.variables;

import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.StringUtils;
import uk.co.oaktest.messages.jackson.Variable;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translator {

    //TODO: make this XML aware

    ArrayList<Variable> existingVariables;

    public Translator() {
        this.existingVariables = new ArrayList<>();
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

    public boolean isPath(String testString) {
        Pattern pathPattern = Pattern.compile("^(\\w+)\\.(.+)$");
        Matcher pathMatcher = pathPattern.matcher(testString);
        if (pathMatcher.find()) {
            return true;
        }
        return false;
    }

    public String translate(String stringContainingVariable) {
        return translate(stringContainingVariable, false);
    }

    public String translate(String stringContainingVariable, Boolean forceEvaluation) {
        String parsedString = stringContainingVariable;

        if (!StringUtils.isEmpty(stringContainingVariable)) {
            Pattern pattern = Pattern.compile("\\$\\{(.+)\\}");
            Matcher matcher = pattern.matcher(stringContainingVariable);

            Boolean matchesFound = false;
            while (matcher.find()) {
                matchesFound = true;
                String currentVariable = matcher.group(1);

                Pattern variableNamePattern = Pattern.compile("^[\\w\\-]+$");
                Matcher variableMatcher = variableNamePattern.matcher(currentVariable);
                String evaluatedVariable;
                if (variableMatcher.find()) {
                    // the variable is simply the name of a variable in turn, so look for it in the list
                    evaluatedVariable = getVariable(currentVariable);
                } else {
                    evaluatedVariable = evaluatePath(currentVariable);
                }
                if (!evaluatedVariable.equals("")) {
                    parsedString = stringContainingVariable.replaceFirst("\\$\\{" + currentVariable + "\\}", getVariable(currentVariable));
                }
            }

            //In theory (!) all of the variables have now been replaced; now check if the translated string is in itself a path
            String evaluatedEntirePath = evaluatePath(parsedString);
            if ( (matchesFound || forceEvaluation) && !evaluatedEntirePath.equals("")) {
                parsedString = evaluatedEntirePath;
            }
        }

        return parsedString;
    }

    private String evaluatePath(String possiblePath) {
        String evaluatedPath = "";

        Pattern pathPattern = Pattern.compile("^(\\w+)\\.(.+)$");
        Matcher pathMatcher = pathPattern.matcher(possiblePath);
        if (pathMatcher.find()) {
            // the string is a path! First things first, get the variable
            String pathName = pathMatcher.group(1);
            String path = pathMatcher.group(2);

            String pathString = getVariable(pathName);
            if (!pathString.equals("")) {
                evaluatedPath = JsonPath.read(pathString, "$." + path).toString();
            }
        }

        return evaluatedPath;
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
