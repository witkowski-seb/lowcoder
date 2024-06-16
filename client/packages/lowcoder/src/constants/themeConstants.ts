import { ThemeDetail } from "@lowcoder-ee/api/commonSettingApi";

const text = {
  style: {
    borderWidth: 0,
  }
};

const input = {
  style: {
    borderWidth: '0px',
    background: 'transparent',
  },
  labelStyle: {
    borderWidth: '0px',
  },
  inputFieldStyle: {
    borderWidth: '1px',
    border: '#D7D9E0'
  }
};

export const defaultTheme: ThemeDetail = {
  primary: "#3377FF",
  textDark: "#222222",
  textLight: "#FFFFFF",
  canvas: "#F5F5F6",
  primarySurface: "#FFFFFF",
  border: "#D7D9E0",
  radius: "4px",
  borderWidth: "1px",
  borderStyle: "solid",
  margin: "3px",
  padding: "3px",
  gridColumns: "24",
  textSize: "14px",
  animation: "",
  animationDelay: "",
  animationDuration: "",
  opacity: "1",
  boxShadow: "",
  boxShadowColor: "",
  animationIterationCount: "",
  components: {
    text,
    input,
  },
};
