package com.kopiyama.service;

import com.kopiyama.models.*;
import com.kopiyama.repositories.PersonRepository;
import com.kopiyama.repositories.ReservationRepository;
import com.kopiyama.repositories.ServiceRepository;

import java.util.List;
import java.util.stream.Collectors;

public class PrintService {
    public static void printMenu(String title, String[] menuArr){
        int num = 1;
        System.out.println(title);
        for (int i = 0; i < menuArr.length; i++) {
            if (i == (menuArr.length - 1)) {   
                num = 0;
            }
            System.out.println(num + ". " + menuArr[i]);   
            num++;
        }
    }

    public String printServices(List<Service> serviceList){
        String result = "";
        // Bisa disesuaikan kembali
        for (Service service : serviceList) {
            result += service.getServiceName() + ", ";
        }
        return result;
    }

    // Function yang dibuat hanya sebgai contoh bisa disesuaikan kembali
    public void showRecentReservation(List<Reservation> reservationList){
        int num = 1;
        System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Biaya Service", "Pegawai", "Workstage");
        System.out.println("+========================================================================================+");
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("Waiting") || reservation.getWorkstage().equalsIgnoreCase("In process")) {
                System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                num, reservation.getReservationId(), reservation.getCustomer().getName(), printServices(reservation.getServices()), reservation.getReservationPrice(), reservation.getEmployee().getName(), reservation.getWorkstage());
                num++;
            }
        }
    }

    public static void showAllCustomers() {
        List<Person> persons = PersonRepository.getAllPerson();
        System.out.println("+-----+---------+------------------+-------------+-------------+------------------+");
        System.out.println("|No   | ID      | Nama             | Alamat      |Membership   | Uang             |");
        System.out.println("+-----+---------+------------------+-------------+-------------+------------------+");
        int num = 1;
        for (Person person : persons) {
            if (person instanceof Customer) {
                Customer customer = (Customer) person;
                System.out.printf("|%-4d | %-7s | %-16s | %-11s | %-11s | Rp%-14.2f |\n",
                        num++, customer.getId(), customer.getName(), customer.getAddress(),
                        customer.getMember().getMembershipName(), customer.getWallet());
            }
        }
        System.out.println("+-----+---------+------------------+-------------+-------------+------------------+");
    }

    public static void showAllEmployees() {
        List<Person> persons = PersonRepository.getAllPerson();
        System.out.println("+-----+---------+----------------+---------------+--------------+");
        System.out.println("|No   | ID      | Nama           | Alamat        | Pengalaman   |");
        System.out.println("+-----+---------+----------------+---------------+--------------+");
        int num = 1;
        for (Person person : persons) {
            if (person instanceof Employee) {
                Employee employee = (Employee) person;
                System.out.printf("|%-4d | %-7s | %-14s | %-13s | %-12d |\n",
                        num++, employee.getId(), employee.getName(), employee.getAddress(), employee.getExperience());
            }
        }
        System.out.println("+-----+---------+----------------+---------------+--------------+");
    }

    public static void showAllServices() {
        List<Service> services = ServiceRepository.getAllService();
        System.out.println("+----+----------+---------------------+-------------------+");
        System.out.println("|No  | ID       | Nama                | Harga             |");
        System.out.println("+----+----------+---------------------+-------------------+");
        int num = 1;
        for (Service service : services) {
            System.out.printf("|%-3d | %-8s | %-19s | Rp%-15.2f |\n",
                    num++, service.getServiceId(), service.getServiceName(), service.getPrice());
        }
        System.out.println("+----+----------+---------------------+-------------------+");
    }

    public static void showRecentReservations() {
        List<Reservation> reservations = ReservationRepository.getAllReservations();
        List<Reservation> filteredReservations = reservations.stream()
                .filter(r -> "In Process".equals(r.getWorkstage()))
                .collect(Collectors.toList());

        if (filteredReservations.isEmpty()) {
            System.out.println("\nThere are no reservations at this time.");
        } else {
            System.out.println("+-----+---------+---------------+-------------------------------------------------+---------------+------------+");
            System.out.println("|No   | ID      | Nama Customer | Service                                         | Total Biaya   | Workstage  |");
            System.out.println("+-----+---------+---------------+-------------------------------------------------+---------------+------------+");
            int num = 1;
            for (Reservation reservation : filteredReservations) {
                String services = reservation.getServices().stream()
                        .map(service -> service.getServiceName())
                        .collect(Collectors.joining(", "));
                System.out.printf("|%-4d | %-7s | %-13s | %-47s | Rp%-11.2f | %-10s |\n",
                        num++, reservation.getReservationId(), reservation.getCustomer().getName(),
                        services, reservation.getReservationPrice(), reservation.getWorkstage());
            }
            System.out.println("+-----+---------+---------------+-------------------------------------------------+---------------+------------+");
        }
    }

    public static void showHistoryReservations() {
        List<Reservation> reservations = ReservationRepository.getAllReservations();
        List<Reservation> finishedReservations = reservations.stream()
                .filter(r -> "Finish".equals(r.getWorkstage()))
                .collect(Collectors.toList());

        if (finishedReservations.isEmpty()) {
            System.out.println("\nThere are no finished reservations at this time.");
        } else {
            System.out.println("+-----+---------+---------------+-------------------------------------------------+---------------+------------+");
            System.out.println("|No   | ID      | Nama Customer | Service                                         | Total Biaya   | Workstage  |");
            System.out.println("+-----+---------+---------------+-------------------------------------------------+---------------+------------+");
            int num = 1;
            double totalIncome = 0;
            for (Reservation reservation : finishedReservations) {
                String services = reservation.getServices().stream()
                        .map(service -> service.getServiceName())
                        .collect(Collectors.joining(", "));
                System.out.printf("|%-4d | %-7s | %-13s | %-47s | Rp%-11.2f | %-10s |\n",
                        num++, reservation.getReservationId(), reservation.getCustomer().getName(),
                        services, reservation.getReservationPrice(), reservation.getWorkstage());
                totalIncome += reservation.getReservationPrice();
            }
            System.out.println("+-----+---------+---------------+-------------------------------------------------+---------------+------------+");
            System.out.printf("| Total Keuangan                                                                  | Rp%-24.2f |\n", totalIncome);
            System.out.println("+-----+---------+---------------+-------------------------------------------------+---------------+------------+");
        }
        ValidationService.validateReturnChoice();
    }
}
