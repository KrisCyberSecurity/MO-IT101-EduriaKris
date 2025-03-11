package com.mycompany.motorphpayrollsystem;

import java.io.*;
import java.util.*;

// Employee class outside of MotorPHPayrollSystem
class Employee {
    int employeeNumber;
    String name;
    int hoursWorked;
    double hourlyRate;
    double grossSalary;
    double netSalary;

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
    public double calculateGrossSalary() {
        return hoursWorked * hourlyRate;
    }

    // Calculate Government Deductions and Net Salary
    public double calculateNetSalary() {
        double sss = grossSalary * 0.045;
        double pagIbig = grossSalary * 0.02;
        double philHealth = grossSalary * 0.03;
        double incomeTax = grossSalary * 0.10;
       
        double totalDeductions = sss + pagIbig + philHealth + incomeTax;
        return grossSalary - totalDeductions;
    }

    // Display Employee Details
    public void displayEmployeeDetails() {
        System.out.println("Employee Number: " + employeeNumber);
        System.out.println("Name: " + name);
        System.out.println("Hours Worked: " + hoursWorked);
        System.out.println("Hourly Rate: PHP " + hourlyRate);
        System.out.println("Gross Salary: PHP " + grossSalary);
        System.out.println("Net Salary: PHP " + netSalary);
        System.out.println("------------------------------");
    }
}

// Main class for payroll system
public class MotorPHPayrollSystem {

    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
       
        // Read employee details from a file
        try (BufferedReader br = new BufferedReader(new FileReader("employees.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int employeeNumber = Integer.parseInt(data[0]);
                String name = data[1];
                int hoursWorked = Integer.parseInt(data[2]);
                double hourlyRate = Double.parseDouble(data[3]);
               
                employees.add(new Employee(employeeNumber, name, hoursWorked, hourlyRate));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
       
        // Display employee details
        for (Employee emp : employees) {
            emp.displayEmployeeDetails();
        }
    }
}

