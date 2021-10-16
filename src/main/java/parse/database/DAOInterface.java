package parse.database;

public interface DAOInterface<T> {
    public void save(T t);
}
