package data;

import java.util.List;

public interface Repository<T> {
    List<T> load();
    void save(List<T> data)
}
