import { Select } from 'antd';
import React, {useState} from 'react';
import {getDistricts} from "../../api/backend";

const { Option } = Select;


const onSearch = (value: string) => {
    console.log('search:', value);
};

export interface dataToSelect{
    code:number,
    name:string
}
let dataGroup:dataToSelect[] = []

export function BaseSelecter(props:any){
    const { setProvince } = props;
    const [ list, setList ] = useState<dataToSelect[]>([]);
    const [ state, setState ] = useState<number>()

    async function fetchDistricts(){
        dataGroup =  (await getDistricts()).resp.data;
        setList(dataGroup);
    }

    const onChange = (value: number) => {
        console.log(`selected ${value}`);
        setProvince(value)
    };

    return (
        <Select
            showSearch
            placeholder="请输入省份"
            optionFilterProp="children"
            onChange={onChange}
            onClick={fetchDistricts}
            onSearch={onSearch}
            filterOption={(input, option) =>
                (option!.children as unknown as string).toLowerCase().includes(input.toLowerCase())
            }
            allowClear={true}
            dropdownStyle={{background: "white"}}
            size={"small"}
        >{
            list.map((value) => {
                return <Option value={value.code}>{value.name}</Option>
            })
        }
        </Select>
    );
}

export default BaseSelecter;
