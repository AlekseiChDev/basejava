package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    public final void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        }
        updateDeep(r, index);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    public final void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        }
        saveDeep(r, index);
    }


    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        deleteDeep(index);
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getDeep(index);
    }

    protected abstract void saveDeep(Resume resume, int index);
    protected abstract void updateDeep(Resume resume, int index);
    protected abstract void deleteDeep(int index);
    protected abstract Resume getDeep(int index);
    protected abstract int getIndex(String uuid);
}