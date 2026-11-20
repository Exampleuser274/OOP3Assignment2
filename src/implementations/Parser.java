package implementations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import exceptions.EmptyQueueException;

/**
 * Reads a text file and checks whether brackets are correctly formatted.
 */
public class Parser {

    /**
     * Checks whether two brackets match.
     */
    private static boolean matches(String opening, char closing) {
        if (opening.equals("(") && closing == ')') {
            return true;
        } else if (opening.equals("{") && closing == '}') {
            return true;
        } else if (opening.equals("[") && closing == ']') {
            return true;
        } else if (opening.equals("<") && closing == '>') {
            return true;
        }

        return false;
    }

    /**
     * Checks whether a character is an opening bracket.
     */
    private static boolean isOpeningBracket(char character) {
    	return character == '(' || character == '{'
                || character == '[' || character == '<';
    }

    /**
     * Checks whether a character is a closing bracket.
     */
    private static boolean isClosingBracket(char character) {
        return character == ')' || character == '}'
                || character == ']' || character == '>';
    }

    /**
     * Reads a file and reports lines containing bracket errors.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide a filename.");
            return;
        }

        String filename = args[0];

        MyQueue errors = new MyQueue();

        try (BufferedReader reader =
                new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                MyStack stack = new MyStack();
                boolean error = false;

                for (int i = 0; i < line.length(); i++) {
                    char current = line.charAt(i);

                    if (isOpeningBracket(current)) {
                        stack.push(String.valueOf(current));

                    } else if (isClosingBracket(current)) {
                        if (stack.isEmpty()) {
                            error = true;
                        }
                        else {
                            String opening = stack.pop();

                            if (!matches(opening, current)) {
                                error = true;
                            }
                        }
                    }
                }

                // Check for unmatched opening brackets.
                if (!stack.isEmpty()) {
                    error = true;
                }

                // Add incorrect line numbers to the queue.
                if (error) {
                    errors.enqueue(String.valueOf(lineNumber));
                }
            }

            // Display the results.
            if (errors.isEmpty()) {
                System.out.println("File: " + filename
                        + " has no errors.");
            } else {
                System.out.println("Errors in file " + filename + ":");

                while (!errors.isEmpty()) {
                    try {
                        String errorLine = errors.dequeue();

                        System.out.println("Line " + errorLine
                                + ": Syntax error");
                    } catch (EmptyQueueException e) {
                        System.out.println("Error retrieving line number.");
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + filename);

        }
    }
}