package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    public final void update(Resume r) {
        Object searchKey = getExistingSearchKey(r.getUuid());
        updateDeep(r, searchKey);
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */

    public final void save(Resume r) {
        Object searchKey = getNotExistingSearchKey(r.getUuid());
        saveDeep(r, searchKey);
    }


    public final void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        deleteDeep(searchKey);
    }

    public final Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return getDeep(searchKey);
    }

    protected abstract void saveDeep(Resume resume, Object searchKey);
    protected abstract void updateDeep(Resume resume, Object searchKey);
    protected abstract void deleteDeep(Object searchKey);
    protected abstract Resume getDeep(Object searchKey);
    protected abstract Object getSearchKey(String uuid);
    protected abstract boolean isExist(Object searchKey);
    private Object getExistingSearchKey(String uuid){
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
    private Object getNotExistingSearchKey(String uuid){
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}