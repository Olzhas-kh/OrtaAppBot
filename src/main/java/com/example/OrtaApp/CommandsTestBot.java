// package com.example.OrtaApp;

// import java.util.ArrayList;
// import java.util.List;
// import org.telegram.telegrambots.bots.TelegramLongPollingBot;
// import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
// import org.telegram.telegrambots.meta.api.objects.Message;
// import org.telegram.telegrambots.meta.api.objects.Update;
// import
// org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
// import
// org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
// import
// org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
// import
// org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
// import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

// public class CommandsTestBot extends TelegramLongPollingBot {

// IftarInfo iftarInfo = new IftarInfo("()", 0, 0, false, "()", "()");
// UserInfo user = new UserInfo(false, "()", false);

// SendMessage sendMessage = new SendMessage();

// // { "name of iftar?", "amount of people?", "price per person?", "for boys?",
// // "place?",
// // "here's your info about iftar:" };

// ArrayList<String> listOfQuestions = new ArrayList<>();
// int currentQuestionIndex = 0;
// ArrayList<String> iftarUserList = new ArrayList<>();

// @Override
// public String getBotUsername() {
// return "OrtaApp";
// }

// @Override
// public String getBotToken() {
// return "5944899438:AAHsfJtGlBhtGeJqocEGRNglM3NTIj3wjns";
// }

// @Override
// public void onUpdateReceived(Update update) {
// Message message = update.getMessage();

// if (user.isOrganizer == true && user.city != null && user.isBoy == true) {

// if (currentQuestionIndex < listOfQuestions.size()) {
// String question = listOfQuestions.get(currentQuestionIndex);
// sendMessage(message, question);
// iftarUserList.add(message.getText());
// System.out.println(iftarUserList);
// currentQuestionIndex++;
// }

// }

// if (message != null && message.hasText()) {

// if (message.getText().equals("/start")) {
// ShowButtons(message, "participate in iftar", "organize iftar",
// "Ramadan mubarak! May Allah accept your post! What are you up to?", "start0",
// "start1");
// System.out.println(user.toString());
// }

// else if (message.getText().equals("Astana") ||
// message.getText().equals("Almaty")) {
// user.checkCity(message.getText());
// ShowRepliesForMessages(message, "Male", "Female", "Your gender?");
// System.out.println(user.toString());
// }

// else if ((message.getText().equals("Male")
// || message.getText().equals("Female")) && user.isOrganizer == false) {
// user.checkIsBoy(message.getText());
// sendMessage(message, "participant");
// System.out.println(user.toString());
// }

// else if ((message.getText().equals("Male")
// || message.getText().equals("Female")) && user.isOrganizer == true) {
// user.checkIsBoy(message.getText());
// ShowButtons(message, "okay", "let's go", "now it's time to organize iftar!",
// "okay", "let's go");
// System.out.println(user.toString());
// }

// else {
// sendMessage(message, "");
// System.out.println(user.toString());
// }
// }

// else if (update.hasCallbackQuery()) {
// if (update.getCallbackQuery().getData().equals("start0")) {
// System.out.println("callback query participant");
// ShowRepliesForUpdates(update, user, "Astana", "Almaty", "Choose city in where
// you currently are");
// } else if (update.getCallbackQuery().getData().equals("start1")) {
// System.out.println("callback query organizer");
// ShowRepliesForUpdates(update, user, "Astana", "Almaty", "Choose city in where
// you currently are");
// }
// }
// }

// public void addQuestions(String text) {
// listOfQuestions.add(text);
// }

// public void sendMessage(Message message, String text) {
// sendMessage.enableMarkdown(true);

// sendMessage.setChatId(message.getChatId().toString());
// // sendMessage.setReplyToMessageId(message.getMessageId());
// sendMessage.setText(text);

// try {
// execute(sendMessage);
// } catch (TelegramApiException e) {
// e.printStackTrace();
// }
// }

// public void ShowRepliesForUpdates(Update update, UserInfo userInfo, String
// kR1, String kR2, String text) {

// String userUpdate = update.getCallbackQuery().getData();

// // Создаем клавиатуру
// ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
// replyKeyboardMarkup.setSelective(true);
// replyKeyboardMarkup.setResizeKeyboard(true);
// replyKeyboardMarkup.setOneTimeKeyboard(true);

// // Создаем список строк клавиатуры
// List<KeyboardRow> keyboard = new ArrayList<>();

// // Первая строчка клавиатуры
// KeyboardRow keyboardFirstRow = new KeyboardRow();
// // Вторая строчка клавиатуры
// KeyboardRow keyboardSecondRow = new KeyboardRow();

// // Добавляем кнопки в первую и вторую строчку клавиатуры
// if (userUpdate.equals("start0")) {
// keyboardFirstRow.add(kR1);
// keyboardSecondRow.add(kR2);
// userInfo.isOrganizer = false;
// } else if (userUpdate.equals("start1")) {
// keyboardFirstRow.add(kR1);
// keyboardSecondRow.add(kR2);
// userInfo.isOrganizer = true;
// }

// // Добавляем все строчки клавиатуры в список
// keyboard.add(keyboardFirstRow);
// keyboard.add(keyboardSecondRow);
// // и устанавливаем этот список нашей клавиатуре
// replyKeyboardMarkup.setKeyboard(keyboard);

// try {
// execute(SendMessage.builder()
// .chatId(update.getCallbackQuery().getMessage().getChatId().toString())
// .text(text)
// .replyMarkup(replyKeyboardMarkup)
// .build());
// } catch (Exception e) {
// e.printStackTrace();
// }

// }

// public void ShowRepliesForMessages(Message message, String kR1, String kR2,
// String text) {

// String userMessage = message.getText();

// // Создаем клавиатуру
// ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
// sendMessage.setReplyMarkup(replyKeyboardMarkup);
// replyKeyboardMarkup.setSelective(true);
// replyKeyboardMarkup.setResizeKeyboard(true);
// replyKeyboardMarkup.setOneTimeKeyboard(true);

// // Создаем список строк клавиатуры
// List<KeyboardRow> keyboard = new ArrayList<>();

// // Первая строчка клавиатуры
// KeyboardRow keyboardFirstRow = new KeyboardRow();
// // Вторая строчка клавиатуры
// KeyboardRow keyboardSecondRow = new KeyboardRow();

// // Добавляем кнопки в первую и вторую строчку клавиатуры
// if (userMessage.equals("Astana") || userMessage.equals("Almaty")) {
// keyboardFirstRow.add(kR1);
// keyboardSecondRow.add(kR2);
// }

// // Добавляем все строчки клавиатуры в список
// keyboard.add(keyboardFirstRow);
// keyboard.add(keyboardSecondRow);
// // и устанавливаем этот список нашей клавиатуре
// replyKeyboardMarkup.setKeyboard(keyboard);

// try {
// execute(SendMessage.builder()
// .chatId(message.getChatId().toString())
// .text(text)
// .replyMarkup(replyKeyboardMarkup)
// .build());
// } catch (Exception e) {
// e.printStackTrace();
// }

// }

// public void ShowButtons(Message message, String kB1, String kB2, String text,
// String callBD1, String callBD2) {
// InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
// InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
// InlineKeyboardButton keyboardButton2 = new InlineKeyboardButton();
// // Btn1
// keyboardButton.setText(kB1);
// keyboardButton.setCallbackData(callBD1);
// // Btn2
// keyboardButton2.setText(kB2);
// keyboardButton2.setCallbackData(callBD2);
// // Row
// List<InlineKeyboardButton> keyboardButtonRow = new ArrayList<>();
// keyboardButtonRow.add(keyboardButton);
// keyboardButtonRow.add(keyboardButton2);

// List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
// rowList.add(keyboardButtonRow);

// keyboardMarkup.setKeyboard(rowList);

// try {
// execute(SendMessage.builder()
// .chatId(message.getChatId().toString())
// .text(text)
// .replyMarkup(keyboardMarkup)
// .build());
// } catch (Exception e) {
// e.printStackTrace();
// }

// }

// }
