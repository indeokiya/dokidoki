// @ts-nocheck
import {Chart as ChartJS, CategoryScale, LinearScale, TimeScale, PointElement, LineElement, BarElement, Title, Tooltip, Legend} from 'chart.js';
import 'chartjs-adapter-moment';
import { Line } from 'react-chartjs-2';
import { useState, useEffect } from 'react';
import './ChartFont.css'



ChartJS.register(CategoryScale, LinearScale, TimeScale, PointElement, BarElement, LineElement, Title, Tooltip, Legend);
ChartJS.defaults.font.family = 'Noto Sans'
// ChartJS.defaults.borderColor = '#D3D3D3'
// ChartJS.defaults.borderColor = '#808080'

type Props = {
    initial_datas: Array<any>,
}

const MemberChart = ({
    initial_datas,
}: Props) => {
 
// initial_datas의 bid_infos 마지막 가격을 기준으로 sort.
let display_datas = []

if (initial_datas) {
    display_datas = [...initial_datas]
    display_datas.sort((a, b) => b.bid_infos[0].y - a.bid_infos[0].y)
}

console.log("initial_datas >> ", initial_datas)
console.log("display_datas >>", display_datas)

const timeFormat = 'YYYY-MM-DDTHH:mm:ss'

const [data, setData] = useState({
    datasets: []
});

const lineBorderColor = ['rgba(255, 0, 0, 0.5)', 'rgba(240, 193, 60, 0.5)', 'rgba(87, 217, 54, 0.5)'];
// const borderColor = ['#E64D40', '#F0C13C', '#57D936'];
const lineBackgroundColor = ['rgba(255, 0, 0, 0.1)', 'rgba(240, 193, 60, 0.1)', 'rgba(87, 217, 54, 0.1)'];

const updateData = () => {
    const newDatasets = [];
    for (let i = 0; i < display_datas.length; i++) {
        let init_data = display_datas[i];
        let dataset = {
            label: init_data.name,
            data: init_data.bid_infos,
            stepped: 'after',
            pointRadius: 3
        }
        if (i < 3) {
            dataset.borderColor = lineBorderColor[i]
            dataset.backgroundColor = lineBackgroundColor[i]
        }
        newDatasets.push(dataset)
    
        
    }
    setData({datasets: newDatasets})
}
useEffect(() => {
    updateData()
}, [initial_datas])

const fontSize = 15;
const borderWidth = 4;
const borderColor = '#D3D3D3'
// const borderColor = '#808080'

const options = {
    scales: {
        y: {
            grid: {
                lineWidth: 2,
                color: borderColor
                // display: false
            },
            ticks: {
                font: {
                    size: fontSize
                },
                callback: function(value, index, ticks) {                    
                    return  value?.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ',') + ' 원';
                }
            },
            border: {
                width: borderWidth,
                color: borderColor
            },
        },
        x: {
            type: 'time',
            time: {
                format: timeFormat,
                displayFormats: {
                    // millisecond: 'HH:mm:ss',
                    // second: 'HH:mm:ss',
                    // minute: 'HH:mm:ss',
                    // hour: 'MM/DD HH'
                },
                // unit: 'second'
            },
            grid: {
                display: false,
            },
            ticks: {
                font: {
                    size: fontSize
                }
            },
            border: {
                width: borderWidth,
                color: borderColor
            }
        },
    },
    plugins: {
        legend: {
            position: 'right',
            align: 'start',
            labels: {
                boxWidth: 10,
                boxHeight: 10,
                font: {
                    size: fontSize + 4,
                    weight: 'bold'
                }
            },
        }
    }

}

return <Line data={data} options={options} />
}
  export default MemberChart;