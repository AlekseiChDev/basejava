package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static ru.javawebinar.basejava.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String MESSAGE_STORAGE_OVERFLOW = "Storage overflow";
    private static final Resume r1 = new Resume(UUID_1);
    private static final Resume r2 = new Resume(UUID_2);
    private static final Resume r3 = new Resume(UUID_3);

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        storage.update(r1);
        storage.update(r2);
        storage.update(r3);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume());
    }


    @Test
    public void getAll() throws Exception {
        Assert.assertEquals(storage.getAll().length, storage.size());
    }

    @Test
    public void save() throws Exception {
        storage.save(new Resume());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(r3);
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_1);
        storage.delete(UUID_2);
        storage.delete(UUID_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("DUMMY");
    }

    @Test
    public void get() throws Exception {
        storage.get(UUID_1);
        storage.get(UUID_2);
        storage.get(UUID_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void fillArrayCompletely() throws Exception {
        int maxRowsAdd = STORAGE_LIMIT - storage.size();
        for (int i = 0; i < maxRowsAdd; i++) {
            storage.save(new Resume());
        }
    }

    @Test
    public void overflowArray() throws Exception {
        int maxRowsAdd = STORAGE_LIMIT - storage.size();
        try {
        for (int i = 0; i < maxRowsAdd; i++) {
            storage.save(new Resume());
        }
        } catch (StorageException e) {
            if (e.getMessage().equals(MESSAGE_STORAGE_OVERFLOW)) {
                Assert.fail("Storage overflow ahead of time");
            } else {
                Assert.fail(e.getMessage());
            }
        }
        try {
             storage.save(new Resume());
        } catch (StorageException e) {
            if (e.getMessage().equals(MESSAGE_STORAGE_OVERFLOW)) {
                return; }
            else {
                Assert.fail(e.getMessage());
            }
        }
        Assert.fail("Overflow did not occur during");
    }

}