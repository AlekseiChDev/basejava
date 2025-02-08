package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    public int size() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }

    @Override
    protected final void doUpdate(Resume r, Object index) {
        storage.set((Integer) index, r);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public List<Resume> getAllSorted() {
        return storage;
    }

    @Override
    protected final void doSave(Resume r, Object index) {
        storage.add(r);
    }

    @Override
    protected final void doDelete(Object index) {
        storage.remove(((Integer) index).intValue());
    }

    @Override
    protected final Resume doGet(Object index) {
        return storage.get((Integer) index);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object index) {
        return  (Integer) index >= 0;
    }
}
