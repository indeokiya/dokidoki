import {useState, useCallback} from'react'
export default function App() {
  const [valueX, setValueX] =  useState(0)

  const onClick = useCallback(() => {
    const val = valueX; //초기값인 0이 들어간다. 
    console.log(val);
  },[])


  return (
    <div className="App"  >
      <button onClick={onClick}>valueX 출력하기 </button>
      <button onClick={()=>{ setValueX(pre => pre+1) }}> valueX+1 : {valueX}</button>
      <h1>valueX : {valueX}</h1>
    </div>
  );
}