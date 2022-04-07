package com.example.dto.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
@RequiredArgsConstructor
public class Intern {
    @JsonProperty("idIntern")
    private int idIntern;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
}
