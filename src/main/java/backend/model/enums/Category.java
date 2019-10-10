package backend.model.enums;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum Category {
    @JsonEnumDefaultValue
    All,
    Pizza,
    Cerveza,
    Hamburguesa,
    Sushi,
    Empanadas,
    Green,
    Vegano
}
