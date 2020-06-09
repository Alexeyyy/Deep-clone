package models;

public class Book {
    private String name;
    private String author;
    private int releaseYear;
    private double price;

    public Book(String name, String author, int releaseYear, double price) {
        this.author = author;
        this.name = name;
        this.releaseYear = releaseYear;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
