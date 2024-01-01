package cz.cvut.fit.tjv.kuchaj19.carleaseclient.api_client;

import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public abstract class CrudClientImpl<T> implements CrudClient<T> {
    protected abstract RestClient getClient();
    protected abstract RestClient getCurrentClient();
    protected abstract Class<T> getTClass();
    protected abstract Class<T[]> getAClass();

    @Override
    public void create(T data) {
        getClient().post()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public Collection<T> getAll() {
        return Arrays.asList(
                Objects.requireNonNull(getClient().get()
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .toEntity(getAClass())
                        .getBody())
        );
    }

    @Override
    public Optional<T> getOne() {
        try {
            return Optional.ofNullable(
                    getCurrentClient()
                            .get()
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .toEntity(getTClass())
                            .getBody()
            );
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(T data) {
        getCurrentClient()
                .put()
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void delete() {
        getCurrentClient()
                .delete()
                .retrieve()
                .toBodilessEntity();
    }
}
