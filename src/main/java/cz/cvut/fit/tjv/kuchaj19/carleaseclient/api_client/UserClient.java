package cz.cvut.fit.tjv.kuchaj19.carleaseclient.api_client;

import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.*;

@Component
public class UserClient {
    private final String baseUrl;
    private final RestClient userRestClient;
    private RestClient currentUserRestClient;
    private RestClient filteredUserRestClient;

    public UserClient(@Value("${api.url}") String baseUrl) {
        this.baseUrl = baseUrl;
        userRestClient = RestClient.create(baseUrl + "/user");
    }

    public void setCurrentUser(Long ID) {
        currentUserRestClient = RestClient.builder()
                .baseUrl(baseUrl + "/user/{id}")
                .defaultUriVariables(Map.of("id", ID))
                .build();
    }
    public void setFilteredUsers(Optional<String> email, Optional<String> name, Optional<String> phone) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl + "/user");
        filteredUserRestClient = RestClient.create(
                factory.builder()
                        .queryParamIfPresent("email", email)
                        .queryParamIfPresent("name",name)
                        .queryParamIfPresent("phone",phone)
                        .build().toString()
        );
    }

    public void create(User data) {
        userRestClient.post()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    public Optional<User> readOne() {
        try {
            return Optional.ofNullable(
                    currentUserRestClient.get().accept(MediaType.APPLICATION_JSON).retrieve().toEntity(User.class).getBody()
            );
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }

    public Collection<User> readAll() {
        return Arrays.asList(
                Objects.requireNonNull(userRestClient.get()
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .toEntity(User[].class)
                        .getBody())
        );
    }

    public Collection<User> readFiltered() {
        return Arrays.asList(
                Objects.requireNonNull(filteredUserRestClient.get()
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .toEntity(User[].class)
                        .getBody())
        );
    }

    public void update(User data) {
        currentUserRestClient.put()
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    public void delete() {
        currentUserRestClient.delete()
                .retrieve()
                .toBodilessEntity();
    }
}
