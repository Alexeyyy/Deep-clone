package models;

import javafx.util.Pair;
import kotlin.Triple;
import org.omg.CORBA.INTERNAL;

import java.util.*;

public class MyComplexObject {
    private int count;
    private double percent;
    private List<Man> men;
    private List<Man> copyMen;
    private MyComplexObject thisObject;
    private Hashtable<Pair<Integer, Integer>, List<HashMap<String, Triple<Integer, List<Man>, Double>>>> hardcore;

    public MyComplexObject(int count, double percent, List<Man> men) {
        this.count = count;
        this.percent = percent;
        this.men = new ArrayList<Man>();
        fillMen();
        this.copyMen = men;
        this.thisObject = this;
    }

    private void fillMen() {
        ArrayList<String> books = new ArrayList<>();
        books.add("Book 1");
        books.add("Book 2");
        books.add("Book 3");
        this.men.add(new Man("Petr", 20, books));
        // намеренно добавим копию книг.
        this.men.add(new Man("Vladislav", 25, books));
    }

    // Добавляет немного хардкорную структуру данных.
    public void addHardcore() {
        HashMap<String, Triple<Integer, List<Man>, Double>> hashmap_1 = new HashMap<>();
        hashmap_1.put("ключ 1", new Triple<>(10, new ArrayList<>(Arrays.asList(new Man("1", 10, new ArrayList<String>(Arrays.asList("1", "2", "3"))))), 80.7));
        hashmap_1.put("ключ 2", new Triple<>(20, new ArrayList<>(Arrays.asList(new Man("2", 20, new ArrayList<String>(Arrays.asList("7", "8", "9"))))), 150.425));

        HashMap<String, Triple<Integer, List<Man>, Double>> hashmap_2 = new HashMap<>();
        hashmap_2.put("ключ 3", new Triple<>(30, new ArrayList<>(Arrays.asList(new Man("3", 30, new ArrayList<String>(Arrays.asList("4", "5", "6"))))), 98.4));
        hashmap_2.put("ключ 4", new Triple<>(40, new ArrayList<>(Arrays.asList(new Man("4", 40, new ArrayList<String>(Arrays.asList("10", "11", "12"))))), 4784.45));

        hardcore = new Hashtable<>();
        hardcore.put(new Pair(1,2), new ArrayList<>(Arrays.asList(hashmap_1, hashmap_2)));
    }
}
