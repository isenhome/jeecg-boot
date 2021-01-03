package org.jeecg.modules.dsp.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class DspIndustryTree implements Serializable {

    private static final long serialVersionUID = 1L;

    private String key;
    private String parentId;
    private boolean isLeaf;
    private String title;
    private String slotTitle;
    private List<DspIndustryTree> children;
    private String value;

    public DspIndustryTree(DspIndustry dspIndustry){
        this.key = dspIndustry.getId();
        this.parentId = dspIndustry.getParentId();
        this.title = dspIndustry.getName();
        this.slotTitle = dspIndustry.getName();
        this.children = new ArrayList<>();
        this.isLeaf = false;
        this.value = dspIndustry.getId();
    }

}
