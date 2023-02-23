package ru.heapsort.controller;

import ru.heapsort.model.Person;
import ru.heapsort.utils.SortUtils;

import java.util.ArrayList;
import java.util.Comparator;

public class AppController {
    public static void start(){
        var persons = new ArrayList<Person>();
        persons.add(new Person(27, "Nikita", "Abramov"));
        persons.add(new Person(18, "Olga", "Nikitina"));
        persons.add(new Person(25, "Ivan", "Semenov"));
        persons.add(new Person(19, "Irina", "Romanova"));
        persons.add(new Person(29, "Mihail", "Kiselev"));
        persons.add(new Person(27, "Arina", "Petriova"));
        persons.add(new Person(31, "Alice", "Anikina"));

        System.out.println("Origin list");
        System.out.println(persons.toString());

        System.out.println("\nSorted by name");
        SortUtils.heapSort(persons, Comparator.comparing(Person::name));
        System.out.println(persons.toString());

        System.out.println("\nSorted by age, then by surname");
        SortUtils.heapSort(persons, Comparator.comparing(Person::age).thenComparing(Person::surname));
        System.out.println(persons.toString());
    }
}
