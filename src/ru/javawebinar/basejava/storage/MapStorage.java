package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<String, Resume>();

    public int size() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }

    @Override
    protected final void updateDeep(Resume r, Object uuid) {
        storage.put((String) uuid, r);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] all = storage.values().toArray(new Resume[0]);
        Arrays.sort(all);
        return all;
    }

    @Override
    protected final void saveDeep(Resume r, Object uuid) {
        storage.put((String) uuid, r);
    }

    @Override
    protected final void deleteDeep(Object uuid) {
        storage.remove((String) uuid);
    }

    @Override
    protected final Resume getDeep(Object uuid) {
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
