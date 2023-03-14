package com.example.OrtaApp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CommandsForBot extends TelegramLongPollingBot {

    UserInfo userInfo = new UserInfo(false, getBaseUrl(), getBaseUrl());
    IftarInfo iftarInfo = new IftarInfo(getBotUsername(), getBaseUrl(), getBaseUrl(), getBaseUrl(), getBotToken(),
            getBaseUrl());

    String[] questions = { "amount of people?", "price per person?", "for boys?", "place?",
            "decription:" };
    List<String> userIftarInput = new ArrayList<>();

    SendMessage sendMessage = new SendMessage();

    boolean isOrganizer = false;
    String city = "";
    String gender = "";
    boolean isFinishedRegistration = false;
    boolean isMethodFinished = false;
    int i = 0;

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

        Message userMessage = update.getMessage();

        if (userMessage != null && userMessage.hasText()) {

            if (userMessage.getText().equals("participate in iftar")) {
                isOrganizer = false;
                userInfo.isOrganizer = isOrganizer;
                showKeyboard(userMessage, "Where are you currently are?", "Astana", "Almaty");
            }

            else if (userMessage.getText().equals("organize iftar")) {
                isOrganizer = true;
                userInfo.isOrganizer = isOrganizer;
                showKeyboard(userMessage, "Where are you currently are?", "Astana", "Almaty");
            }

            else if (userMessage.getText().equals("Astana")) {
                city = "Astana";
                userInfo.city = city;
                showKeyboard(userMessage, "Your gender?", "Male", "Female");
            }

            else if (userMessage.getText().equals("Almaty")) {
                city = "Almaty";
                userInfo.city = city;
                showKeyboard(userMessage, "Your gender?", "Male", "Female");
            }

            else if (isOrganizer == true && userMessage.getText().equals("Male")) {
                gender = "Male";
                userInfo.gender = gender;
                hideKeyboard(userMessage, "you're organizer\nname of iftar?");
                isFinishedRegistration = true;
            }

            else if (isOrganizer == true && userMessage.getText().equals("Female")) {
                gender = "Female";
                userInfo.gender = gender;
                hideKeyboard(userMessage, "you're organizer\nname of iftar?");
                isFinishedRegistration = true;
            }

            else if (isOrganizer == false && userMessage.getText().equals("Male")) {
                gender = "Male";
                userInfo.gender = gender;
                hideKeyboard(userMessage, "you're participant");
                showUserInfo(userMessage);
                showButtons(userMessage, "Is everything correct1?");
                isFinishedRegistration = true;
            }

            else if (isOrganizer == false && userMessage.getText().equals("Female")) {
                gender = "Female";
                userInfo.gender = gender;
                hideKeyboard(userMessage, "you're participant");
                showUserInfo(userMessage);
                showButtons(userMessage, "Is everything correct1?");
                isFinishedRegistration = true;
            }

            else if (isFinishedRegistration == true && isOrganizer == true) {

                if (isMethodFinished == false) {
                    showQuestions(userMessage);
                } else {
                    sendMessage(userMessage, "message");
                }

                if (isMethodFinished == true) {
                    showButtons2(userMessage, "Is everything correct2?");
                }
            }

            switch (userMessage.getText()) {
                case ("/start"):
                    isFinishedRegistration = false;
                    isOrganizer = false;
                    city = null;
                    sendMessage(userMessage, "Welcome to OrtaAppBot!");
                    showKeyboard(userMessage, "What are you up to?", "participate in iftar", "organize iftar");
                    break;

                case ("/dua"):
                    try {
                        hideKeyboard(userMessage, "don't forget to say Bismillah!");
                        sendPicture(userMessage,
                                "https://www.muftyat.kz/media/muftyat/WhatsApp_Image_2022-04-01_at_16.19.52.jpeg",
                                "Dua for iftar & suhur");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case ("/table"):
                    try {
                        hideKeyboard(userMessage, "take a look at namaz times!");
                        sendPicture(userMessage,
                                "https://www.zharar.com/uploads/posts/2022-03/1646907524_full_1646475407fb7821273c5b96bddcd9908d3bb09384_webp.webp",
                                "Ramadan 2023 table");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case ("/about"):
                    hideKeyboard(userMessage, "Description about Orta App");
                    sendMessage(userMessage,
                            "You can follow us on instagram: https://www.instagram.com/zhanger.alekul/");
                    break;

                // default:
                // sendMessage(userMessage, "E waimaw, bauyrym");
            }
        }

        else if (update.hasCallbackQuery()) {

            if (update.getCallbackQuery().getData().equals("edit")) {
                showKeyboard2(update, "What are you up to?", "participate in iftar", "organize iftar");
            }

            else if (update.getCallbackQuery().getData().equals("edit2")) {
                isMethodFinished = false;
                hideButtons(update, "you're organizer\nname of iftar?");
                showQuestions(update.getCallbackQuery().getMessage());
            }

            if (update.getCallbackQuery().getData().equals("continue")) {
                showIftars2(update);
            }

            else if (update.getCallbackQuery().getData().equals("continue2")) {
                hideButtons(update, "You're iftar uploaded successfully");
            }
        }

    }

    public void sendMessage(Message message, String text) {
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);

        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showKeyboard(Message message, String text, String button1, String button2) {
        // creating keyboard with answers
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        // creating row and rows for keyboard
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        // adding values for each row
        row1.add(button1);
        row2.add(button2);
        // adding each row in rows
        rows.add(row1);
        rows.add(row2);
        // setting rows with answeres to keyboard
        replyKeyboardMarkup.setKeyboard(rows);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setResizeKeyboard(true);
        // setting id, text and keyboard for reply
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void hideKeyboard(Message message, String text) {
        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(replyKeyboardRemove);

        replyKeyboardRemove.setSelective(true);
        replyKeyboardRemove.setRemoveKeyboard(true);

        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void hideButtons(Update update, String text) {
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
        editMessageReplyMarkup.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessageReplyMarkup.setReplyMarkup(null);

        try {
            execute(editMessageReplyMarkup);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showQuestions(Message message) {

        sendMessage.setChatId(message.getChatId().toString());

        if (i != questions.length) {
            sendMessage.setText(questions[i]);
            i++;
        }

        userIftarInput.add(message.getText());
        System.out.println(userIftarInput);

        if (userIftarInput.size() == 6) {
            sendMessage.setText("Your iftar info:\nname - " + userIftarInput.get(0) +
                    "\namount of people - "
                    + userIftarInput.get(1) + "\nprice per person - "
                    + userIftarInput.get(2) + "\nfor boys? - "
                    + userIftarInput.get(3) + "\nplace - "
                    + userIftarInput.get(4) + "\ndescription - "
                    + userIftarInput.get(5));

            iftarInfo.nameOfIftar = userIftarInput.get(0);
            iftarInfo.amountOfPeople = userIftarInput.get(1);
            iftarInfo.pricePerPerson = userIftarInput.get(2);
            iftarInfo.forBoys = userIftarInput.get(3);
            iftarInfo.place = userIftarInput.get(4);
            iftarInfo.description = userIftarInput.get(5);

            // userIftarInput.clear();
            i = 0;
            // isFinishedRegistration = false;
            // isOrganizer = false;
            isMethodFinished = true;
        }

        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showIftars(Message message) {
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Iftar list");

        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showUserInfo(Message message) {
        sendMessage.setChatId(message.getChatId().toString());

        sendMessage.setText(userInfo.toString());

        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showIftarInfo(Message message) {
        sendMessage.setChatId(message.getChatId().toString());

        sendMessage.setText(iftarInfo.toString());

        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPicture(Message message, String pictureURL, String caption)
            throws MalformedURLException, IOException {
        SendPhoto sendPhoto = new SendPhoto();

        sendPhoto.setChatId(message.getChatId().toString());
        sendPhoto.setPhoto(
                new InputFile(new URL(pictureURL).openStream(), "WhatsApp_Image_2022-04-01_at_16.19.52.jpeg"));
        sendPhoto.setCaption(caption);

        sendPhoto.setAllowSendingWithoutReply(true);

        try {
            execute(sendPhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showButtons(Message message, String text) {

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
        InlineKeyboardButton keyboardButton2 = new InlineKeyboardButton();
        // Btn1
        keyboardButton.setText("Edit");
        keyboardButton.setCallbackData("edit");
        // Btn2
        keyboardButton2.setText("Continue");
        keyboardButton2.setCallbackData("continue");
        // Row
        List<InlineKeyboardButton> keyboardButtonRow = new ArrayList<>();
        keyboardButtonRow.add(keyboardButton);
        keyboardButtonRow.add(keyboardButton2);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonRow);

        keyboardMarkup.setKeyboard(rowList);

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(keyboardMarkup);

        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showButtons2(Message message, String text) {

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
        InlineKeyboardButton keyboardButton2 = new InlineKeyboardButton();
        // Btn1
        keyboardButton.setText("Edit");
        keyboardButton.setCallbackData("edit2");
        // Btn2
        keyboardButton2.setText("Continue");
        keyboardButton2.setCallbackData("continue2");
        // Row
        List<InlineKeyboardButton> keyboardButtonRow = new ArrayList<>();
        keyboardButtonRow.add(keyboardButton);
        keyboardButtonRow.add(keyboardButton2);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonRow);

        keyboardMarkup.setKeyboard(rowList);

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(keyboardMarkup);

        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showKeyboard2(Update update, String text, String btn1, String btn2) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        // creating row and rows for keyboard
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        // adding values for each row
        row1.add(btn1);
        row2.add(btn2);
        // adding each row in rows
        rows.add(row1);
        rows.add(row2);
        // setting rows with answeres to keyboard
        replyKeyboardMarkup.setKeyboard(rows);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setResizeKeyboard(true);

        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void showIftars2(Update update) {
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
        sendMessage.setText("Iftar list");
        sendMessage.setReplyMarkup(null);
        System.out.println(userInfo.toString());

        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}