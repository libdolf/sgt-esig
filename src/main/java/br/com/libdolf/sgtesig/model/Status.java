package br.com.libdolf.sgtesig.model;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum Status implements Serializable {
    IN_PROGRESS("Em andamento"),
    COMPLETED("Completa");

    private final String description;

    Status(String description) {
        this.description = description;
    }
}