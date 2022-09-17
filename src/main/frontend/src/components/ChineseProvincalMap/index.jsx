import React, { useState, useEffect } from 'react';
import { ChoroplethMap } from '@ant-design/maps';


export function ChineseProvincalMap(props){
    const [ data, setData ]  = useState([])
    useEffect(() => {
        setData(props.data)
    },[props.data])
    const config = {
        map: {
            type: 'mapbox',
            style: 'blank',
            center: [104.552500, 34.322700],
            pitch: 0,
            zoom:2,
            maxZoom:5,
            boxZoom:false,
            dragPan:false,
            dragRotate:false,
            scrollZoom:false,
            trackResize:false,
            doubleClickZoom:false,
            language:"zh"
        },
        source: {
            data: data,
            joinBy: {
                sourceField: 'code',
                geoField: 'adcode',
            },
        },
        preserveDrawingBuffer:false,
        viewLevel: {
            level: 'country',
            adcode: 100000,
        },
        autoFit: false,
        color: {
            field: 'value',
            value: ['#ffffff','#eaeefc','#d0d9f5','#bac7f2', '#708ae0','#2e57db','#234dd9','#1239ba'],
            scale: {
                type: 'quantile',
            },
        },
        style: {
            opacity: 1,
            stroke: 'rgb(93,112,146)',
            lineWidth: 0.6,
            lineOpacity: 1,
        },
        label: {
            visible: true,
            field: 'name',
            style: {
                fill: '#000',
                opacity: 0.8,
                fontSize: 10,
                stroke: '#fff',
                strokeWidth: 1.5,
                textAllowOverlap: false,
                padding: [5, 5],
                zLevel: 99
            },
        },
        state: {
            active: {
                stroke: 'black',
                lineWidth: 0.5,
            },

        },
        tooltip: {
            items: ['name', 'adcode', 'value'],
        },
        scale:false,
        roam:false
    };

    return (
        <ChoroplethMap {...config} />
    );
};

export default ChineseProvincalMap;

