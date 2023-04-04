// @ts-nocheck
import {Chart as ChartJS, CategoryScale, LinearScale, TimeScale, PointElement, LineElement, BarElement, Title, Tooltip, Legend} from 'chart.js';
import 'chartjs-adapter-moment';
import { Line } from 'react-chartjs-2';
import { useState, useEffect } from 'react';

ChartJS.register(CategoryScale, LinearScale, TimeScale, PointElement, BarElement, LineElement, Title, Tooltip, Legend);

type Props = {
    initial_datas: Array<any>,
}

const MemberChart = ({
    initial_datas,
}: Props) => {
 
// initial_datas의 bid_infos 마지막 가격을 기준으로 sort.
if (initial_datas) {
    initial_datas.sort((a, b) => b.bid_infos[0].y - a.bid_infos[0].y)
}
console.log("initial_data >>", initial_datas)

const timeFormat = 'YYYY-MM-DDTHH:mm:ss'

const [data, setData] = useState({
    datasets: []
});

const borderColor = ['#E64D40', '#F0C13C', '#57D936'];
const backgroundColor = ['#FFAFA1', '#EBD386', '#AEFF9E'];

const updateData = () => {
    const newDatasets = [];
    for (let i = 0; i < initial_datas.length; i++) {
        let init_data = initial_datas[i];
        let dataset = {
            label: init_data.name,
            data: init_data.bid_infos,
            stepped: 'after',
            pointRadius: 2
        }
        if (i < 3) {
            dataset.borderColor = borderColor[i]
            dataset.backgroundColor = backgroundColor[i]
        }
        newDatasets.push(dataset)
    
        
    }
    setData({datasets: newDatasets})
}
useEffect(() => {
    updateData()
}, [initial_datas])


const options = {
    scales: {
        y: {
            grid: {
                // display: false
            },
            ticks: {
                // callback: function(value, index, ticks) {
                    
                //     return `${value} P`
                // }
            }
        },
        x: {
            type: 'time',
            time: {
                format: timeFormat,
            },
            grid: {
                display: false
            }
        },
    },
    plugins: {
        legend: {
            position: 'right',
            align: 'start',
            labels: {
                boxWidth: 10,
                boxHeight: 8,
            },
        }
    }

}

return <Line data={data} options={options} />
}
  export default MemberChart;