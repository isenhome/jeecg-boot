package org.jeecg.modules.dsp.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DspTree implements Serializable {

    private static final long serialVersionUID = 1L;

    private Object id;
    private String name;
    private Object key;
    private Object parentId;
    private boolean isLeaf;
    private String title;
    private String slotTitle;
    private List<DspTree> children;
    private Object value;

    public DspTree(DspIndustry dspIndustry){
        this.key = dspIndustry.getId();
        this.id =  dspIndustry.getId();
        this.parentId = dspIndustry.getParentId();
        this.title = dspIndustry.getName();
        this.name =  dspIndustry.getName();
        this.slotTitle = dspIndustry.getName();
        this.isLeaf = true;
        this.value = dspIndustry.getId();
    }

    public DspTree(DspInterest dspInterest){
        this.key = dspInterest.getId();
        this.id =  dspInterest.getId();
        this.parentId = dspInterest.getParentId();
        this.title = dspInterest.getName();
        this.name =  dspInterest.getName();
        this.slotTitle = dspInterest.getName();
        this.isLeaf = true;
        this.value = dspInterest.getId();
    }

}
