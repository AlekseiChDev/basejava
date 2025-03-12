package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ObjectStreamPathStorage extends AbstractPathStorage {
    protected ObjectStreamPathStorage(String directory, SerializationStrategy serializationStrategy) {
        super(directory, serializationStrategy);
    }

    @Override
    protected void doWrite(Resume r, OutputStream os) throws IOException {
        serializationStrategy.doWrite(r, os);
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        return  serializationStrategy.doRead(is);
    }
}
