package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    public int size() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }

    @Override
    protected final void doUpdate(Resume r, Object resume) {
        storage.put(((Resume) resume).getUuid(), r);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public List<Resume> getAll() {
        List<Resume> all = new ArrayList<>(storage.values());
        return all;
    }

    @Override
    protected final void doSave(Resume r, Object resume) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected final void doDelete(Object resume) {
        storage.remove(((Resume) resume).getUuid());
    }

    @Override
    protected final Resume doGet(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Object resume) {
        return  resume != null;
    }
}
