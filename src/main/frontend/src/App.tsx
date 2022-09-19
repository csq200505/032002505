import React, {useEffect, useState} from 'react';
import { Button } from 'antd';
import { DownloadOutlined } from '@ant-design/icons'
import './App.css';
import BasicLineGraph from "./components/BasicLineGraph";
import ChineseProvincalMap from "./components/ChineseProvincalMap";
import BasicBarGraph from "./components/BasicBarGraph";
import RightBottomBox from "./pages/RightBottomBox";
import {
    getDefiniteInSevenDays,
    getIndefiniteInSevenDays,
    getNationalMapData,
    getRank,
    getExcelTable
} from "./api/backend";


function App() {
    const [ definiteData, setDefiniteData ] = useState({xAxis: '时间', yAxis: '人数', data: []});
    const [ inDefiniteData, setInDefiniteData ] = useState({xAxis: '时间', yAxis: '人数', data: []});
    const [ rank, setRank ] = useState([]);
    const [ map, setMap ] = useState([])

    useEffect( () => {
        const fetchData1 = async() => {
            return await getDefiniteInSevenDays()
        }
        const fetchData2 = async() => {
            return await getIndefiniteInSevenDays()
        }
        const fetchData3 = async() => {
            return await getRank()
        }
        const fetchData4 = async() => {
            return await getNationalMapData()
        }
        fetchData1().then((data) => setDefiniteData({xAxis: '时间', yAxis: '人数', data:data.resp.data}))
        fetchData2().then((data) => setInDefiniteData({xAxis: '时间', yAxis: '人数', data: data.resp.data})
        )
        fetchData3().then((data) => setRank(data.resp.data));
        fetchData4().then((data)=> setMap(data.resp.data))
    },[]);

    async function getTable(){
        await getExcelTable()
    }

    return (
        <div className="App">
           <div className="container">
                <p>今日全国疫情数据</p>
                <ChineseProvincalMap data = {map}/>
            </div>
            <div className="basic-change-in-seven-days">
                <p>七日内内地新增确诊数量变化</p>
                <BasicLineGraph data = {definiteData}/>
            </div>
            <div className="total-change-in-seven-days">
                <p>七日内内地新增无症状数量变化</p>
                <BasicLineGraph data ={inDefiniteData}/>
            </div>
            <div className="graph-for-provinces">
            </div>
            <div className="headLine">
                <h1 style={{color:"white"}}>欢迎来到数据大屏！</h1>
                <Button
                        icon={<DownloadOutlined style ={{marginRight:"2px"}}/>}
                        size={"large"}
                        shape="round"
                    style = {{
                        backgroundColor:"blue",
                        color:"white"
                    }}
                ><a href="http://localhost:8100/export" style={{color:"white"}} >点按获取疫情数据excel表</a></Button>

            </div>
            <div className="bottomLeftRank">
                <p>七日内内地新增确诊前10名</p>
                <BasicBarGraph data = {rank}/>
            </div>
            <RightBottomBox/>
        </div>
    );

}
export default App;
