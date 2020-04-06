package org.telegram.telegrambots.meta.updateshandlers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.Serializable;

/**
 * @author vgorin
 * file created on 2020-04-06 17:11
 */

public interface ExtendedCallback<T extends Serializable> extends SentCallback<T> {
    /**
     * Called when the request is successful
     * @param method Method executed
     * @param response Answer from Telegram server
     */
    default void onResult(BotApiMethod<T> method, T response) {
        onResult((PartialBotApiMethod<T>) method, response);
    }

    /**
     * Called when the request fails
     * @param method Method executed
     * @param apiException Answer from Telegram server (contains error information)
     */
    default void onError(BotApiMethod<T> method, TelegramApiRequestException apiException) {
        onError((PartialBotApiMethod<T>) method, apiException);
    }

    /**
     * Called when the http request throw an exception
     * @param method Method executed
     * @param exception Exception thrown
     */
    default void onException(BotApiMethod<T> method, Exception exception) {
        onException((PartialBotApiMethod<T>) method, exception);
    }

    /**
     * Called when the request is successful
     * @param method Method executed
     * @param response Answer from Telegram server
     */
    void onResult(PartialBotApiMethod<T> method, T response);

    /**
     * Called when the request fails
     * @param method Method executed
     * @param apiException Answer from Telegram server (contains error information)
     */
    void onError(PartialBotApiMethod<T> method, TelegramApiRequestException apiException);

    /**
     * Called when the http request throw an exception
     * @param method Method executed
     * @param exception Exception thrown
     */
    void onException(PartialBotApiMethod<T> method, Exception exception);

}
