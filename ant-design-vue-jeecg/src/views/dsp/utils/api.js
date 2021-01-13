import {getAction} from "../../../api/manage";

const load = function (url) {
  let params = {
    dsp_ad_format: {status: 1},
    page_no: 1,
    page_size: 100
  }
  let that = this
  let data = [];
  getAction(url, params).then((res) => {
    let list = res.result.records
    for (let a = 0; a < list.length; a++) {
      let temp = list[a];
      data.push({key: temp.id, value: temp.name});
    }
    console.log(data)
  })
  return data
}

export {
  load
}