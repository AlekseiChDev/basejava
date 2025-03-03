package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {

    private final Map<String, Resume> storage = new HashMap<>();

    public int size() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }

    @Override
    protected final void doUpdate(Resume r, Resume resume) {
        storage.put(resume.getUuid(), r);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public List<Resume> doCopyAll() {
        List<Resume> all = new ArrayList<>(storage.values());
        return all;
    }

    @Override
    protected final void doSave(Resume r, Resume resume) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected final void doDelete(Resume resume) {
        storage.remove(resume.getUuid());
    }

    @Override
    protected final Resume doGet(Resume resume) {
        return resume;
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Resume resume) {
        return  resume != null;
    }
}
