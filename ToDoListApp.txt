

import java.util.ArrayList;
import java.util.Scanner;

public class ToDoListApp {
    private static ArrayList<String> tasks = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        System.out.println("Welcome to the To-Do List Application!");

        do {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume leftover newline

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    displayTasks();
                    break;
                case 3:
                    removeTask();
                    break;
                case 4:
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 4);
    }

    private static void displayMenu() {
        System.out.println("\n===== To-Do List Menu =====");
        System.out.println("1. Add a new task");
        System.out.println("2. Display all tasks");
        System.out.println("3. Remove a task");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addTask() {
        System.out.print("Enter the task to add: ");
        String task = scanner.nextLine();
        tasks.add(task);
        System.out.println("Task added successfully.");
    }

    private static void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks in the list.");
        } else {
            System.out.println("\nCurrent Tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    private static void removeTask() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to remove.");
            return;
        }

        System.out.println("Remove by:");
        System.out.println("1. Index");
        System.out.println("2. Name");
        System.out.print("Enter your choice: ");
        int removeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume leftover newline

        switch (removeChoice) {
            case 1:
                System.out.print("Enter the task number to remove: ");
                int index = scanner.nextInt() - 1;
                scanner.nextLine(); // Consume leftover newline
                if (index >= 0 && index < tasks.size()) {
                    String removedTask = tasks.remove(index);
                    System.out.println("Removed task: " + removedTask);
                } else {
                    System.out.println("Invalid task number.");
                }
                break;

            case 2:
                System.out.print("Enter the task name to remove: ");
                String name = scanner.nextLine();
                if (tasks.remove(name)) {
                    System.out.println("Removed task: " + name);
                } else {
                    System.out.println("Task not found.");
                }
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }
}