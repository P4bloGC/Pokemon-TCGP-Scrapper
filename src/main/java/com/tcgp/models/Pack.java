package com.tcgp.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pack {
    Long id;
    Integer setId;
    String packName;
  
}
