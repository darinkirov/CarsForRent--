package com.carhiring.carhiring.business;

import com.carhiring.carhiring.data.entities.Client;
import com.carhiring.carhiring.data.repositories.ClientRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class ClientService {

    private static final Logger logger = LogManager.getLogger(ClientService.class);

    private final ClientRepository repository = ClientRepository.getInstance();

    private static class ClientServiceHolder {
        public static final ClientService INSTANCE = new ClientService();
    }

    public static ClientService getInstance() {
        return ClientServiceHolder.INSTANCE;
    }

    public Client getClientByName(String name) {
        return repository.getByName(name);
    }

    public void addClient(Client client) {
        try {
            repository.save(client);
            logger.info("Registering client...");
        } catch (Exception ex) {
            logger.error("Error registering client: ", ex);
        }
    }

    public ObservableList<Client> getAllClients() {
        List<Client> clients = repository.getAll();
        return FXCollections.observableArrayList(clients);
    }
}
