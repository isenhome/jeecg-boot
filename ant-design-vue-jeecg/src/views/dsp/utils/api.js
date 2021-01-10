import {getAction} from "../../../api/manage";

const loadAdFormat=function (){
  let params = {
    dsp_ad_format:{status:1},
    page_no:1,
    page_size:100
  }
  getAction("/dsp/DspAdFormat/list",params).then((res)=>{
    that.adformatDatasource = [];
    let treeList = res.result.records
    for (let a = 0; a < treeList.length; a++) {
      let temp = treeList[a];
      temp.isLeaf = temp.leaf;
      that.adformatDatasource.push(temp);
    }
  })
}

export {
  loadAdFormat
}