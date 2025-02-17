package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapUuidStorage extends AbstractStorage<String> {

    private final Map<String, Resume> storage = new HashMap<>();

    public int size() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }

    @Override
    protected final void doUpdate(Resume r, String uuid) {
        storage.put(uuid, r);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public List<Resume> getAll() {
        List<Resume> all = new ArrayList<>(storage.values());
        return all;
    }

    @Override
    protected final void doSave(Resume r, String uuid) {
        storage.put(uuid, r);
    }

    @Override
    protected final void doDelete(String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected final Resume doGet(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String uuid) {
        return  storage.get(uuid) != null;
    }
}
