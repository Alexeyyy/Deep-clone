package test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Man {
    public String name;
    public int age;
    public List<String> favoriteBooks;
    public List<Book> books;
    public Hashtable<String, Object> hashtable;
    private Man thisMan;

    public Man() {}

    public Man(String name, int age, List<String> favoriteBooks, ArrayList<Book> books) {
        this.name = name;
        this.age = age;
        this.favoriteBooks = favoriteBooks;
        this.books = books;
        this.thisMan = this;

        hashtable = new Hashtable<>();
        hashtable.put("#", this.books.get(0));
        //hashtable.put("@", this);
        //hashtable.put("$", new Object());
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

    public List<String> getFavoriteBooks() {
        return favoriteBooks;
    }

    public void setFavoriteBooks(List<String> favoriteBooks) {
        this.favoriteBooks = favoriteBooks;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}