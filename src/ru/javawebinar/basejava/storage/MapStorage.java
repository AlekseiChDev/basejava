package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    public int size() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }

    @Override
    protected final void doUpdate(Resume r, Object uuid) {
        storage.put((String) uuid, r);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public List<Resume> getAll() {
        List<Resume> all = new ArrayList<>(storage.values());
        return all;
    }

    @Override
    protected final void doSave(Resume r, Object uuid) {
        storage.put((String) uuid, r);
    }

    @Override
    protected final void doDelete(Object uuid) {
        storage.remove((String) uuid);
    }

    @Override
    protected final Resume doGet(Object uuid) {
        return storage.get((String) uuid);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object uuid) {
        return  storage.get((String) uuid) != null;
    }
}
