package com.github.classpick.reservation.repository;

public enum ProofType {
    IN_USE,
    CLEANED,
    ;

    public String toPathname() {
        return this.name().toLowerCase().replace('_', '-');
    }
}
