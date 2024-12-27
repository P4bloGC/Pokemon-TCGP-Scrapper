package com.tcgp.mappers;

import com.tcgp.models.Set;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SetMapper {
    void insertSet(Set set);
    Set getSetByName(String setName);
}