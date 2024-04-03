package com.carhiring.carhiring.business;

import com.carhiring.carhiring.data.entities.Operator;
import com.carhiring.carhiring.data.entities.User;
import com.carhiring.carhiring.data.repositories.OperatorRepository;

public class OperatorService {

    private final OperatorRepository repository = OperatorRepository.getInstance();

    private static class OperatorServiceHolder {
        public static final OperatorService INSTANCE = new OperatorService();
    }

    public static OperatorService getInstance() {
        return OperatorServiceHolder.INSTANCE;
    }

    public Operator getOperatorByUser(User user) {
        return repository.getByUser(user);
    }
}
