package ru.ranepa.presentation;

import ru.ranepa.model.Employee;
import ru.ranepa.service.HRMService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final HRMService service;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public Menu(HRMService service) {
        this.service = service;
    }

    public void start() {
        while (true) {
            printMainMenu();
            int choice = readIntInput("Choose option: ");

            switch (choice) {
                case 1 -> showAllEmployees();
                case 2 -> addEmployee();
                case 3 -> deleteEmployee();
                case 4 -> findEmployeeById();
                case 5 -> showStatistics();
                case 6 -> filterByPosition();
                case 7 -> showEmployeesSortedByDate();
                case 8 -> saveToFile();
                case 0 -> {
                    saveOnExit();
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid input. Please choose option from 0 to 8.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\nSystem Menu");
        System.out.println("1. Show all employees");
        System.out.println("2. Add employee");
        System.out.println("3. Delete employee");
        System.out.println("4. Find employee by ID");
        System.out.println("5. Show statistics");
        System.out.println("6. Filter employees by position");
        System.out.println("7. Show employees sorted by hire date");
        System.out.println("8. Save to CSV file");
        System.out.println("0. Exit");
    }

    private void showAllEmployees() {
        List<Employee> employees = service.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("Employee list is empty.");
            return;
        }

        System.out.printf("%-5s %-22s %-20s %-12s %-15s%n",
                "ID", "Name", "Position", "Salary", "Hire Date");

        for (Employee e : employees) {
            System.out.printf("%-5d %-22s %-20s %-12.0f %-15s%n",
                    e.getId(),
                    truncateString(e.getName(), 22),
                    truncateString(e.getPosition(), 20),
                    e.getSalary(),
                    e.getHireDate().format(dateFormatter));
        }
        System.out.println("Total employees: " + employees.size());
    }

    private void showEmployeesSortedByDate() {
        List<Employee> employees = service.getAllEmployeesSortedByHireDate();
        if (employees.isEmpty()) {
            System.out.println("Employee list is empty.");
            return;
        }

        System.out.println("\nEmployees sorted by hire date (oldest first)");
        System.out.printf("%-5s %-22s %-20s %-12s %-15s%n",
                "ID", "Name", "Position", "Salary", "Hire Date");

        for (Employee e : employees) {
            System.out.printf("%-5d %-22s %-20s %-12.0f %-15s%n",
                    e.getId(),
                    truncateString(e.getName(), 22),
                    truncateString(e.getPosition(), 20),
                    e.getSalary(),
                    e.getHireDate().format(dateFormatter));
        }
        System.out.println("Total employees: " + employees.size());
    }

    private void addEmployee() {
        System.out.println("\nAdd New Employee");

        String name = readStringInput("Enter name: ");
        String position = readStringInput("Enter position: ");
        double salary = readDoubleInput("Enter salary: ");
        LocalDate hireDate = readDateInput("Enter hire date (dd.MM.yyyy): ");

        Employee employee = new Employee(null, name, position, BigDecimal.valueOf(salary), hireDate);
        Employee saved = service.addEmployee(employee);

        System.out.printf("Employee successfully added with ID: %d%n", saved.getId());
    }

    private void deleteEmployee() {
        System.out.println("\nDelete Employee");
        Long id = readLongInput("Enter employee ID to delete: ");

        if (service.deleteEmployee(id)) {
            System.out.println("Employee with ID " + id + " successfully deleted.");
        } else {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }

    private void findEmployeeById() {
        System.out.println("\nFind Employee by ID");
        Long id = readLongInput("Enter employee ID: ");

        Optional<Employee> employee = service.findById(id);
        if (employee.isPresent()) {
            System.out.println("\nEmployee found:");
            System.out.println(employee.get());
        } else {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }

    private void showStatistics() {
        System.out.println("\nCompany Statistics");
        List<Employee> employees = service.getAllEmployees();

        if (employees.isEmpty()) {
            System.out.println("No data for statistics. Employee list is empty.");
            return;
        }

        double avgSalary = service.getAverageSalary();
        System.out.printf("Average salary in company: %.0f rub.%n", avgSalary);

        Optional<Employee> topEmployee = service.findHighestPaidEmployee();
        if (topEmployee.isPresent()) {
            Employee top = topEmployee.get();
            System.out.println("Highest paid employee:");
            System.out.println("   " + top);
        }
    }

    private void filterByPosition() {
        System.out.println("\nFilter by Position");
        String position = readStringInput("Enter position to filter: ");

        List<Employee> filtered = service.filterByPosition(position);
        if (filtered.isEmpty()) {
            System.out.println("Employees with position '" + position + "' not found.");
            return;
        }

        System.out.println("\nEmployees found: " + filtered.size());
        for (Employee e : filtered) {
            System.out.println(e);
        }
    }

    private void saveToFile() {
        System.out.println("\nSave to CSV File");
        System.out.println("1. Save to default file (employees.csv)");
        System.out.println("2. Save to custom file");
        int saveChoice = readIntInput("Choose option: ");

        String filename;
        if (saveChoice == 1) {
            filename = "employees.csv";
            service.saveToFile(filename);
        } else if (saveChoice == 2) {
            filename = readStringInput("Enter filename (for example kukushkina.csv): ");
            if (!filename.endsWith(".csv")) {
                filename += ".csv";
            }
            service.saveToFile(filename);
        } else {
            System.out.println("Invalid option. Returning to menu.");
        }
    }

    private void saveOnExit() {
        System.out.println("\nDo you want to save before exiting?");
        System.out.println("1. Yes, save to file");
        System.out.println("2. No, exit without saving");
        int choice = readIntInput("Choose option: ");

        if (choice == 1) {
            saveToFile();
        }
    }

    private int readIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: please enter a valid integer.");
            }
        }
    }

    private long readLongInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: please enter a valid number.");
            }
        }
    }

    private double readDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: please enter a valid number (use dot as decimal separator).");
            }
        }
    }

    private String readStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private LocalDate readDateInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(scanner.nextLine().trim(), dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Error: please enter date in format dd.MM.yyyy (e.g., 15.03.2024)");
            }
        }
    }

    private String truncateString(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
}