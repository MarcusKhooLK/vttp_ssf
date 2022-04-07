package nus.iss.edu.workshop16;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import nus.iss.edu.workshop16.repository.GameRepository;
import nus.iss.edu.workshop16.services.GameService;

@SpringBootApplication
public class Workshop16Application implements CommandLineRunner {

	@Autowired
	private GameService gameSvc;

	@Autowired
	private GameRepository gameRepo;

	public static void main(String[] args) {
		SpringApplication.run(Workshop16Application.class, args);
	}

	@Override
	public void run(String[] args) {
		if (args.length <= 0) {
			System.err.println("Please pass in a JSON file to parse");
			System.exit(-1);
		}

		try{
			FileInputStream inputStream = new FileInputStream(args[0]);
			JsonArray gamesArray = gameSvc.loadData(inputStream);
			gamesArray.stream()
			.map(v -> (JsonObject)v)
			.forEach((JsonObject v) -> {
				gameRepo.save(v);
			});
		}catch(FileNotFoundException e){
			System.err.println(e.getMessage());
			System.exit(-1);
		}

		System.out.println("completed!");

	}

}
