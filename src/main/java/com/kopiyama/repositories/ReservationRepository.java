package com.kopiyama.repositories;

import com.kopiyama.models.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {
    private static List<Reservation> reservations = new ArrayList<>();

    public static List<Reservation> getAllReservations() {
        return reservations;
    }

    public static void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public static Reservation getReservationById(String reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId().equals(reservationId)) {
                return reservation;
            }
        }
        return null;
    }
}
