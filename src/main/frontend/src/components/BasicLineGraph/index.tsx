import React, {useEffect, useState} from 'react';
import {Line} from '@ant-design/charts';

/**
 * 标准折线图组件
 * @param props
 * @constructor
 */

export interface LineData{
    xAxis:string,
    yAxis:string,
    data:any
}

const preContent:LineData = {
    xAxis:"",
    yAxis:"",
    data:[]
}

export function BaseLine(props:any){
    const [ content, setContent ]  = useState<LineData>(preContent)
    useEffect(() => {
        const inContent = props.data
        setContent(inContent)
    },[props.data])

    const xAxis = content==undefined?preContent.xAxis:content.xAxis;
    const yAxis = content==undefined?preContent.yAxis:content.yAxis;
    const graphData = content==undefined?preContent.data:content.data
    const config = {
        data:graphData,
        xField: "key",
        yField: "value",
        point: {
            size: 5,
            shape: 'diamond',
        },
        meta:{
            key:{ alias:xAxis },
            value:{ alias:yAxis }
        }
    };
    return <Line {...config} />;
};
export default BaseLine;
