package test;

import models.Book;
import models.Man;
import models.MyComplexObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class DeepCopyTest {
    public static Hashtable<String, List<Book>> getBooks() {
        ArrayList<Book> category_1 = new ArrayList<>();
        category_1.add(new Book("Война и мир", "Л.Н. Толстой", 1865, 100.045));
        category_1.add(new Book("Преступление и наказание", "Ф.М. Достоевский", 1866, 200.10));
        category_1.add(new Book("Анна Каренина", "Л.Н. Толстой", 1875, 954.45));

        ArrayList<Book> category_2 = new ArrayList<>();
        category_2.add(new Book("Under the Eagle", "Simon Scarrow", 2000, 986.45));
        category_2.add(new Book("The Eagle's Conquest", "Simon Scarrow",2001, 687.542));
        category_2.add(new Book("When the Eagle hunts", "Simon Scarrow", 2002, 452.45));

        ArrayList<Book> category_3 = new ArrayList<>();
        category_3.add(new Book("Master and Commander", "Patrick O'Brian", 1969, 875.542));
        category_3.add(new Book("Post Captain", "Patrick O'Brian",1972, 698.45));
        category_3.add(new Book("HMS Surprise", "Patrick O'Brian", 1973, 457.41));

        Hashtable<String, List<Book>> books = new Hashtable<>();
        books.put("Русская классика", category_1);
        books.put("Historical novel (0 AD - 1000 AD)", category_2);
        books.put("Historical novel (1000 AD - 2000 AD)", category_3);

        // Нарочно сделаем копию.
        books.put("Copy of Русская классика", category_1);

        return books;
    }

    public static Man getMan() {
        Man m = new Man("Алексей", 26, new ArrayList<>());
        m.getFavoriteBooks().add("Traitors of Rome");
        m.getFavoriteBooks().add("The Legion");
        return m;
    }

    public static MyComplexObject getMyComplexObject() {
        ArrayList<Man> men = new ArrayList<>();
        men.add(new Man("Alex", 26, new ArrayList<String>(Arrays.asList("Book 1", "Book 2", "Book 3"))));
        men.add(new Man("Michael", 40, new ArrayList<String>(Arrays.asList("Book 4", "Book 5", "Book 6"))));

        MyComplexObject obj = new MyComplexObject(150, 89.09, men);
        obj.addHardcore();

        return obj;
    }
}
