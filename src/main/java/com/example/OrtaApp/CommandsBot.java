package com.example.OrtaApp;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandsBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "OrtaApp";
    }

    @Override
    public String getBotToken() {
        return "5944899438:AAHsfJtGlBhtGeJqocEGRNglM3NTIj3wjns";
    }

    @Override
    public void onUpdateReceived(Update update) {
        throw new UnsupportedOperationException("Unimplemented method 'onUpdateReceived'");
    }

}