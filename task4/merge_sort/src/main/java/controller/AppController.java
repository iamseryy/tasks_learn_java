package controller;

import model.Person;
import util.SortUtils;

import java.util.ArrayList;

public class AppController {
    public static void start(){
        var persons = new ArrayList<Person>();
        persons.add(new Person(27, "Nikita", "Petriov"));
        persons.add(new Person(18, "Olga", "Nikitina"));
        persons.add(new Person(25, "Ivan", "Semenov"));
        persons.add(new Person(19, "Irina", "Romanova"));
        persons.add(new Person(29, "Mihail", "Kiselev"));
        persons.add(new Person(27, "Arina", "Abramova"));
        persons.add(new Person(31, "Alice", "Anikina"));

        System.out.println(persons.toString());
        SortUtils.mergeSort(persons, (person1, person2) -> person1.surname().compareTo(person2.surname()));
        System.out.println(persons.toString());
    }
}
