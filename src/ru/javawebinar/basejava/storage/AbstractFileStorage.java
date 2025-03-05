package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * gkislin
 * 22.07.2016
 */
public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        try {
            File[] files = directory.listFiles(File::isFile);
            if (files == null) {
                throw new StorageException("Read directory error", null);
            }
            for (File file : files) {
                doDelete(file);
            }
        } catch (SecurityException e) {
            throw new StorageException("Not access to the directory", null);
        }
    }

    @Override
    public int size() {
        try {
            File[] files = directory.listFiles(File::isFile);
            if (files == null) {
                throw new StorageException("Read directory error", null);
            }
            return files.length;
        } catch (SecurityException e) {
            throw new StorageException("Not access to the directory", null);
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;
    protected abstract Resume doRead(File file) throws IOException;

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Delete file error", file.getName());
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        try {
            File[] files = directory.listFiles(File::isFile);
            if (files == null) {
                throw new StorageException("Read directory error", null);
            }
            List<Resume> list = new ArrayList<>();
            for (File file : files) {
                list.add(doGet(file));
            }
            return list;
        } catch (SecurityException e) {
            throw new StorageException("Not access to the directory", null);
        }
    }
}
