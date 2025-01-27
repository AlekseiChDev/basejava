package ru.javawebinar.basejava.storage; /**
 * Array based storage for Resumes
 */
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (index != -1) {
            System.out.println("Ошибка сохранения: резюме " + r + " уже есть в хранилище. ");
            return;
        }

        if (size == storage.length) {
            System.out.println("Ошибка сохранения: хранилище переполнено");
        } else {
            storage[size] = r;
            size++;
            System.out.println("Резюме " + r + " сохранено успешно");
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("Резюме " + uuid + " не найдено");
            return null;
        } else {
            return storage[index];
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("Резюме " + uuid + " не найдено");
        } else {
            size--;
            if (index != size) {
                storage[index] = storage[size];
            }
            storage[size] = null;
            System.out.println("Резюме " + uuid + " удалено");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index == -1) {
            System.out.println("Ошибка обновления: резюме " + resume + " еще нет в хранилище. ");
        } else {
            storage[index] = resume;
            System.out.println("Резюме " + resume + " обновлено");
        }
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
