package com.example.service;

import com.example.entity.StHeader;
import com.example.mapper.SettingMapper;
import com.example.vo.StHeaderVo;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 【名称】</br>
 * ログイン用Service
 *
 * @author eptsz01
 */
@Service
public class SettingService {

    private final Logger logger = LoggerFactory.getLogger(SettingService.class);

    @Resource
    private SettingMapper settingMapper;

    @Resource
    private DozerBeanMapper dozerBeanMapper;

    public List<StHeaderVo> getStHeaders() {

        // Loginでユーザ情報を取得します。
        List<StHeader> resultList = this.settingMapper.getStHeaders();
        List<StHeaderVo> stHeaderVoList = new ArrayList<>();
        stHeaderVoList = dozerBeanMapper.map(resultList,  stHeaderVoList.getClass());
        return stHeaderVoList;
    }

    @Transactional
    public void addStHeader(StHeaderVo stHeaderVo) {
        StHeader stHeader = dozerBeanMapper.map(stHeaderVo, StHeader.class);
        this.settingMapper.addStHeader(stHeader);
    }

    public StHeaderVo findStHeaderById(Long id){
        StHeader stHeader = this.settingMapper.findStHeaderById(id);
        StHeaderVo stHeaderVo = dozerBeanMapper.map(stHeader, StHeaderVo.class);
        return stHeaderVo;
    }

    public void updateStHeader(StHeaderVo stHeaderVo){
        StHeader stHeader = dozerBeanMapper.map(stHeaderVo, StHeader.class);
        this.settingMapper.updateStHeader(stHeader);
    }

    public void deleteStHeader(Long id){
        this.settingMapper.deleteStHeaderById(id);
    }
}
