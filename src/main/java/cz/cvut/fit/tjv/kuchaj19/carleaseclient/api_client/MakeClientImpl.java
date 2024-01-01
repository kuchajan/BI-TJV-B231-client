package cz.cvut.fit.tjv.kuchaj19.carleaseclient.api_client;


import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.Make;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class MakeClientImpl extends CrudClientImpl<Make> implements MakeClient {
    private final String baseUrl;
    private final RestClient makeClient;
    private RestClient currentMakeClient;

    public MakeClientImpl(@Value("${api.url}") String baseUrl) {
        this.baseUrl = baseUrl;
        makeClient = RestClient.create(baseUrl + "/make");
    }

    @Override
    public void setCurrentClient(Long id) {
        currentMakeClient = RestClient.builder()
                .baseUrl(baseUrl + "/make/{id}")
                .defaultUriVariables(Map.of("id", id))
                .build();
    }

    @Override
    protected RestClient getClient() {
        return makeClient;
    }

    @Override
    protected RestClient getCurrentClient() {
        return currentMakeClient;
    }

    @Override
    protected Class<Make> getTClass() {
        return Make.class;
    }

    @Override
    protected Class<Make[]> getAClass() {
        return Make[].class;
    }
}
