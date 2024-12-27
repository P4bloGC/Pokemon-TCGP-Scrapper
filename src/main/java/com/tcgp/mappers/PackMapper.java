package com.tcgp.mappers;

import com.tcgp.models.Pack;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PackMapper {
    @Insert("INSERT INTO packs (id, set_id, pack_name) VALUES (#{id}, #{setId}, #{packName})")
    void insertPack(Pack pack);
}