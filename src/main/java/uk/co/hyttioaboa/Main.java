package uk.co.hyttioaboa;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("Hello world.");
        System.out.println("I say hello too!");
        //String definition = "<node>value</node>";
        String definition = "{\"pages\":[{\"name\":\"1\"},{\"name\":\"2\"}]}";

        TestDefinition test = new TestDefinition(definition);
    }
}
