import StartAction from '../components/main/StartAction/StartAction';
import TotalTransaction from '../components/main/TotalTransaction/TotalTransaction';
import FunctionInfoCards from '../components/main/functionInfoCards/FunctionInfoCards';
import Treasury from 'src/components/main/Treasury/Treasury';

const RendingPageContainer = () => {
  return (
    <>
      <StartAction></StartAction>
      <Treasury></Treasury>
      <TotalTransaction></TotalTransaction>
      <FunctionInfoCards></FunctionInfoCards>
    </>
  );
};

export default RendingPageContainer;
