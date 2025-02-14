package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static ru.javawebinar.basejava.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    private static final String MESSAGE_STORAGE_OVERFLOW = "Storage overflow";
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void overflowArray() throws Exception {
        storage.clear();
        try {
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume("Name" + i));
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
}