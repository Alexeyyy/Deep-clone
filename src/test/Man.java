package test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Man {
    private String name;
    private int age;
    private List<String> favouriteBooks;
    private List<Book> books;
    private Hashtable<String, Object> hashtable;
    private Man thisMan;

    public Man(String name, int age, ArrayList<String> favouriteBooks, ArrayList<Book> books) {
        this.name = name;
        this.age = age;
        this.favouriteBooks = favouriteBooks;
        this.books = books;
        this.thisMan = this;
    }

    /*
    * Специально для проверки ссылок.
    */
    public void addHardcore() {
        hashtable = new Hashtable<>();
        hashtable.put("#", this.books.get(0));
        hashtable.put("@", this);
        hashtable.put("$", new Object());

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getFavouriteBooks() {
        return favouriteBooks;
    }

    public void setFavouriteBooks(List<String> favouriteBooks) {
        this.favouriteBooks = favouriteBooks;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}