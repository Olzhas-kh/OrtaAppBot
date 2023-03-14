package com.example.OrtaApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class OrtaAppApplication {

	public static void main(String[] args) throws TelegramApiException {
		SpringApplication.run(OrtaAppApplication.class, args);
		// CommandsTestBot bot = new CommandsTestBot();
		// CommandsBot bot = new CommandsBot();

		CommandsForBot bot = new CommandsForBot();

		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		telegramBotsApi.registerBot(bot);
	}

}
