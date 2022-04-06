package com.example.dto.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Intern {
    @JsonProperty("Id_Inter")
    private int idInter;
    @JsonProperty("First_Name")
    private String firstName;
    @JsonProperty("Last_Name")
    private String lastName;
}
