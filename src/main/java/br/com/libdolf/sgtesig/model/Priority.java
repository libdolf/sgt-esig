package br.com.libdolf.sgtesig.model;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum Priority implements Serializable {
    HIGH("Alta"),
    MEDIUM("Média"),
    LOW("Baixa");

    private final String description;

    Priority(String description) {
        this.description = description;
    }
}