import TimePicker from "../../components/DatePicker";
import BaseSelecter from "../../components/BaseSelecter";
import {Button, notification} from "antd";
import React, {useEffect, useState} from "react";
import BasicLineGraph from "../../components/BasicLineGraph";
import 'antd/dist/antd.css';
import {getDefiniteBetweenDaysAndProvince} from "../../api/backend";


export default function RightBottomBox(){

    const size = 'small'
    const [ startTime, setStartTime ]= useState();
    const [ endTime, setEndTime ] = useState();
    const [ province, setProvince ] = useState<number>();
    const [ data, setData ] = useState({xAxis: '时间', yAxis: '人数', data: []});


    useEffect(()=> {
        console.log("开始时间："+startTime)
        console.log("结束时间: "+endTime)
        console.log("省份码："+ data)
    },[])


    async function doRequest(){
        getDefiniteBetweenDaysAndProvince({startTime:startTime, endTime:endTime, code:province})
            .then((result) => {
                setData({xAxis: '时间', yAxis: '人数', data: result.resp.data})
            })
    }

    function onRequest(){
        if(startTime!=null&&endTime!=null&&province!=null){
            if(province==710000||province==810000||province==820000){
                notification["warning"]({
                    message:"提示",
                    description:"港澳台数据暂不支持查询！"
                })
            }else{
                doRequest()
            }

        } else{
            notification["error"]({
                message:"警告",
                description:"请将搜索条件补充完整！"
            })
        }
    }

    return (
        <div className = "bottomRightBlock">
            <div className= "bottomRightHeadline">
                <TimePicker setStartTime = {setStartTime} setEndTime = {setEndTime}/>
                <BaseSelecter setProvince = {setProvince}/>
                    <Button type="primary"
                            size = {size}
                            style={{height:"50%"}}
                            onClick={onRequest}
                    >提交</Button>
            </div>
            <div className= "bottomRightGraph">
                <BasicLineGraph data = {data}/>
            </div>
        </div>
    )

}
