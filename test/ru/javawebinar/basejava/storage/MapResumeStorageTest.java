package ru.javawebinar.basejava.storage;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapResumeStorageTest extends AbstractArrayStorageTest{

    public MapResumeStorageTest() {
        super(new MapResumeStorage());
    }

    @Override
    @Ignore
    @Test
    public void overflowArray() throws Exception {
    }
}