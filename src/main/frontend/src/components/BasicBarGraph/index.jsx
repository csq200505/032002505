import React, { useState, useEffect } from 'react';
import { Bar } from '@ant-design/plots';

const baseBarData = [
    {
        key: '1951 年',
        value: 38,
    },
    {
        key: '1952 年',
        value: 52,
    },
    {
        key: '1956 年',
        value: 61,
    },
    {
        key: '1957 年',
        value: 145,
    },
    {
        key: '1958 年',
        value: 48,
    },
];

export function BaseBar(props){
    const [ data, setData ] = useState(baseBarData)
    useEffect(() => {
        setData(props.data)
    },[props.data])

    const config = {
        data,
        xField: 'value',
        yField: 'key',
        seriesField: 'key',
        legend: {
            position: 'top-left',
        },
    };
    return <Bar {...config} />;
}

export default BaseBar;
