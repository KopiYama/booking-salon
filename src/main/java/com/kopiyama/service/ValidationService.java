package com.kopiyama.service;

import com.kopiyama.models.*;
import com.kopiyama.repositories.PersonRepository;
import com.kopiyama.repositories.ReservationRepository;
import com.kopiyama.repositories.ServiceRepository;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ValidationService {
    private static Scanner input = new Scanner(System.in);
    // Buatlah function sesuai dengan kebutuhan
    public static void validateInput(){

    }
    public static String validateCustomerId() {
        Scanner input = new Scanner(System.in);
        List<Person> persons = PersonRepository.getAllPerson();
        String inputId;

        while (true) {
            System.out.print("Please enter Customer ID: ");
            inputId = input.nextLine().trim();

            final String customerId = inputId;

            boolean isValidId = !customerId.isEmpty() && persons.stream()
                    .filter(person -> person instanceof Customer)
                    .anyMatch(customer -> customer.getId().equals(customerId));

            if (isValidId) {
                return customerId;
            } else {
                System.out.println("\nThe ID customer entered is incorrect or not registered. Please try again.");
            }
        }
    }

    public static String validateEmployeeId() {
        Scanner input = new Scanner(System.in);
        List<Person> persons = PersonRepository.getAllPerson();
        String inputId;

        while (true) {
            System.out.print("Please enter Employee ID: ");
            inputId = input.nextLine().trim();

            final String employeeId = inputId;

            boolean isValidId = !employeeId.isEmpty() && persons.stream()
                    .filter(person -> person instanceof Employee)
                    .anyMatch(employee -> employee.getId().equals(employeeId));

            if (isValidId) {
                return employeeId;
            } else {
                System.out.println("\nThe employee ID entered is incorrect or not registered. Please try again.");
            }
        }
    }

    public static String validateServiceId(Set<String> selectedServiceIds) {
        List<Service> services = ServiceRepository.getAllService();
        String inputId;

        while (true) {
            System.out.print("Please enter Service ID: ");
            inputId = input.nextLine().trim();

            final String serviceId = inputId;

            if (selectedServiceIds.contains(serviceId)) {
                System.out.println("\nYou have already added the service you selected. Please try again.");
                continue;
            }

            boolean isValidId = !serviceId.isEmpty() && services.stream()
                    .anyMatch(service -> service.getServiceId().equals(serviceId));

            if (isValidId) {
                selectedServiceIds.add(serviceId);
                return serviceId;
            } else {
                System.out.println("\nThe service ID entered is incorrect or not registered. Please try again.");
            }
        }
    }

    public static int validateMenuChoice() {
        int choice = -1;
        while (true) {
            System.out.print("Enter your choice: ");
            String inputLine = input.nextLine().trim();

            if (!inputLine.matches("\\d+")) {
                System.out.println("Incorrect input. Please enter a valid number.");
                continue;
            }
            choice = Integer.parseInt(inputLine);
            break;
        }
        return choice;
    }

    public static boolean validateYesNoInput() {
        String response;
        while (true) {
            response = input.nextLine().trim().toUpperCase();
            if (response.equals("Y") || response.equals("T")) {
                break;
            } else {
                System.out.println("Please enter 'Y' or 'T'");
            }
        }
        return response.equals("Y");
    }

    public static int validatePostBookingChoice() {
        int choice;
        while (true) {
            System.out.println("0. Back to main menu");
            System.out.print("Enter your choice: ");
            String inputLine = input.nextLine().trim();

            if (inputLine.matches("\\d+")) {
                choice = Integer.parseInt(inputLine);
                if (choice == 0) {
                    MenuService.mainMenu();
                } else {
                    System.out.println("Incorrect input. Please enter '0' to go back to main menu.");
                }
            } else {
                System.out.println("Incorrect input. Please enter a valid number.");
            }
        }
    }

    public static void validateReturnChoice() {
        int choice;
        do {
            System.out.println("\n0. Back to Show Data");
            System.out.println("1. Back to main menu");
            System.out.print("Enter your choice: ");
            String inputLine = input.nextLine().trim();

            if (inputLine.matches("[01]")) {
                choice = Integer.parseInt(inputLine);
                switch (choice) {
                    case 0:
                        System.out.println("Returning to Show Data menu...");
                        return;
                    case 1:
                        System.out.println("Returning to main menu...");
                        MenuService.mainMenu();
                        break;
                    default:
                        System.out.println("An unexpected error occurred.");
                        break;
                }
            } else {
                System.out.println("Incorrect input. Please enter a valid number (0 or 1).");
            }
        } while (true);
    }

    public static String validateReservationId() {
        while (true) {
            System.out.print("Please enter Reservation ID: ");
            String reservationId = input.nextLine().trim();
            if (!reservationId.isEmpty()) {
                Reservation reservation = ReservationRepository.getReservationById(reservationId);
                if (reservation != null) {
                    return reservationId;
                }
            }
            System.out.println("The reservation ID entered is incorrect or not registered. Please try again.");
        }
    }

    public static String validateWorkstage() {
        System.out.println("Please enter Workstage status:");
        System.out.println("1. Finish");
        System.out.println("2. Cancel");
        System.out.print("Enter your choice: ");

        while (true) {
            String choice = input.nextLine().trim();
            switch (choice) {
                case "1":
                    return "Finish";
                case "2":
                    return "Cancel";
                default:
                    System.out.println("Invalid choice entered. Please enter 1 for Finish or 2 for Cancel.");
                    System.out.print("Enter your choice: ");
                    break;
            }
        }
    }
}
