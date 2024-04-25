package com.kopiyama.repositories;

import com.kopiyama.models.Customer;
import com.kopiyama.models.Employee;
import com.kopiyama.models.Membership;
import com.kopiyama.models.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonRepository {
    private static List<Person> personList = null; // inisialisasi awal sebagai null

    public static List<Person> getAllPerson(){
        if (personList == null) {
            initializePersonList();
        }
        return personList;
    }

    private static void initializePersonList() {
        personList = new ArrayList<>();

        Membership member1 = new Membership("Mem-01", "none");
        Membership member2 = new Membership("Mem-02", "Silver");
        Membership member3 = new Membership("Mem-03", "Gold");

        Person customer1 = Customer.builder()
                .id("Cust-01")
                .name("Budi")
                .address("Bandung")
                .member(member1)
                .wallet(1_000_000)
                .build();

        Person customer2 = Customer.builder()
                .id("Cust-02")
                .name("Aceng")
                .address("Cimahi")
                .member(member2)
                .wallet(1_000_000)
                .build();

        Person customer3 = Customer.builder()
                .id("Cust-03")
                .name("Nur")
                .address("Garut")
                .member(member3)
                .wallet(1_000_000)
                .build();

        Person customer4 = Customer.builder()
                .id("Cust-04")
                .name("Iwan")
                .address("Sukabumi")
                .member(member2)
                .wallet(1_000_000)
                .build();

        Person employee1 = Employee.builder()
                .id("Emp-01")
                .name("Jono")
                .address("Bandung")
                .experience(3)
                .build();

        Person employee2 = Employee.builder()
                .id("Emp-02")
                .name("Joni")
                .address("Cimahi")
                .experience(1)
                .build();

        Person employee3 = Employee.builder()
                .id("Emp-03")
                .name("Hana")
                .address("Garut")
                .experience(5)
                .build();

        personList.addAll(List.of(customer1, customer2, customer3, customer4, employee1, employee2, employee3));
    }
}
