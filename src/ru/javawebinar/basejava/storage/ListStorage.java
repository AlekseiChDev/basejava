package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private List<Resume> storage = new ArrayList();

    public int size() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }

    @Override
    protected final void updateDeep(Resume r, int index) {
        storage.set(index, r);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    protected final void saveDeep(Resume r, int index) {
        storage.add(r);
    }

    @Override
    protected final void deleteDeep(int index) {
        storage.remove(index);
    }

    @Override
    protected final Resume getDeep(int index) {
        return storage.get(index);
    }

    @Override
    protected int getIndex(String uuid) {
        int index = 0;
        for (Resume resume: storage
             ) {
            if (resume.getUuid().equals(uuid)) {
              return(index);
            }
            index++;
        }
        return -1;
    }
}
