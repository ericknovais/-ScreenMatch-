package br.com.endsystem.screenmatch.principal;

import br.com.endsystem.screenmatch.model.DadosEpisodio;
import br.com.endsystem.screenmatch.model.DadosSerie;
import br.com.endsystem.screenmatch.model.DadosTemporada;
import br.com.endsystem.screenmatch.service.ConsumoApi;
import br.com.endsystem.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private final Scanner leitura = new Scanner(System.in);
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=aa01283d";
    private final ConverteDados converteDados = new ConverteDados();

    public void exibeMenu() {
        System.out.println("Digite o nome da série para busca");
        String nomeSerie = leitura.nextLine();

        String buscaSerie = "%s%s%s".formatted(
                ENDERECO,
                nomeSerie.replace(" ", "+"),
                API_KEY);

        String json = consumoApi.obterDados(buscaSerie);
        DadosSerie dadosSerie = converteDados.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i <= dadosSerie.totalTemporada(); i++) {
            buscaSerie = "%s%s&season=%s%s".formatted(ENDERECO,
                    nomeSerie.replace(" ", "+"),
                    i,
                    API_KEY);
            json = consumoApi.obterDados(buscaSerie);
            DadosTemporada temporada = converteDados.obterDados(json, DadosTemporada.class);
            temporadas.add(temporada);
        }
        temporadas.forEach(System.out::println);
//
//        for (int i = 0; i < dadosSerie.totalTemporada(); i++) {
//            List<DadosEpisodio> episodiosTemporadas = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporadas.size(); j++) {
//                System.out.println(episodiosTemporadas.get(j).titulo());
//            }
//        }
        // Lambeda em Java
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }
}