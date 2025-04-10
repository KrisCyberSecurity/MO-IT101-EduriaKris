package com.mycompany.motorphpayrollsystem;

import java.io.*;
import java.util.*;

// Employee class defined outside the main class for better modularity
class Employee {
    private int employeeNumber;
    private String name;
    private int hoursWorked;
    private double hourlyRate;
    private double grossSalary;
    private double netSalary;

    // Constructor
    public Employee(int employeeNumber, String name, int hoursWorked, double hourlyRate) {
        this.employeeNumber = employeeNumber;
        this.name = name;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
        this.grossSalary = calculateGrossSalary();
        this.netSalary = calculateNetSalary();
    }

    // Calculate Gross Salary
    private double calculateGrossSalary() {
        return hoursWorked * hourlyRate;
    }

    // Calculate Government Deductions and Net Salary
    private double calculateNetSalary() {
        double sss = grossSalary * 0.045;
        double pagIbig = grossSalary * 0.02;
        double philHealth = grossSalary * 0.03;
        double incomeTax = grossSalary * 0.10;

        double totalDeductions = sss + pagIbig + philHealth + incomeTax;
        return grossSalary - totalDeductions;
    }

    // Improved display method using StringBuilder for better performance
    public void displayEmployeeDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("Employee Number: ").append(employeeNumber).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Hours Worked: ").append(hoursWorked).append("\n");
        sb.append("Hourly Rate: PHP ").append(String.format("%.2f", hourlyRate)).append("\n");
        sb.append("Gross Salary: PHP ").append(String.format("%.2f", grossSalary)).append("\n");
        sb.append("Net Salary: PHP ").append(String.format("%.2f", netSalary)).append("\n");
        sb.append("------------------------------");
        System.out.println(sb.toString());
    }
}

// Main class
public class MotorPHPayrollSystem {

    // File path made configurable (improvement for flexibility)
    private static final String FILE_PATH = "employees.txt";

    public static void main(String[] args) {
        List<Employee> employees = loadEmployeesFromFile(FILE_PATH);

        if (employees.isEmpty()) {
            System.out.println("No employee data found.");
        } else {
            System.out.println("=== Employee Payroll Details ===");
            for (Employee emp : employees) {
                emp.displayEmployeeDetails();
            }
        }
    }

    // Refactored file reading into a separate method for better structure and reusability
    private static List<Employee> loadEmployeesFromFile(String filename) {
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] data = line.split(",");

                // Added validation to skip lines with incorrect format
                if (data.length != 4) {
                    System.out.println("Skipping invalid data at line " + lineNumber);
                    continue;
                }

                try {
                    int employeeNumber = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    int hoursWorked = Integer.parseInt(data[2].trim());
                    double hourlyRate = Double.parseDouble(data[3].trim());

                    employees.add(new Employee(employeeNumber, name, hoursWorked, hourlyRate));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format at line " + lineNumber + ": " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Employee file not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return employees;
    }
}
