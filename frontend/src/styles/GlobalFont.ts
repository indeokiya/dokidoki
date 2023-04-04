import { createGlobalStyle } from "styled-components";

import WorkSansBold from "src/assets/font/WorkSans-Bold.woff";
import WorkSansSemibold from "src/assets/font/WorkSans-SemiBold.woff";

export default createGlobalStyle`
  @font-face {
    font-family: "WorkSans";
    src: local("WorkSans"), url(${WorkSansBold}) format('woff');
    font-weight: bold;
  }

  @font-face {
    font-family: "WorkSans";
    src: local("WorkSans"), url(${WorkSansSemibold}) format('woff');
    font-weight: semibold;
  }
`;