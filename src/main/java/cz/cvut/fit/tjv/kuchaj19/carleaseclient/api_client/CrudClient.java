package cz.cvut.fit.tjv.kuchaj19.carleaseclient.api_client;

import java.util.Collection;
import java.util.Optional;

public interface CrudClient<T> {
    void setCurrentClient(Long id);
    void create(T data);
    Collection<T> getAll();
    Optional<T> getOne();
    void update(T data);
    void delete();
}
