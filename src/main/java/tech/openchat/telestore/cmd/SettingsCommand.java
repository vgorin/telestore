package tech.openchat.telestore.cmd;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import tech.openchat.telestore.entity.ServiceArea;
import tech.openchat.telestore.entity.UserSettings;
import tech.openchat.telestore.service.ServiceAreaService;
import tech.openchat.telestore.service.UserSettingsService;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static tech.openchat.telestore.cmd.CommandUtils.verticalKeyboard;

/**
 * @author vgorin
 * file created on 2020-04-14 19:17
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class SettingsCommand implements NamedCommand {
    private final ResourceBundleMessageSource messageSource;
    private final ServiceAreaService serviceAreaService;
    private final UserSettingsService userSettingsService;

    @Override
    public String getDefaultCommandName() {
        return "settings";
    }

    @Override
    public PartialBotApiMethod<Message> process(CommandPayload payload) {
        Iterator<String> i = payload.getArguments().iterator();
        if(i.hasNext()) {
            String settingsSection = i.next();
            switch(settingsSection) {
                case "service_area": {
                    List<ServiceArea> serviceAreas = serviceAreaService.listServiceAreas();
                    if(serviceAreas.isEmpty()) {
                        log.error("empty service area list; did you forget to fill in service_area table with the data?");
                    }
                    return new SendMessage()
                            .setChatId(payload.getChatId())
                            .setText(messageSource.getMessage("settings.service_area.text", null, Locale.ENGLISH))
                            .setReplyMarkup(verticalKeyboard(serviceAreas.stream().collect(Collectors.toMap(
                                    serviceArea -> "/set service_area " + serviceArea.getId(),
                                    ServiceArea::getAddress
                            ))));
                }
                default: {
                    log.debug("unknown settings section {}", settingsSection);
                }
            }
        }

        ReplyKeyboard keyboard = verticalKeyboard(new LinkedHashMap<String, String>() {{
            put("/settings service_area", messageSource.getMessage("settings.buttons.service_area", null, Locale.ENGLISH));
            put("/start", messageSource.getMessage("settings.buttons.back_to_home", null, Locale.ENGLISH));
        }});


        UserSettings userSettings = userSettingsService.getUserSettings(payload.getUserId());
        if(userSettings != null) {
            String serviceArea = userSettings.getServiceArea() == null? null: userSettings.getServiceArea().getAddress();
            if(serviceArea == null) {
                serviceArea = messageSource.getMessage("settings.texts.not_set", null, Locale.ENGLISH);
            }

            return new SendMessage()
                    .setChatId(payload.getChatId())
                    .setText(String.format(
                            messageSource.getMessage("settings.texts.display_and_prompt", null, Locale.ENGLISH),
                            serviceArea
                    ))
                    .setReplyMarkup(keyboard);
        }

        return new SendMessage()
                .setChatId(payload.getChatId())
                .setText(messageSource.getMessage("settings.texts.prompt_only", null, Locale.ENGLISH))
                .setReplyMarkup(keyboard);
    }
}
