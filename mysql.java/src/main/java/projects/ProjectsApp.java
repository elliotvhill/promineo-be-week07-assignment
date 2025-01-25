package projects;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.exception.DbException;

public class ProjectsApp {
	// Instance variables:

	// @formatter:off
	private List<String> operations = List.of(
			"1) Add a project"
			);
	// @formatter:on

	private Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		new ProjectsApp().processUserSelections();

	}

	private void processUserSelections() {
		boolean done = false;

		while (!done) {
			try {
				int selection = getUserSelection();
				
				switch (selection) {
					case -1:
						done = exitMenu();
						break;
					default:
						System.out.println("\n" + selection + " is not a valid selection. Try again.");
						break;
				}
					
				
			} catch (Exception e) {
				System.out.println("\nError: " + e + " Try again.");
			}
		}
	}

	private boolean exitMenu() {
		System.out.println("Exiting the menu.");
		return true;
	}

	private int getUserSelection() {
		printOperations();
		Integer input = getIntInput("Enter a menu selection");

		// Check for null value and return:
		return Objects.isNull(input) ? -1 : input;
	}

	private void printOperations() {
		System.out.println("\nThese are the available selections. Press the Enter key to quit:");

		operations.forEach(line -> System.out.println("   " + line));
	}

	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);

		// Check if input is null:
		if (Objects.isNull(input)) {
			return null;
		}
		
		try {
			// Convert string input to int:
			return Integer.valueOf(input);
		} 
		catch (NumberFormatException e) {
			throw new DbException(input + " is not a valid number. Try again.");
		}
	}

	private String getStringInput(String prompt) {
		System.out.print(prompt + ": ");
		
		String input = scanner.nextLine();
		
		// Check if input is blank, else return trimmed input:
		return input.isBlank() ? null : input.trim();
	}

}

























