package cz.cvut.fit.tjv.kuchaj19.carleaseclient.api_client;

import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserClient extends CrudClient<User> {
    public void setFilteredClient (Optional<String> email, Optional<String> name, Optional<String> phone);
    public Collection<User> readFiltered();
}
