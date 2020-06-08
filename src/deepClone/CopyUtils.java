package deepClone;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public class CopyUtils {
    /*
    * Метод, открытый клиенту. Метод позволяет сделать полную копию объекта любой сложности и вложенности.
    * Поддерживает примитивы, кастомные объекты, коллекции, словари, массивы и т.д.
    * @obj - объект, копию которого необходимо сделать.
    * */
    public static Object deepCopy(Object obj) {
        HashMap<Integer, Integer> hashes = new HashMap<>();
        HashMap<Integer, Object> parts = new HashMap<>();

        Object copy = null;

        try {
            copy = new Object();
            copy = deepClone(obj, hashes, parts);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        return copy;
    }

    /*
    * Метод создания копии объекта.
    * @obj - исходный объект.
    * @hashes - словарь сопоставлений хешей: <ключ, значение> = <хеш исходной части объекта, хеш клонированной части объекта>.
    * @parts - словарь сопоставлений хешей и частей созданного объекта. Цель: если есть ссылки на объекты, то сразу определять ссылки.
    * */
    private static Object deepClone(Object obj, HashMap<Integer, Integer> hashes, HashMap<Integer, Object> parts) throws Exception {
        Object copy;

        // Объект пустой.
        if (obj == null) {
            return null;
        }

        Class objCls = obj.getClass();

        // Если объект — ссылка на уже существующую часть.
        if (hashes.containsKey(obj.hashCode())) {
            copy = parts.get(hashes.get(obj.hashCode()));
            return copy;
        }

        // Если объект primitive (int, float и т.д.).
        if (isPrimitiveExtended(objCls)) {
            copy = obj; // все равно хеш один припишет.

            hashes.put(obj.hashCode(), copy.hashCode());
            parts.put(copy.hashCode(), copy);

            return copy;
        }

        // Если объект коллекция.
        if (obj instanceof Collection<?>) {
            copy = (Collection) objCls.getConstructor().newInstance();

            Iterator items = ((Collection) obj).iterator();
            while (items != null && items.hasNext()) {
                Object item = items.next();
                Object copyItem;

                // Ссылка на объект.
                if (hashes.containsKey(item.hashCode())) {
                    copyItem = parts.get(hashes.get(item.hashCode()));
                }
                else {
                    copyItem = new Object();
                    copyItem = deepClone(item, hashes, parts);

                    hashes.put(item.hashCode(), copyItem.hashCode());
                    parts.put(copyItem.hashCode(), copyItem);
                }

                ((Collection) copy).add(copyItem);
            }

            return copy;
        }

        // Если объект — Map-подобный (словари, map и т.д.).
        if (obj instanceof Map<?,?>) {
            copy = (Map) objCls.getConstructor().newInstance();
            Iterator iterator = ((Map<?, ?>) obj).keySet().iterator();

            while (iterator.hasNext()) {
                // Клонируем ключ.
                Object key = iterator.next();
                Object copyKey;

                // Это ссылка на существующий объект.
                if (hashes.containsKey(key.hashCode())) {
                    copyKey = parts.get(hashes.get(key.hashCode()));
                }
                // Это новый объект.
                else {
                    copyKey = new Object();
                    copyKey = deepClone(key, hashes, parts);
                    hashes.put(key.hashCode(), copyKey.hashCode());
                    parts.put(copyKey.hashCode(), copyKey);
                }

                // Клонируем объект.
                Object value = ((Map<?, ?>) obj).get(key);
                Object copyValue;

                if (hashes.containsKey(value.hashCode())) {
                    copyValue = parts.get(hashes.get(value.hashCode()));
                }
                else {
                    copyValue = new Object();
                    copyValue = deepClone(value, hashes, parts);
                    hashes.put(value.hashCode(), copyValue.hashCode());
                    parts.put(copyValue.hashCode(), copyValue);
                }

                // Заполняем сам Map.
                ((Map) copy).put(copyKey, copyValue);
            }

            return copy;
        }

        // Создаем объект, если нет пустого конструктора - приходится немного магию творить.
        copy = instantiate(objCls);

        // Создали объект, сохранили все в hashes и parts.
        hashes.put(obj.hashCode(), copy.hashCode());
        parts.put(copy.hashCode(), copy);

        // Если множество полей и это сложный тип, то собираем объект дальше.
        Field[] objFields = objCls.getDeclaredFields();
        Field[] copyFields = copy.getClass().getDeclaredFields();

        for (Field f : objFields) {
            f.setAccessible(true);

            for (Field fc: copyFields) {
                fc.setAccessible(true);

                if (f.getName() == fc.getName()) {
                    Object value = deepClone(f.get(obj), hashes, parts);
                    fc.set(copy, value);

                    // Выходим из внутреннего цикла, так как все поля уже сопоставлены.
                    break;
                }
            }
        }

        return copy;
    }

    /*
     * Проверяет: является ли тип примитивом? Немного расширяет возможность isPrimitive().
     * @type - входящий тип.
     * */
    private static boolean isPrimitiveExtended(Class type) {
        if (type == Boolean.class || type == Float.class || type == Long.class || type == Integer.class
                || type == Short.class || type == Character.class || type == Byte.class || type == Double.class
                || type == String.class || type.isPrimitive()) {
            return true;
        }
        return false;
    }

    /*
    * Идея создания метода заключается в том, что у типа может и не быть конструктора по умолчанию.
    * Например, есть тип Book. Но конструктор переопределен и есть только Book(int cost), а конструктора Book() нет.
    * */
    private static Object instantiate(Class cls) throws Exception {
        final Constructor constructor = getConstructor(cls);
        final ArrayList<Object> params = new ArrayList<>();
        final Class[] parameters = constructor.getParameterTypes();

        for (Class type: parameters) {
            if (type == Integer.class || type == Float.class || type == Long.class
                    || type == Short.class || type == Double.class || type == Byte.class) {
                params.add(1);
                continue;
            }
            if (type == Integer.TYPE || type == Float.TYPE || type == Long.TYPE
                || type == Short.TYPE || type == Double.TYPE || type == Byte.TYPE) {
                params.add(1);
                continue;
            }
            if (type == String.class) {
                params.add("");
                continue;
            }
            if (type == Character.class || type == Character.TYPE) {
                params.add(' ');
                continue;
            }
            if (type == Boolean.class || type == Boolean.TYPE) {
                params.add(false);
                continue;
            }

            // Если объект, то просто отправляем пустой объект.
            params.add(null);
        }

        final Object instance = constructor.newInstance(params.toArray());

        return instance;
    }

    /*
    * Находит пустой конструктор или если его нет, то первый конструктор.
    * */
    private static Constructor getConstructor(Class cls) {
        Constructor[] constructors = cls.getConstructors();

        for (Constructor c: constructors) {
            if (c.getParameterTypes().length == 0) {
                return c;
            }
        }

        return constructors[0];
    }
}
