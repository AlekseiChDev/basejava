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
    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String MESSAGE_STORAGE_OVERFLOW = "Storage overflow";
    private static final Resume resume1 = new Resume(UUID_1);
    private static final Resume resume2 = new Resume(UUID_2);
    private static final Resume resume3 = new Resume(UUID_3);
    private static final Resume resume4 = new Resume(UUID_4);

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
        Resume[] expected = new Resume[0];
        Assert.assertArrayEquals(expected, storage.getAll());
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_3);
        storage.update(resume);
        Assert.assertSame(resume, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume());
    }


    @Test
    public void getAll() throws Exception {
        Resume[] expected = new Resume[]{resume1,resume2,resume3};
        Assert.assertArrayEquals(expected, storage.getAll());
    }

    @Test
    public void save() throws Exception {
        storage.save(resume4);
        assertSize(4);
        assertGet(resume4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(resume3);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        try {
            storage.delete(UUID_1);
        } catch (NotExistStorageException e) {
            Assert.fail("Error delete");
        }
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("DUMMY");
    }

    @Test
    public void get() throws Exception {
        assertGet(resume1);
        assertGet(resume2);
        assertGet(resume3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void fillArrayCompletely() throws Exception {
        storage.clear();
        for (int i = 0; i < STORAGE_LIMIT; i++) {
            storage.save(new Resume());
        }
    }

    @Test(expected = StorageException.class)
    public void overflowArray() throws Exception {
        storage.clear();
        try {
        for (int i = 0; i < STORAGE_LIMIT; i++) {
            storage.save(new Resume());
        }
        } catch (StorageException e) {
            if (e.getMessage().equals(MESSAGE_STORAGE_OVERFLOW)) {
                Assert.fail("Storage overflow ahead of time");
            } else {
                Assert.fail(e.getMessage());
            }
        }
        storage.save(new Resume());
    }

    public void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }

    public void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

}