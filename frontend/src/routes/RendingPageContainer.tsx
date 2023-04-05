import StartAction from '../components/main/StartAction/StartAction';
import TotalTransaction from '../components/main/TotalTransaction/TotalTransaction';
import FunctionInfoCards from '../components/main/functionInfoCards/FunctionInfoCards';
import Treasury from 'src/components/main/Treasury/Treasury';
import Footer from 'src/components/main/footer/Footer';


const RendingPageContainer = () => {
  return (
    <>
      <StartAction></StartAction>
      <Treasury></Treasury>
      <TotalTransaction></TotalTransaction>
      <FunctionInfoCards></FunctionInfoCards>
      <Footer></Footer>
    </>
  );
};

export default RendingPageContainer;
