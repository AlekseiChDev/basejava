package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected final void doUpdate(Resume r, Object index) {
            storage[(Integer) index] = r;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public List<Resume> getAllSorted() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    @Override
    protected final void doSave(Resume r, Object index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        insertElement(r, (Integer) index);
        size++;
    }

    @Override
    protected final void doDelete(Object index) {
        fillDeletedElement((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected final Resume doGet(Object index) {
        return storage[(Integer) index];
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume r, int index);

}