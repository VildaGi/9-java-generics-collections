package com.example.task02;

import java.io.*;
import java.nio.file.Files;
import java.util.AbstractList;
import java.util.ArrayList;

public class SavedList<E extends Serializable> extends AbstractList<E> implements Serializable{
    private final ArrayList<E> list = new ArrayList<>();
    private final File file;

    public SavedList(File file) {
        this.file = file;
        if(file.exists() && file.length() > 0){
            getFromFile();
        } else {
            file.getParentFile().mkdirs();
            save();
        }
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        E tmp = list.set(index, element);
        save();
        return tmp;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
        save();
    }

    @Override
    public E remove(int index) {
        E tmp = list.remove(index);
        save();
        return tmp;
    }

    private void getFromFile(){
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(file.toPath()))) {
            Object data = ois.readObject();
            if (data instanceof ArrayList) {
                list.clear();
                list.addAll((ArrayList<E>) data);
            }
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void save(){
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file.toPath()))) {
            oos.writeObject(new ArrayList<>(list));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
