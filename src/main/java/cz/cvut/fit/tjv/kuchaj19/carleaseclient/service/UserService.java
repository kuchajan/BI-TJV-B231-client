package cz.cvut.fit.tjv.kuchaj19.carleaseclient.service;

import cz.cvut.fit.tjv.kuchaj19.carleaseclient.api_client.UserClient;
import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {
    private final UserClient userClient;
    private Long currentUser;

    public UserService(UserClient userClient) {
        this.userClient = userClient;
    }

    public boolean isCurrentUser() {
        return currentUser != null;
    }

    public void setCurrentUser(Long ID) {
        this.currentUser = ID;
        userClient.setCurrentUser(ID);
    }
    public void setFilteredUsers(Optional<String> email, Optional<String> name, Optional<String> phone) {
        userClient.setFilteredUsers(email,name,phone);
    }
    public Optional<User> readOne() {
        return userClient.readOne();
    }
    public Collection<User> readAll() {
        return userClient.readAll();
    }
    public Collection<User> readFiltered() {
        return userClient.readFiltered();
    }
    public void create(User data) {
        if(data.getPhoneNumber().isEmpty()) {
            data.setPhoneNumber(null);
        }
        userClient.create(data);
    }
    public void update(User data) {
        if(data.getPhoneNumber().isEmpty()) {
            data.setPhoneNumber(null);
        }
        userClient.update(data);
    }

    public void delete() {
        userClient.delete();
    }
}
