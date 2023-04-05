import StartAction from '../components/main/StartAction/StartAction';
import TotalTransaction from '../components/main/TotalTransaction/TotalTransaction';
import FunctionInfoCards from '../components/main/functionInfoCards/FunctionInfoCards';
import Treasury from 'src/components/main/Treasury/Treasury';
import Footer from 'src/components/main/footer/Footer';

import SuperRichRank from 'src/components/main/Rank/SuperRichRank';
import MostSaleProductsRank from 'src/components/main/Rank/MostSaleProductsRank';

const RendingPageContainer = () => {
  return (
    <>
      <StartAction></StartAction>
      <Treasury></Treasury>
      <SuperRichRank></SuperRichRank>
      <TotalTransaction></TotalTransaction>
      <MostSaleProductsRank></MostSaleProductsRank>
      <FunctionInfoCards></FunctionInfoCards>
      <Footer></Footer>
    </>
  );
};

export default RendingPageContainer;
