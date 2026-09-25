package com.example.buscacep.service;

import com.example.buscacep.model.EnderecoResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CepService {

    private final RestClient restClient;

    public CepService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://viacep.com.br/ws")
                .build();
    }

    public EnderecoResponse consultar(String cep) {
        String valor = cep == null ? "" : cep.replaceAll("\\D", "");

        if (valor.isBlank() || valor.length() != 8) {
            throw new IllegalArgumentException("Informe um CEP válido com 8 dígitos.");
        }

        EnderecoResponse response = restClient.get()
                .uri("/{cep}/json", valor)
                .retrieve()
                .body(EnderecoResponse.class);

        if (response == null) {
            throw new IllegalStateException("Não foi possível consultar o CEP informado.");
        }

        if (response.getErro() != null && response.getErro().equals("true")) {
            throw new IllegalArgumentException("CEP não encontrado.");
        }

        return response;
    }
}
