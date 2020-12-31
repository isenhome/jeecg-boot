package org.jeecg.modules.dsp.service.impl;

import org.jeecg.modules.dsp.entity.DspAdvertiser;
import org.jeecg.modules.dsp.mapper.DspAdvertiserMapper;
import org.jeecg.modules.dsp.service.IDspAdvertiserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;

/**
 * @Description: dsp_advertiser
 * @Author: jeecg-boot
 * @Date:   2020-12-30
 * @Version: V1.0
 */
@Service
public class DspAdvertiserServiceImpl extends ServiceImpl<DspAdvertiserMapper, DspAdvertiser> implements IDspAdvertiserService {

    public boolean removeById(String id){
        DspAdvertiser entity = new DspAdvertiser();
        entity.setId(id);
        entity.setStatus(-1);
        return updateById(entity);
    }

}
