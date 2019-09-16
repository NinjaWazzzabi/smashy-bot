package database;

interface PersistentData<T> {

    String name();
    T retreive();
    void store(T data);

}
