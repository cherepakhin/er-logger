package ru.domru.logger.kafka.receiver.controller.token;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static ru.domru.logger.kafka.receiver.controller.token.AttributeHelpers.findEnumJsonProperty;


public class EnumHelper<T extends Enum<T> & SearchableEnum> {
    private static final String UNKNOWN_VALUE_LOG_MESSAGE = "Can not parse {} value of {}. Unknown {} value: {}";
    private final Class<T> tClass;

    private final Logger logger;

    private Map<String, T> clientValueMap = new HashMap<>();
    private Map<String, T> nameValueMap = new HashMap<>();
    private Map<Long, T> idValueMap = new HashMap<>();

    /**
     * @param item - input class
     *             Инициализирует хелпер для енама, по основным полям и контракту
     */
    public EnumHelper(final Class<T> item) {
        this.tClass = item;
        logger = LoggerFactory.getLogger(tClass);
        String jsonVal;
        for (T eItem : tClass.getEnumConstants()) {
            if ((jsonVal = findEnumJsonProperty(eItem)) != null) {
                clientValueMap.put(jsonVal.toLowerCase(), eItem);
            }

            nameValueMap.put(eItem.name().toLowerCase(), eItem);
            idValueMap.put(eItem.getKey(), eItem);
        }
    }

    /**
     * @param text - input value
     *             поиск по всем доступным значениям енама
     * @return Енам или IllegalArgumentException
     */
    public T fromValue(String text) {
        return Stream.<Function<String, T>>of(clientValueMap::get, nameValueMap::get, this::fromKeyValue)
                .map(f -> f.apply(StringUtils.lowerCase(text)))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> {
                    doLog(StringUtils.EMPTY, text);
                    return new IllegalArgumentFormatException(tClass.getSimpleName(), text);
                });
    }

    /**
     * @param text - input value
     *             поиск по клиентскому значению полей JsonProperty
     * @return Енам или null
     */
    public T fromClientValue(String text) {
        return Optional.ofNullable(clientValueMap.get(StringUtils.lowerCase(text))).orElseGet(() -> {
            doLog("client", text);
            return null;
        });
    }

    /**
     * @param text - input value
     *             поиск по названию полей енама, не чувствителен к регистру
     * @return Енам или null
     */
    public T fromNameValue(String text) {
        return Optional.ofNullable(nameValueMap.get(StringUtils.lowerCase(text))).orElseGet(() -> {
            doLog("name", text);
            return null;
        });
    }

    /**
     * @param id - input value
     *           поиск по значению getKey, определенному в Searchable enum
     * @return Енам или null
     */
    public T fromKeyValue(Long id) {
        return Optional.ofNullable(idValueMap.get(id)).orElseGet(() -> {
            doLog("id", id);
            return null;
        });
    }

    /**
     * @param text - input value
     *             поиск по значению getKey, определенному в Searchable enum
     * @return Енам или null
     */
    private T fromKeyValue(String text) {
        try {
            return idValueMap.get(Long.parseLong(text));
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    /**
     * Обертка для логгирования
     */
    private void doLog(String fieldName, Object value) {
        logger.info(UNKNOWN_VALUE_LOG_MESSAGE, tClass.getSimpleName(), fieldName, fieldName, value);
    }
}
