package com.abranlezama.searchautocomplete.queryaggregation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyEntry {
    private int queryCount;
    private LocalDate weekOf;
}
