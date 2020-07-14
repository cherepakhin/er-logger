package ru.domru.logger.kafka.receiver.controller.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AttributeHelpers {
    /**
     * Disables default constructor.
     */
    private AttributeHelpers() {
    }

    /**
     * Returns JSON name of property annotated with {@link JsonProperty}.
     *
     * @param value Enum property to find JSON name
     * @return JSON name of the property or <code>null</code> if property is not found or
     * it isn't annotated with {@link JsonProperty}
     */
    @Nullable
    public static String findEnumJsonProperty(@NotNull Enum<?> value) {
        try {
            JsonProperty jsonPropertyAnnotation = value
                    .getClass()
                    .getField(value.name())
                    .getAnnotation(JsonProperty.class);

            if (jsonPropertyAnnotation == null) {
                return null;
            }

            return jsonPropertyAnnotation.value();
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    /**
     * Returns instance of enum {@see T} from string value.
     * <p>
     * Property search priority:
     * <ul>
     * <li>Property annotated with {@link JsonProperty} with name == <code>value</code></li>
     * <li>Property with name == <code>value</code></li>
     * <li>Property by key (only for {@link SearchableEnum} instances and numeric <code>value</code>)</li>
     * </ul>
     * </p>
     * <p>
     * All checks are case-insensitive!
     * </p>
     *
     * @param value The value to search property in the enum
     * @param type  The type of the enum
     * @param <T>   The type argument of the enum
     * @return Instance of enum <code>T</code> with matched <code>value</code>
     * @throws IllegalArgumentFormatException Thrown when instance of enum for specified <code>value</code> is not found
     */
    @NotNull
    public static <T extends Enum<T>> T fromValue(@NotNull String value, @NotNull Class<T> type) {
        // TODO: all of those is very slow...

        T[] values = type.getEnumConstants();
        for (T t : values) {
            if (value.equalsIgnoreCase(findEnumJsonProperty(t))) {
                return t;
            }
        }

        for (T t : values) {
            if (value.equalsIgnoreCase(t.toString())) {
                return t;
            }
        }

        // Check if it's a number for SearchableEnum instance
        if (SearchableEnum.class.isAssignableFrom(type)) {
            try {
                int numberValue = Integer.parseInt(value);
                for (T t : values) {
                    if (((SearchableEnum) t).getKey() == numberValue) {
                        return t;
                    }
                }
            } catch (NumberFormatException ignored) {
                // Do nothing
            }
        }

        // TODO: business-oriented exception
        throw new IllegalArgumentFormatException(type.getSimpleName(), value);
    }
}
