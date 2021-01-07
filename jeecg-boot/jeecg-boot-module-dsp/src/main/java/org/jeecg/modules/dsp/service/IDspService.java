package org.jeecg.modules.dsp.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.dsp.entity.DspEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IDspService<T extends DspEntity> extends IService<T> {
    default Map<String, String> getNameMap(Wrapper<T> wrapper) {
        Map<String, String> map = new HashMap<>();
        List<T> list = this.list(wrapper);
        for (T item : list) {
            map.put(item.getId(), item.getName());
        }
        return map;
    }
}
