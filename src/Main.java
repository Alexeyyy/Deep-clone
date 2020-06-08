import deepClone.CopyUtils;
import test.Book;
import test.Man;

import java.util.ArrayList;

class Main {
    public static void main(String[] args) {
        ArrayList<String> books = new ArrayList<String>();
        books.add("1");
        books.add("2");
        books.add("3");

        ArrayList<Book> books2 = new ArrayList<Book>();
        books2.add(new Book("1", "author 1", 2015));
        books2.add(new Book("2", "author 2", 2017));
        books2.add(new Book("3", "author 3", 2018));

        Man m = new Man("Alex", 10, books, books2);
        m.addHardcore();
        Man m2 = (Man) CopyUtils.deepCopy(m);

        int a = 0;
        a++;
    }
}
