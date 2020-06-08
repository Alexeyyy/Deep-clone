import deepClone.DeepCloner;
import deepClone.ObjectRelation;
import test.Book;
import test.Man;
import test.PrimObj;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

class Main {
    public static void main(String[] args) {
        ArrayList<String> books = new ArrayList<String>();
        books.add("1");
        books.add("2");
        books.add("3");

        ArrayList<Book> books2 = new ArrayList<Book>();
        books2.add(new Book("1", "author 1", 2015));
        //books2.add(new Book("2", "author 2", 2017));
        //books2.add(new Book("3", "author 3", 2018));

        try {
            Man m = new Man("Alex", 10, null, books2);
            Man m2 = (Man) DeepCloner.makeFullCopy(m);

            int a = 0;
            a++;

            /*Man m2 = new Man("Alex", 10, books, books2);
            Man m3 = m2;

            ArrayList<ObjectRelation> relations = new ArrayList<>();
            HashMap<String, Object> data = new HashMap<>();
            ArrayList<Integer> hashes = new ArrayList<Integer>();

            DeepCloner.scanObject(m, UUID.randomUUID().toString(), 0, relations, data, hashes);

            PrimObj po1 = new PrimObj("123", 10);
            PrimObj po2 = (PrimObj) DeepCloner.copyPrimitiveObject(po1.getClass(), po1);*/
        }
        catch (Exception ex) {
            System.out.println("Error!");
            System.out.println(ex.getMessage());
        }
    }
}
