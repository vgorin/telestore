package tech.openchat.telestore.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

/**
 * @author vgorin
 * file created on 2020-03-30 15:41
 */

@Service
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final String username;
    private final String botToken;

    public TelegramBot(
            @Value("${telegram.bot.username}") String username,
            @Value("${telegram.bot.api_token}") String botToken
    ) {
        this.username = username;
        this.botToken = botToken;
    }


    @Override
    public void onUpdateReceived(Update update) {
        log.trace("onUpdateReceived {}", update);
        if(update.hasMessage() && update.getMessage().hasText()) {
            try {
                executeAsync(new SendMessage()
                                .setChatId(update.getMessage().getChatId())
                                .setText(StringUtils.reverse(update.getMessage().getText())),
                        new SentCallback<Message>() {
                            @Override
                            public void onResult(BotApiMethod<Message> method, Message response) {
                                log.trace("onResult {} {}", method, response);
                            }

                            @Override
                            public void onError(BotApiMethod<Message> method, TelegramApiRequestException apiException) {
                                log.error("onError {} {}", method, apiException);
                            }

                            @Override
                            public void onException(BotApiMethod<Message> method, Exception exception) {
                                log.warn("onException", exception);
                            }
                        }
                );
            }
            catch(TelegramApiException e) {
                log.warn("executeAsync failed", e);
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
