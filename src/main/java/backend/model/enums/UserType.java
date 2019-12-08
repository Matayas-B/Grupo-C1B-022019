package backend.model.enums;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum UserType {
    @JsonEnumDefaultValue
    customer,
    supplier
}
