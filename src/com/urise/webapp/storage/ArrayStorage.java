package com.urise.webapp.storage; /**
 * Array based storage for Resumes
 */
import com.urise.webapp.model.Resume;

import java.util.Arrays;
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        for (int i = 0; i < size; i++) {
            if (r.uuid.equals(storage[i].uuid)) {
                System.out.println("Ошибка сохранения: резюме " + r + " уже есть в хранилище. ");
                return;
            }
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
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return storage[i];
            }
        }
        System.out.println("Резюме " + uuid + " не найдено");
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                size--;
                if (i != size) {
                    storage[i] = storage[size];
                }
                storage[size] = null;
                System.out.println("Резюме " + uuid + " удалено");
                return;
            }
        }
        System.out.println("Резюме " + uuid + " не найдено");
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
        for (int i = 0; i < size; i++) {
            if (resume.uuid.equals(storage[i].uuid)) {
                storage[i] = resume;
                System.out.println("Резюме " + resume + " обновлено");
                return;
            }
        }
        System.out.println("Ошибка обновления: резюме " + resume + " еще нет в хранилище. ");
    }
}
