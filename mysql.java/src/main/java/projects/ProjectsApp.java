package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

public class ProjectsApp {
	// Instance variables:

	// @formatter:off
	private List<String> operations = List.of(
			"1) Add a project",
			"2) List projects"
			);
	// @formatter:on

	private Scanner scanner = new Scanner(System.in);

	private ProjectService projectService = new ProjectService();

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
				case 1:
					createProject();
					break;
				case 2:
					listProjects();
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

	private void printOperations() {
		System.out.println("\nThese are the available selections. Press the Enter key to quit:");

		operations.forEach(line -> System.out.println("   " + line));
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

	private String getStringInput(String prompt) {
		System.out.print(prompt + ": ");

		String input = scanner.nextLine();

		// Check if input is blank, else return trimmed input:
		return input.isBlank() ? null : input.trim();
	}

	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);

		// Check if input is null:
		if (Objects.isNull(input)) {
			return null;
		}

		try {
			// Create new BigDecimal object and set number of decimal places (scale) to 2:
			return new BigDecimal(input).setScale(2);
		} catch (NumberFormatException e) {
			throw new DbException(input + " is not a valid decimal number. Try again.");
		}
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
		} catch (NumberFormatException e) {
			throw new DbException(input + " is not a valid number. Try again.");
		}
	}

	private void createProject() {
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
		// TODO: code to validate difficulty input
		String notes = getStringInput("Enter the project notes");

		Project project = new Project();

		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);

		Project dbProject = projectService.addProject(project);

		System.out.println("You have successfully created project: " + dbProject);
	}

	private void listProjects() {
		List<Project> projects = projectService.fetchAllProjects();
		System.out.println("\nProjects:");

		projects.forEach(
				project -> System.out.println("    " + project.getProjectId() + ": " + project.getProjectName()));
	}

}
