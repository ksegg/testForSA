package com.example.mapper;

import com.example.entity.StHeader;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 【名称】</br>
 * ログインMapper
 * 
 * @author eptsz01
 *
 */
@Mapper
public interface SettingMapper {

    /**
     * Get Global Headers
     * @return
     */
    public List<StHeader> getStHeaders();

    public void addStHeader(StHeader stHeader);

    public StHeader findStHeaderById(Long id);

    public void updateStHeader(StHeader stHeader);

    public void deleteStHeaderById(Long id);
}
