package com.kopiyama.service;

import com.kopiyama.models.Person;
import com.kopiyama.models.Reservation;
import com.kopiyama.models.Service;
import com.kopiyama.repositories.PersonRepository;
import com.kopiyama.repositories.ServiceRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Getter
@Setter
@NoArgsConstructor

public class MenuService {
    private static List<Person> personList = PersonRepository.getAllPerson();
    private static List<Service> serviceList = ServiceRepository.getAllService();
    public static List<Reservation> reservationList = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);

    public static void mainMenu() {
        boolean exit = false;
        do {
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Welcome to 79 Salon");
            System.out.println("1. Show Data");
            System.out.println("2. Create Reservation");
            System.out.println("3. Complete/cancel reservation");
            System.out.println("0. Exit");

            int choice = ValidationService.validateMenuChoice();

            switch (choice) {
                case 1:
                    showDataSubMenu();
                    break;
                case 2:
                    ReservationService.createReservation();
                    break;
                case 3:
                    ReservationService.editReservationWorkstage();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        } while (!exit);
    }

    private static void showDataSubMenu() {
        boolean backToMainMenu = false;
        do {
            System.out.println("\nShow Data");
            System.out.println("1. Recent Reservation");
            System.out.println("2. Show Customer");
            System.out.println("3. Show Available Employee");
            System.out.println("4. Show History Reservation");
            System.out.println("0. Back to main menu");

            int choice = ValidationService.validateMenuChoice();

            switch (choice) {
                case 1:
                    PrintService.showRecentReservations();
                    ValidationService.validateReturnChoice();
                    break;
                case 2:
                    PrintService.showAllCustomers();
                    ValidationService.validateReturnChoice();
                    break;
                case 3:
                    PrintService.showAllEmployees();
                    ValidationService.validateReturnChoice();
                    break;
                case 4:
                    PrintService.showHistoryReservations();
                    break;
                case 0:
                    backToMainMenu = true;
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        } while (!backToMainMenu);
    }


}
