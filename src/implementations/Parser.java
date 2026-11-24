package implementations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import exceptions.EmptyQueueException;

/**
 * Reads a text file and checks whether brackets are correctly formatted.
 * 
 * Uses MyStack and MyQueue to match opening and closing brackets, identify
 * incorrectly nested brackets, and analyze any leftover brackets after reaching
 * the end of the file.
 * 
 * Supports parentheses, curly brackets, square brackets and angle brackets.
 * Reports the line numbers containing syntax errors in ascending order.
 */

public class Parser {

	/**
	 * Checks whether an opening bracket matches a closing bracket.
	 * 
	 * @param opening The opening bracket stored as a String.
	 * @param closing The closing bracket being checked.
	 * @return True if the brackets match, otherwise false.
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
	 * Returns the corresponding opening bracket for a closing bracket. Used when
	 * searching the stack for a matching opening bracket.
	 * 
	 * @param closing The closing bracket being checked.
	 * @return The corresponding opening bracket as a String, or an empty String if
	 *         the character is not a supported closing bracket.
	 */

	private static String getOpeningBracket(char closing) {
		if (closing == ')') {
			return "(";
		} else if (closing == '}') {
			return "{";
		} else if (closing == ']') {
			return "[";
		} else if (closing == '>') {
			return "<";
		}

		return "";
	}

	/**
	 * Checks whether a character is a supported opening bracket.
	 * 
	 * @param character The character being checked.
	 * @return True if the character is an opening bracket, otherwise false.
	 */

	private static boolean isOpeningBracket(char character) {
		return character == '(' || character == '{' || character == '[' || character == '<';
	}

	/**
	 * Checks whether a character is a supported closing bracket.
	 * 
	 * @param character The character being checked.
	 * @return True if the character is a closing bracket, otherwise false.
	 */

	private static boolean isClosingBracket(char character) {
		return character == ')' || character == '}' || character == ']' || character == '>';
	}

	/**
	 * Reads a text file and reports lines containing bracket errors.
	 * 
	 * Follows Kitty's Parser Algorithm by matching brackets using a stack and
	 * storing unmatched brackets in queues for further analysis. Once the entire
	 * file has been read, any leftover brackets are analyzed and the incorrect line
	 * numbers are displayed in ascending order.
	 * 
	 * @param args Command-line arguments containing the filename or file path.
	 */

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Please provide a filename.");
			return;
		}

		// Retrieve the filename from the command-line arguments.
		String filename = args[0];

		// Stores the line numbers containing syntax errors
		MyQueue errors = new MyQueue();

		// Stores opening brackets and their corresponding line numbers.
		MyStack stack = new MyStack();
		MyStack lineStack = new MyStack();

		// Stores unmatched brackets for further analysis.
		MyQueue errorQ = new MyQueue();
		MyQueue extrasQ = new MyQueue();

		// Stores the corresponding line numbers of brackets in each queue.
		MyQueue errorLineQ = new MyQueue();
		MyQueue extrasLineQ = new MyQueue();

		// Open the file for reading.
		// The reader is automatically closed when the try block finishes.
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			int lineNumber = 0;

			// Read one line at a time until the end of the file is reached.
			while ((line = reader.readLine()) != null) {
				lineNumber++;

				// Examine each character in the current line.
				for (int i = 0; i < line.length(); i++) {
					char current = line.charAt(i);

					// Only supported opening and closing brackets are processed. All other
					// characters are ignored
					if (isOpeningBracket(current)) {
						// Push the opening bracket onto the stack
						stack.push(String.valueOf(current));

						// Store the line number of the opening bracket.
						// Both stacks must remain in the same order.
						lineStack.push(String.valueOf(lineNumber));

					} else if (isClosingBracket(current)) {

						// Check whether the closing bracket matches the top of the stack.
						// Peek allows us to check without removing the opening bracket.
						if (!stack.isEmpty() && matches(stack.peek(), current)) {
							// Remove the matching bracket and its line number.
							stack.pop();
							lineStack.pop();

						} else if (!errorQ.isEmpty() && matches(errorQ.peek(), current)) {
							// Check whether the closing bracket matches a previously
							// misplaced opening bracket at the front of errorQ.
							errorQ.dequeue();
							errorLineQ.dequeue();

						} else if (stack.isEmpty()) {
							// There are no opening brackets available in the stack.
							// Store the closing bracket and its line number for analysis.
							errorQ.enqueue(String.valueOf(current));
							errorLineQ.enqueue(String.valueOf(lineNumber));

						} else {
							// Search for a matching opening bracket.
							String expected = getOpeningBracket(current);

							// Search deeper into the stack for a matching opening bracket.
							// A return value of -1 means no match was found.
							if (stack.search(expected) != -1) {

								// Move unmatched opening brackets into errorQ
								// until the matching opening bracket is reached.
								while (!matches(stack.peek(), current)) {
									String bracket = stack.pop();
									String bracketLine = lineStack.pop();

									errorQ.enqueue(bracket);
									errorLineQ.enqueue(bracketLine);

									// Report the incorrectly nested bracket.
									errors.enqueue(bracketLine);
								}

								// Remove the matching bracket and its line number.
								stack.pop();
								lineStack.pop();

							} else {
								// No matching opening bracket was found in the stack.
								// Store the closing bracket that has no matching opening bracket.
								extrasQ.enqueue(String.valueOf(current));
								extrasLineQ.enqueue(String.valueOf(lineNumber));
							}
						}
					}
				}
			}

			// Move remaining opening brackets into errorQ after EOF.
			// These brackets have not been matched during file processing.
			while (!stack.isEmpty()) {
				errorQ.enqueue(stack.pop());
				errorLineQ.enqueue(lineStack.pop());
			}

			// Compare the remaining brackets in both queues.
			while (!errorQ.isEmpty() && !extrasQ.isEmpty()) {

				// Check whether the brackets at the front of both queues match.
				if (matches(errorQ.peek(), extrasQ.peek().charAt(0))) {

					// Remove matching brackets and their line numbers.
					errorQ.dequeue();
					errorLineQ.dequeue();

					extrasQ.dequeue();
					extrasLineQ.dequeue();

				} else {

					// The brackets do not match, so remove the bracket from errorQ
					// and retrieve its original line number.
					errorQ.dequeue();

					String errorLine = errorLineQ.dequeue();

					// Only add the line number if it has not already been reported.
					if (!errors.contains(errorLine)) {
						errors.enqueue(errorLine);
					}
				}
			}

			// Report any brackets remaining in errorQ.
			// These brackets could not be matched during leftover analysis.
			while (!errorQ.isEmpty()) {
				errorQ.dequeue();

				String errorLine = errorLineQ.dequeue();

				// Avoid adding duplicate line numbers to the errors queue.
				if (!errors.contains(errorLine)) {
					errors.enqueue(errorLine);
				}
			}

			// Report any brackets remaining in extrasQ.
			while (!extrasQ.isEmpty()) {
				extrasQ.dequeue();

				String errorLine = extrasLineQ.dequeue();

				// Avoid adding duplicate line numbers to the errors queue.
				if (!errors.contains(errorLine)) {
					errors.enqueue(errorLine);
				}
			}

			// Display the results.

			// If the errors queue is empty, no syntax errors were detected.
			if (errors.isEmpty()) {
				System.out.println("File: " + filename + " has no errors.");
			} else {
				System.out.println("Errors in file " + filename + ":");

				// Check each line number in ascending order.
				// This ensures errors are displayed in the order they appear
				// in the file, without displaying the same line more than once.
				for (int i = 1; i <= lineNumber; i++) {
					if (errors.contains(String.valueOf(i))) {
						System.out.println("Line " + i + ": Syntax error");
					}
				}
			}

		} catch (IOException e) {
			// Handle errors that occur when opening or reading the file.
			System.out.println("Error reading file: " + filename);

		} catch (EmptyQueueException e) {
			// Handle errors caused by attempting to access an empty queue.
			System.out.println("Error processing bracket queue.");
		}
	}
}