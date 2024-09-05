package vukv.bordercrossingbe.domain.entities.border;

import lombok.Getter;

@Getter
public enum Country {

    SRB("Serbia"),
    CRO("Croatia"),
    MNE("Montenegro"),
    HUN("Hungary");

    private final String name;

    Country(String name) {
        this.name = name;
    }

}
