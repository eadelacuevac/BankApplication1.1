package com.devsu.hackerearth.backend.client.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<ClientDto> getAll() {
        return clientRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDto getById(Long id) {
        // CAMBIO: Retorna null si no existe, en vez de lanzar excepción
        return clientRepository.findById(id).map(this::mapToDto).orElse(null);
    }

    @Override
    public ClientDto create(ClientDto clientDto) {
        Client client = mapToEntity(clientDto);
        return mapToDto(clientRepository.save(client));
    }

    @Override
    public ClientDto update(ClientDto clientDto) {
        // CAMBIO: Si existe, actualiza y guarda. Si no, retorna null.
        return clientRepository.findById(clientDto.getId()).map(client -> {
            client.setDni(clientDto.getDni());
            client.setName(clientDto.getName());
            client.setGender(clientDto.getGender());
            client.setAge(clientDto.getAge());
            client.setAddress(clientDto.getAddress());
            client.setPhone(clientDto.getPhone());
            client.setPassword(clientDto.getPassword());
            client.setActive(clientDto.isActive());
            return mapToDto(clientRepository.save(client));
        }).orElse(null);
    }

    @Override
    public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
        // CAMBIO: Si existe, actualiza solo el estado. Si no, retorna null.
        return clientRepository.findById(id).map(client -> {
            client.setActive(partialClientDto.isActive());
            return mapToDto(clientRepository.save(client));
        }).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        // CAMBIO: Elimina solo si existe (la validación del 404 ya la hace el
        // Controller)
        clientRepository.findById(id).ifPresent(clientRepository::delete);
    }

    // Mapeos Manuales
    private ClientDto mapToDto(Client client) {
        return new ClientDto(
                client.getId(), client.getDni(), client.getName(),
                client.getPassword(), client.getGender(), client.getAge(),
                client.getAddress(), client.getPhone(), client.isActive());
    }

    private Client mapToEntity(ClientDto dto) {
        Client client = new Client();
        client.setId(dto.getId());
        client.setDni(dto.getDni());
        client.setName(dto.getName());
        client.setGender(dto.getGender());
        client.setAge(dto.getAge());
        client.setAddress(dto.getAddress());
        client.setPhone(dto.getPhone());
        client.setPassword(dto.getPassword());
        client.setActive(dto.isActive());
        return client;
    }
}