package hu.idne.backend.models.system.enums;


import lombok.Getter;

public enum DocType {
    POST_PHOTOS("postphotos");


    @Getter
    private final String value;

    DocType(String value) {
        this.value = value;
    }
}
