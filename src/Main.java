import deepClone.CopyUtils;
import javafx.util.Pair;
import models.Book;
import models.Man;
import models.MyComplexObject;
import test.DeepCopyTest;
import java.util.Hashtable;
import java.util.List;

class Main {
    public static void main(String[] args) {
        // Test 0. Тестирование на основе предложенного в задании класса.
        try {
            Man alex = DeepCopyTest.getMan();
            Man newAlex = (Man) CopyUtils.deepCopy(alex);

            System.out.println("Test 0 passed");
        }
        catch (Exception ex) {
            System.out.println("Test 0 failed. The error occurred while copying object");
        }

        // Test 1. Копирование Hash-таблицы.
        try {
            Hashtable<String, List<Book>> bookStore = DeepCopyTest.getBooks();
            Hashtable<String, List<Book>> copyBookStore = (Hashtable<String, List<Book>>) CopyUtils.deepCopy(bookStore);

            // Если смотреть debug, то наименование изменится только в базовой hash-таблице.
            bookStore.get("Русская классика").get(0).setName("Воскресение");

            // Меняем объект, где ссылка --> меняется в обоих списках: Русская классика и Copy of Русская классика.
            copyBookStore.get("Copy of Русская классика").get(0).setName("Собрание сочинений");

            System.out.println("Test 1 passed");
        }
        catch (Exception ex) {
            System.out.println("Test 1 failed. The error occurred while copying object");
        }

        // Test 2. Копирование пары.
        try {
            Pair<Integer, Book> pair = new Pair<>(10, new Book("BookName", "BookAuthor", 2021, 1000.00));
            Pair<Integer, Book> pairCopy = (Pair<Integer, Book>)CopyUtils.deepCopy(pair);

            System.out.println("Test 2 passed");
        }
        catch (Exception ex) {
            System.out.println("Test 2 failed. The error occurred while copying object");
        }

        // Test 3. Копирование сложного объекта (смотри метод addHardcore()).
        try {
            MyComplexObject obj = DeepCopyTest.getMyComplexObject();
            MyComplexObject copyObj = (MyComplexObject) CopyUtils.deepCopy(obj);

            System.out.println("Test 3 passed");
        }
        catch (Exception ex) {
            System.out.println("Test 3 failed. The error occurred while copying object");
        }

        // Test 4. Копирование массива.
        try {
            Man[] a = new Man[] {DeepCopyTest.getMan(), DeepCopyTest.getMan()};
            Man[] copyA = (Man[]) CopyUtils.deepCopy(a);

            int[] b = new int[] { 1,2,3,4,5 };
            int[] copyB = (int[]) CopyUtils.deepCopy(b);

            // Многомерочка.
            int[][][] c =  { { { 1, 2 } }, { { 3, 4} }, { { 5, 6} } };
            int[][][] copyC = (int[][][]) CopyUtils.deepCopy(c);

            Man[][][] d = { { { DeepCopyTest.getMan(), DeepCopyTest.getMan() } }, { { DeepCopyTest.getMan(), DeepCopyTest.getMan()}  }, { { DeepCopyTest.getMan(), DeepCopyTest.getMan()} } };
            Man[][][] copyD = (Man[][][]) CopyUtils.deepCopy(d);

            System.out.println("Test 4 passed");
        }
        catch (Exception ex) {
            System.out.println("Test 4 failed. The error occurred while copying object");
        }
    }
}
