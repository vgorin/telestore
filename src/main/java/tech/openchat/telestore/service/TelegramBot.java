package tech.openchat.telestore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;
import tech.openchat.telestore.cmd.CommandProcessor;

/**
 * @author vgorin
 * file created on 2020-03-30 15:41
 */

@Service
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final String username;
    private final String botToken;
    private final CommandProcessor processor;
    private final SentCallback<Message> callback;

    public TelegramBot(
            @Value("${telegram.bot.username}") String username,
            @Value("${telegram.bot.api_token}") String botToken,
            CommandProcessor processor,
            SentCallback<Message> callback
    ) {
        this.username = username;
        this.botToken = botToken;
        this.processor = processor;
        this.callback = callback;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.trace("onUpdateReceived {}", update);
        BotApiMethod<Message> method = processor.process(update);
        if(method != null) {
            try {
                executeAsync(method, callback);
            }
            catch(TelegramApiException e) {
                log.warn("executeAsync failed o execute method " + method, e);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
