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
initial_datas.sort((a, b) => b.bid_infos[0].y - a.bid_infos[0].y)
console.log("initial_data >>", initial_datas)

const timeFormat = 'YYYY-MM-DDTHH:mm:ss'

const [data, setData] = useState({
    datasets: []
});

const borderColor = ['#EDFFAB', '#92C9B1', 'A9FDAC'];

for (let i = 0; i < initial_datas.length; i++) {
    let init_data = initial_datas[i];
    let dataset = {
        label: init_data.name,
        data: init_data.bid_infos,
        stepped: 'after'
    }
    if (i < 3) {
        dataset.borderColor = borderColor[i]
    }

    data.datasets.push(dataset)
}

const options = {
    scales: {
        y: {

        },
        x: {
            type: 'time',
            time: {
                format: timeFormat,
            }
        }
    }

}

return <Line data={data} options={options} />
}
  export default MemberChart;