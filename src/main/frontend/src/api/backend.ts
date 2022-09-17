import {DoPost, DoGet, ApiResults} from "./service";

export function getDistricts():Promise<ApiResults>{
    return DoGet(null,"/getDistricts")
}

export function getDefiniteInSevenDays():Promise<ApiResults>{
    return DoGet(null, "/getDefiniteInSevenDays")
}

export function getIndefiniteInSevenDays():Promise<ApiResults>{
    return DoGet(null,"/getIndefiniteInSevenDays")
}

export function getRank():Promise<ApiResults>{
    return DoGet(null,"/getRank")
}

export function getNationalMapData():Promise<ApiResults>{
    return DoGet(null,"/getNationalMapData")
}

export function getDefiniteBetweenDaysAndProvince(data:any):Promise<ApiResults>{
    return DoPost(data,"/getDefiniteBetweenDaysAndProvince")
}

export function getExcelTable():Promise<ApiResults>{
    return DoGet(null,"/export")
}
