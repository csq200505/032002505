import { DatePicker } from 'antd';
import type { Moment } from 'moment';
import React, {useEffect, useState} from 'react';

const { RangePicker } = DatePicker;

type RangeValue = [Moment | null, Moment | null] | null;

export function TimePicker(props:any){
    const { setStartTime, setEndTime } = props;
    const [dates, setDates] = useState<RangeValue>(null);
    const [hackValue, setHackValue] = useState<RangeValue>(null);
    const [value, setValue] = useState<RangeValue>(null);

    const disabledDate = (current: Moment) => {
        if (!dates) {
            return false;
        }
        const tooLate = dates[0] && current.diff(dates[0], 'days') > 7;
        const tooEarly = dates[1] && dates[1].diff(current, 'days') > 7;
        return !!tooEarly || !!tooLate;
    };

    const onOpenChange = (open: boolean) => {
        if (open) {
            setHackValue([null, null]);
            setDates([null, null]);
        } else {
            setHackValue(null);
        }
    };

    const onChange = (val:RangeValue) => {
        console.log(val)
        console.log(value)
        setValue(val)
        if(val!=null){
            setStartTime(val[0])
            setEndTime(val[1])
        }

    }

    return (
        <RangePicker
            value={hackValue || value}
            disabledDate={disabledDate}
            onCalendarChange={val => setDates(val)}
            onChange={onChange}
            onOpenChange={onOpenChange}
            placeholder={["请选择开始日期","请选择结束日期"]}
            size={"small"}
        />
    );
};

export default TimePicker;
