package com.TopLabBazaar2909.TLBnew2909.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class Config {
    private boolean report = false;
    private boolean booking = false;
    private boolean notification = false;

    public Object getConfigReport() {
        return null;
    }
}
