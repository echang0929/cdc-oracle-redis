package com.tutorial.cdc.redis.service;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.tutorial.cdc.redis.entity.Customer;
import com.tutorial.cdc.redis.repository.CustomerRepository;
import io.debezium.data.Envelope.Operation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Transactional
    public void maintainReadModel(Map<String, Object> customerData, Operation operation) {
        final JsonMapper jsonMapper = JsonMapper.builder()
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .build();
        final Customer customer = jsonMapper.convertValue(customerData, Customer.class);

        if (operation == Operation.DELETE) {
            customerRepository.deleteById(customer.getId());
        } else {
            customerRepository.save(customer);
        }
    }
}
