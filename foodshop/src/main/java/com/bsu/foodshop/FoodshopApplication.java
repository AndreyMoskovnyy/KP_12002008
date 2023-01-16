package com.bsu.foodshop;

import com.bsu.foodshop.ui.console.FoodShopConsoleClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения
 */
@SpringBootApplication
@RequiredArgsConstructor
// имплементиурем интерфейс CommandLineRunner для того
// чтобы мы могли сами задавать spring'у то, что мы хотим сделать после старта приложения
// в данном случае запустить консольный клиент
public class FoodshopApplication implements CommandLineRunner {
	// импортируем консольный клиент
	private final FoodShopConsoleClient foodShopConsoleClient;

	public static void main(String[] args) {
		SpringApplication.run(FoodshopApplication.class, args);
	}

	// переопределяем точку входа в приложения
	@Override public void run(final String... args) throws Exception {
		// запускаем клиент
		foodShopConsoleClient.run();
	}
}
