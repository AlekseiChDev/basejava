package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.basejava.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String NAME_1 = "name1";
    private static final String NAME_2 = "name2";
    private static final String NAME_3 = "name3";
    private static final String NAME_4 = "name4";
    private static final String MESSAGE_STORAGE_OVERFLOW = "Storage overflow";
    private static final String UUID_NOT_EXIST = "dummy";
    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, NAME_1);
        RESUME_2 = new Resume(UUID_2, NAME_2);
        RESUME_3 = new Resume(UUID_3, NAME_3);
        RESUME_4 = new Resume(UUID_4, NAME_4);
    }
    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);

    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
        List<Resume> expected = new ArrayList<>();
        Assert.assertEquals(expected, storage.getAllSorted());
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_3,NAME_3);
        storage.update(resume);
        Assert.assertSame(resume, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume("NameEmpty"));
    }


    @Test
    public void getAll() throws Exception {
        List<Resume> expected = new ArrayList<>();
        expected.add(RESUME_1);
        expected.add(RESUME_2);
        expected.add(RESUME_3);
        Assert.assertEquals(expected, storage.getAllSorted());
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_3);
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
        storage.delete(UUID_NOT_EXIST);
    }

    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_NOT_EXIST);
    }

    @Test(expected = StorageException.class)
    public void overflowArray() throws Exception {
        storage.clear();
        try {
        for (int i = 0; i < STORAGE_LIMIT; i++) {
            storage.save(new Resume("NameEmpty"));
        }
        } catch (StorageException e) {
            if (e.getMessage().equals(MESSAGE_STORAGE_OVERFLOW)) {
                Assert.fail("Storage overflow ahead of time");
            } else {
                Assert.fail(e.getMessage());
            }
        }
        storage.save(new Resume("NameEmpty"));
    }

    public void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }

    public void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

}