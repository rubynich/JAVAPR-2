package ru.edu.pr08;

import java.util.HashMap;
import java.util.Map;

public class ClientRepository {
    private final Map<String, Client> data = new HashMap<>();

    public void save(Client client) {
        data.put(client.inn(), client);
    }

    public Client findByInn(String inn) {
        return data.get(inn);
    }

    public boolean containsInn(String inn) {
        return data.containsKey(inn);
    }

    public int size() {
        return data.size();
    }
}
