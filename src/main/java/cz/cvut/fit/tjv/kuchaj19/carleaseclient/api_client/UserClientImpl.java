package cz.cvut.fit.tjv.kuchaj19.carleaseclient.api_client;

import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.*;

@Component
public class UserClientImpl extends CrudClientImpl<User> implements UserClient {
    private final RestClient userClient;
    private RestClient currentUserClient;
    private RestClient filteredUserClient;

    private final String baseUrl;

    UserClientImpl(@Value("${api.url}") String baseUrl) {
        this.baseUrl = baseUrl;
        userClient = RestClient.create(baseUrl + "/user");
    }

    public void setCurrentClient(Long id) {
        currentUserClient = RestClient.builder()
                .baseUrl(baseUrl + "/user/{id}")
                .defaultUriVariables(Map.of("id", id))
                .build();
    }
    public void setFilteredClient(Optional<String> email, Optional<String> name, Optional<String> phone) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl + "/user");
        filteredUserClient = RestClient.create(
                factory.builder()
                        .queryParamIfPresent("email", email)
                        .queryParamIfPresent("name",name)
                        .queryParamIfPresent("phone",phone)
                        .build().toString()
        );
    }


    @Override
    protected RestClient getClient() {
        return userClient;
    }

    @Override
    protected RestClient getCurrentClient() {
        return currentUserClient;
    }

    @Override
    protected Class<User> getTClass() {
        return User.class;
    }

    @Override
    protected Class<User[]> getAClass() {
        return User[].class;
    }

    public Collection<User> readFiltered() {
        return Arrays.asList(
                Objects.requireNonNull(filteredUserClient.get()
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .toEntity(User[].class)
                        .getBody())
        );
    }
}
