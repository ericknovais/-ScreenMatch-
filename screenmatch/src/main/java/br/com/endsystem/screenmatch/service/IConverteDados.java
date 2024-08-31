package br.com.endsystem.screenmatch.service;

import br.com.endsystem.screenmatch.model.DadosSerie;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
