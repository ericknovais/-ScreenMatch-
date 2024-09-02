package br.com.endsystem.screenmatch;

import br.com.endsystem.screenmatch.model.DadosEpisodio;
import br.com.endsystem.screenmatch.model.DadosSerie;
import br.com.endsystem.screenmatch.model.DadosTemporada;
import br.com.endsystem.screenmatch.service.ConsumoApi;
import br.com.endsystem.screenmatch.service.ConverteDados;
import br.com.endsystem.screenmatch.service.IConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumoApi consumoApi = new ConsumoApi();
		String json = consumoApi.obterDados("https://www.omdbapi.com/?t=supernatural&apikey=aa01283d");
		System.out.println(json);
		ConverteDados converteDados = new ConverteDados();
		DadosSerie dadosSerie = converteDados.obterDados(json, DadosSerie.class);
		System.out.println(dadosSerie);
		json = consumoApi.obterDados("https://www.omdbapi.com/?t=supernatural&apikey=aa01283d&season=1&episode=2");
		DadosEpisodio episodio = converteDados.obterDados(json, DadosEpisodio.class);
		System.out.println(episodio);

		List<DadosTemporada> temporadas = new ArrayList<>();
		for (int i = 1; i <= dadosSerie.totalTemporada(); i++){
			json = consumoApi.obterDados("https://www.omdbapi.com/?t=supernatural&apikey=aa01283d&season=%s".formatted(i));
			DadosTemporada temporada = converteDados.obterDados(json, DadosTemporada.class);
			temporadas.add(temporada);
		}
		temporadas.forEach(System.out::println);
	}
}


