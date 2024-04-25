package com.kopiyama.service;


import com.kopiyama.models.*;
import com.kopiyama.repositories.PersonRepository;
import com.kopiyama.repositories.ReservationRepository;
import com.kopiyama.repositories.ServiceRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder

public class ReservationService {
    private static Scanner input = new Scanner(System.in);
    private static final List<Reservation> reservations = new ArrayList<>();
    private static int nextId = 1;


    public static Person getPersonById(String id) {
        List<Person> persons = PersonRepository.getAllPerson();
        return persons.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static Service getServiceById(String serviceId) {
        List<Service> services = ServiceRepository.getAllService();
        return services.stream()
                .filter(service -> service.getServiceId().equals(serviceId))
                .findFirst()
                .orElse(null);
    }

    public static void createReservation() {
        PrintService.showAllCustomers();
        String customerId = ValidationService.validateCustomerId();
        Customer customer = (Customer) getPersonById(customerId);

        PrintService.showAllEmployees();
        String employeeId = ValidationService.validateEmployeeId();
        Employee employee = (Employee) getPersonById(employeeId);

        PrintService.showAllServices();
        List<Service> selectedServices = new ArrayList<>();
        Set<String> selectedServiceIds = new HashSet<>();
        double totalPrice = 0;
        boolean moreServices = false;

        do {
            String serviceId = ValidationService.validateServiceId(selectedServiceIds);
            Service service = getServiceById(serviceId);
            double servicePrice = service.getPrice();

            double discountedPrice = servicePrice;
            if (customer.getMember().getMembershipName().equalsIgnoreCase("Silver")) {
                discountedPrice *= 0.95;
            } else if (customer.getMember().getMembershipName().equalsIgnoreCase("Gold")) {
                discountedPrice *= 0.90;
            }

            if (customer.getWallet() < (totalPrice + discountedPrice)) {
                System.out.println("Your wallet is not enough to add this service. Current total price: Rp" + totalPrice);
                break;
            }

            selectedServices.add(service);
            totalPrice += discountedPrice;

            System.out.println("Current total price: Rp" + totalPrice);
            System.out.println("Do you want to choose more services? (Y/T)");
            moreServices = ValidationService.validateYesNoInput();
        } while (moreServices);

        if (totalPrice > 0 && customer.getWallet() >= totalPrice) {
            customer.setWallet(customer.getWallet() - totalPrice);
            Reservation reservation = new Reservation("Rsv-" + String.format("%02d", nextId++), customer, employee, selectedServices, totalPrice, "In Process");
            ReservationRepository.addReservation(reservation);
            System.out.println("Booking Successful!");
            System.out.println("Total Booking Cost: Rp" + totalPrice);
            System.out.println("Remaining balance in wallet: Rp" + customer.getWallet());
        } else {
            System.out.println("Booking cancelled or not enough funds to proceed.\n");
            ValidationService.validatePostBookingChoice();
        }
    }


    public static void editReservationWorkstage(){
        List<Reservation> reservations = ReservationRepository.getAllReservations();
        List<Reservation> inProcessReservations = reservations.stream()
                .filter(r -> "In Process".equals(r.getWorkstage()))
                .collect(Collectors.toList());

        if (inProcessReservations.isEmpty()) {
            System.out.println("\nThere are no reservations at this time.\n");
            ValidationService.validatePostBookingChoice();
        }
        System.out.println("\nUpdate Status Reservation");
        PrintService.showRecentReservations();

        String reservationId = ValidationService.validateReservationId();
        Reservation reservation = ReservationRepository.getReservationById(reservationId);

        if (reservation != null && "In Process".equals(reservation.getWorkstage())) {
            String newWorkstage = ValidationService.validateWorkstage();
            if ("Cancel".equalsIgnoreCase(newWorkstage)) {
                Customer customer = reservation.getCustomer();
                customer.setWallet(customer.getWallet() + reservation.getReservationPrice());
                System.out.println("Reservation cancelled. Refunded Rp" + reservation.getReservationPrice() + " to " + customer.getName() + "'s wallet.\n");
            } else {
                System.out.println("Reservation status updated to " + newWorkstage + ".\n");
            }
            reservation.setWorkstage(newWorkstage);
        } else {
            System.out.println("Reservation not found or not in process.");
        }
        ValidationService.validatePostBookingChoice();
    }

    // Silahkan tambahkan function lain, dan ubah function diatas sesuai kebutuhan
}
