package backend.model.enums;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum Category {
    @JsonEnumDefaultValue
    ALL,
    Pizza,
    Cerveza,
    Hamburguesa,
    Sushi,
    Empanadas,
    Green,
    Vegano
}
