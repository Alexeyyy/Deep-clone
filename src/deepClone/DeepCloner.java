package deepClone;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public class DeepCloner {
    /*
    * Сканирует объект, составляя карту полей, подъобъектов.
    * Впоследствии используется при процедуру deepClone.
    * */
    public static void scanObject(Object obj, String objUUID, int depth, ArrayList<ObjectRelation> relations, HashMap<String, Object> structure, ArrayList<Integer> hashes) throws Exception {
        int objHashCode =obj.hashCode();
        Class objClass = obj.getClass();

        // Сохраняем хеш объекта.
        hashes.add(objHashCode);

        // Устанавливаем глубину сканирования объекта.
        depth = depth + 1;

        // Получаем все поля, из которых состоит объект, включая приватные.
        Field[] fields = obj.getClass().getDeclaredFields();

        // Сохраняем поля для создания будущей копии.
        // Данные хранятся в виде ключ подструктуры - поля.
        structure.put(objUUID, obj);

        for (Field f: fields) {
            f.setAccessible(true);
            Object fObj = f.get(obj);
            Class fObjClass = fObj.getClass();
            int fObjHashcode = fObj.hashCode();

            // Поле не примитив, а объект какого-то класса.
            if (!isPrimitiveExtended(fObjClass)) {
                // Определяем UUID нового подъобъекта.
                String subObjUUID = UUID.randomUUID().toString();

                // Ранее проходили данный объект? Имеется в виду ссылка на другой объект.
                if (hashes.contains(fObjHashcode)) {
                    relations.add(new ObjectRelation(objUUID, subObjUUID, objClass, fObjClass, objHashCode, fObjHashcode, depth, true));
                } else {
                    relations.add(new ObjectRelation(objUUID, subObjUUID, objClass, fObjClass, objHashCode, fObjHashcode, depth, false));

                    // "Сканируем" дочерний объект только в случае если это не ссылка на уже проверенный ранее объект.
                    scanObject(fObj, subObjUUID, depth, relations, structure, hashes);
                }
            }
        }
    }

    /*
    * Проверяет: является ли тип примитивом?
    * */
    private static boolean isPrimitiveExtended(Class type) {
        if (type == Boolean.class || type == Float.class || type == Long.class || type == Integer.class
                || type == Short.class || type == Character.class || type == Byte.class || type == Double.class
                || type == String.class || type.isPrimitive()) {
            return true;
        }
        return false;
    }

    public static Object copyPrimitiveObject(Class type, Object from) throws Exception {
        Object to = type.newInstance();

        for (Field toField : to.getClass().getDeclaredFields()) {
            toField.setAccessible(true);
            for (Field fromField : from.getClass().getDeclaredFields()) {
                if (toField.getName() == fromField.getName() && toField.getType() == fromField.getType()) {
                    toField.set(to, fromField.get(from));
                }
            }
        }

        return to;
    }

    public static Object makeFullCopy(Object obj) {
        HashMap<Integer, Integer> hashes = new HashMap<>();
        HashMap<Integer, Object> parts = new HashMap<>();

        Object copy = null;

        try {
            Constructor objConst = obj.getClass().getConstructor();
            copy = objConst.newInstance();
            copy = deepClone(obj, copy, hashes, parts);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        return copy;
    }

    /*
    * Метод создания копии объекта.
    * */
    private static Object deepClone(Object obj, Object copy, HashMap<Integer, Integer> hashes, HashMap<Integer, Object> parts) throws Exception {
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
                    copyItem = deepClone(item, copyItem, hashes, parts);

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
                    copyKey = deepClone(key, copyKey, hashes, parts);
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
                    copyValue = deepClone(value, copyValue, hashes, parts);
                    hashes.put(value.hashCode(), copyValue.hashCode());
                    parts.put(copyValue.hashCode(), copyValue);
                }

                // Заполняем сам Map.
                ((Map) copy).put(copyKey, copyValue);
            }

            return copy;
        }

        // Это объект. А здесь нужен конструктор.
        Constructor constructor = obj.getClass().getConstructor();
        copy = constructor.newInstance();

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
                    Object value = deepClone(f.get(obj), fc.get(copy), hashes, parts);
                    fc.set(copy, value);

                    // Выходим из внутреннего цикла, так как все поля уже сопоставлены.
                    break;
                }
            }
        }

        return copy;
    }
}
