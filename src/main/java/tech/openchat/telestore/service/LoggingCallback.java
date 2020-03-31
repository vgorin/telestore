package tech.openchat.telestore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

/**
 * @author vgorin
 * file created on 2020-03-31 17:59
 */

@Slf4j
@Component
class LoggingCallback implements SentCallback<Message> {
    @Override
    public void onResult(BotApiMethod<Message> method, Message response) {
        log.trace("onResult {} {}", method, response);
    }

    @Override
    public void onError(BotApiMethod<Message> method, TelegramApiRequestException apiException) {
        log.error("onError " + method, apiException);
    }

    @Override
    public void onException(BotApiMethod<Message> method, Exception exception) {
        log.warn("onException " + method, exception);
    }
}
