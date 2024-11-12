package com.gosoft.gobtp.service.impl;

import com.gosoft.gobtp.domain.Client;
import com.gosoft.gobtp.repository.ClientRepository;
import com.gosoft.gobtp.repository.UserRepository;
import com.gosoft.gobtp.service.ClientService;
import com.gosoft.gobtp.service.dto.ClientDTO;
import com.gosoft.gobtp.service.mapper.ClientMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.gosoft.gobtp.domain.Client}.
 */
@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger LOG = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    private final UserRepository userRepository;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.userRepository = userRepository;
    }

    @Override
    public ClientDTO save(ClientDTO clientDTO) {
        LOG.debug("Request to save Client : {}", clientDTO);
        Client client = clientMapper.toEntity(clientDTO);
        String userId = client.getInternalUser().getId();
        userRepository.findById(userId).ifPresent(client::internalUser);
        client = clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    @Override
    public ClientDTO update(ClientDTO clientDTO) {
        LOG.debug("Request to update Client : {}", clientDTO);
        Client client = clientMapper.toEntity(clientDTO);
        client.setIsPersisted();
        client = clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    @Override
    public Optional<ClientDTO> partialUpdate(ClientDTO clientDTO) {
        LOG.debug("Request to partially update Client : {}", clientDTO);

        return clientRepository
            .findById(clientDTO.getId())
            .map(existingClient -> {
                clientMapper.partialUpdate(existingClient, clientDTO);

                return existingClient;
            })
            .map(clientRepository::save)
            .map(clientMapper::toDto);
    }

    @Override
    public Page<ClientDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Clients");
        return clientRepository.findAll(pageable).map(clientMapper::toDto);
    }

    public Page<ClientDTO> findAllWithEagerRelationships(Pageable pageable) {
        return clientRepository.findAllWithEagerRelationships(pageable).map(clientMapper::toDto);
    }

    @Override
    public Optional<ClientDTO> findOne(String id) {
        LOG.debug("Request to get Client : {}", id);
        return clientRepository.findOneWithEagerRelationships(id).map(clientMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
    }
}
