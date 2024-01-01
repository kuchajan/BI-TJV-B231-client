package cz.cvut.fit.tjv.kuchaj19.carleaseclient.service;

import cz.cvut.fit.tjv.kuchaj19.carleaseclient.api_client.MakeClient;
import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.Make;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class MakeService {
    private final MakeClient makeClient;
    private Long currentMake;

    public MakeService(MakeClient makeClient) {
        this.makeClient = makeClient;
    }

    public Boolean isCurrentMake() {
        return currentMake != null;
    }

    public void setCurrentMake(Long currentMake) {
        this.currentMake = currentMake;
        makeClient.setCurrentClient(currentMake);
    }

    public Optional<Make> readOne() {
        return makeClient.getOne();
    }
    public Collection<Make> readAll() {
        return makeClient.getAll();
    }
    public void create(Make data) {
        makeClient.create(data);
    }
    public void update(Make data) {
        makeClient.update(data);
    }

    public void delete() {
        makeClient.delete();
    }
}
