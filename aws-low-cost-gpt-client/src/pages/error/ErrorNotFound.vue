<template>
  <div class="q-ma-lg inner-container">
    <div class="glitch-wrapper">
      <span data-text="E" class="text buzz err-code">E</span>
      <span data-text="R" class="text buzz err-code">R</span>
      <span data-text="R" class="text buzz err-code">R</span>
      <span data-text="O" class="text buzz err-code red">O</span>
      <span data-text="R" class="text buzz err-code">R</span>
    </div>
    <div data-text="FORBIDDEN" class="buzz text placeholder-title">
      Unknown error.
    </div>
    <div data-text="FORBIDDEN" class="buzz text placeholder-title">
      なんかエラーです。
    </div>
    <br />
    <div data-text="_" class="text buzz blip">_</div>
  </div>
</template>

<script>
export default {
  mounted() {
    this.initialize();
  },
  methods: {
    initialize() {},
  },
};
</script>

<style>
@import url("https://fonts.googleapis.com/css?family=Orbitron");
body {
  font-family: Sans-serif;
  background: linear-gradient(45deg, #222529 0%, #191519 100%);
}

.inner-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  text-align: center;
}

.glitch-wrapper,
.placeholder-title,
.blip {
  display: inline-block;
}

body:before {
  content: " ";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  border-style: solid;
  border-width: 72px 72px 72px 72px;
  border-color: rgba(0, 0, 0, 0.4) rgba(0, 0, 0, 0.2) rgba(0, 0, 0, 0)
    rgba(0, 0, 0, 0.2);
}

.text {
  color: #36ba2c;
  font-size: 24px;
  padding: 0 4px;
  display: inline-block;
  text-shadow: 0px 0px 8px #79ba48;
}

.container {
  background: #222;
  background: radial-gradient(ellipse at center, #2e3d26 0%, #040805 100%);
  background-attachment: fixed;
  position: absolute;
  top: 64px;
  left: 64px;
  right: 64px;
  bottom: 64px;
  padding: 64px;
  border-radius: 24px;
  overflow: hidden;
}

.container:before {
  display: block;
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 0;
}

.container:after {
  display: block;
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 0;
  box-shadow: inset 0px 0px 32px 16px rgba(0, 0, 0, 0.4);
  z-index: 2;
  border-radius: 24px;
}

.inner-container:after {
  position: absolute;
  content: "";
  display: block;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background: url(http://i.imgur.com/VXcVUTX.png);
  border-radius: 24px;
}

.err-code {
  font-family: "Orbitron";
  font-weight: bold;
  font-size: 128px;
  padding: 0 4px;
}

.buzz {
  color: #36ba2c;
  position: relative;
}

@keyframes blink {
  0% {
    opacity: 0;
  }
  40% {
    opacity: 0;
  }
  45% {
    opacity: 1;
  }
  100% {
    opacity: 1;
  }
}
.blip {
  animation: blink 1.5s steps(5, start) infinite;
}

@keyframes noise-anim {
  0% {
    clip: rect(27px, 9999px, 182px, 0);
  }
  3.3333333333% {
    clip: rect(14px, 9999px, 162px, 0);
  }
  6.6666666667% {
    clip: rect(61px, 9999px, 145px, 0);
  }
  10% {
    clip: rect(83px, 9999px, 189px, 0);
  }
  13.3333333333% {
    clip: rect(52px, 9999px, 90px, 0);
  }
  16.6666666667% {
    clip: rect(47px, 9999px, 52px, 0);
  }
  20% {
    clip: rect(28px, 9999px, 59px, 0);
  }
  23.3333333333% {
    clip: rect(81px, 9999px, 34px, 0);
  }
  26.6666666667% {
    clip: rect(21px, 9999px, 192px, 0);
  }
  30% {
    clip: rect(49px, 9999px, 24px, 0);
  }
  33.3333333333% {
    clip: rect(53px, 9999px, 103px, 0);
  }
  36.6666666667% {
    clip: rect(83px, 9999px, 146px, 0);
  }
  40% {
    clip: rect(50px, 9999px, 183px, 0);
  }
  43.3333333333% {
    clip: rect(81px, 9999px, 57px, 0);
  }
  46.6666666667% {
    clip: rect(20px, 9999px, 87px, 0);
  }
  50% {
    clip: rect(99px, 9999px, 192px, 0);
  }
  53.3333333333% {
    clip: rect(58px, 9999px, 14px, 0);
  }
  56.6666666667% {
    clip: rect(94px, 9999px, 91px, 0);
  }
  60% {
    clip: rect(27px, 9999px, 198px, 0);
  }
  63.3333333333% {
    clip: rect(18px, 9999px, 52px, 0);
  }
  66.6666666667% {
    clip: rect(10px, 9999px, 188px, 0);
  }
  70% {
    clip: rect(50px, 9999px, 138px, 0);
  }
  73.3333333333% {
    clip: rect(92px, 9999px, 41px, 0);
  }
  76.6666666667% {
    clip: rect(43px, 9999px, 178px, 0);
  }
  80% {
    clip: rect(100px, 9999px, 162px, 0);
  }
  83.3333333333% {
    clip: rect(98px, 9999px, 128px, 0);
  }
  86.6666666667% {
    clip: rect(14px, 9999px, 31px, 0);
  }
  90% {
    clip: rect(45px, 9999px, 103px, 0);
  }
  93.3333333333% {
    clip: rect(29px, 9999px, 193px, 0);
  }
  96.6666666667% {
    clip: rect(39px, 9999px, 151px, 0);
  }
  100% {
    clip: rect(83px, 9999px, 101px, 0);
  }
}
.buzz:after {
  content: attr(data-text);
  position: absolute;
  left: 4px;
  top: 0;
  background: radial-gradient(ellipse at center, #2e3d26 0%, #040805 100%);
  background-attachment: fixed;
  animation: noise-anim 2s infinite linear alternate-reverse;
}

@keyframes noise-anim-2 {
  0% {
    clip: rect(26px, 9999px, 6px, 0);
  }
  3.3333333333% {
    clip: rect(3px, 9999px, 54px, 0);
  }
  6.6666666667% {
    clip: rect(40px, 9999px, 120px, 0);
  }
  10% {
    clip: rect(39px, 9999px, 136px, 0);
  }
  13.3333333333% {
    clip: rect(39px, 9999px, 149px, 0);
  }
  16.6666666667% {
    clip: rect(75px, 9999px, 51px, 0);
  }
  20% {
    clip: rect(77px, 9999px, 92px, 0);
  }
  23.3333333333% {
    clip: rect(76px, 9999px, 115px, 0);
  }
  26.6666666667% {
    clip: rect(19px, 9999px, 38px, 0);
  }
  30% {
    clip: rect(5px, 9999px, 146px, 0);
  }
  33.3333333333% {
    clip: rect(20px, 9999px, 65px, 0);
  }
  36.6666666667% {
    clip: rect(87px, 9999px, 145px, 0);
  }
  40% {
    clip: rect(50px, 9999px, 32px, 0);
  }
  43.3333333333% {
    clip: rect(19px, 9999px, 46px, 0);
  }
  46.6666666667% {
    clip: rect(83px, 9999px, 89px, 0);
  }
  50% {
    clip: rect(76px, 9999px, 51px, 0);
  }
  53.3333333333% {
    clip: rect(85px, 9999px, 68px, 0);
  }
  56.6666666667% {
    clip: rect(23px, 9999px, 120px, 0);
  }
  60% {
    clip: rect(83px, 9999px, 126px, 0);
  }
  63.3333333333% {
    clip: rect(17px, 9999px, 122px, 0);
  }
  66.6666666667% {
    clip: rect(70px, 9999px, 118px, 0);
  }
  70% {
    clip: rect(64px, 9999px, 124px, 0);
  }
  73.3333333333% {
    clip: rect(62px, 9999px, 18px, 0);
  }
  76.6666666667% {
    clip: rect(33px, 9999px, 63px, 0);
  }
  80% {
    clip: rect(24px, 9999px, 80px, 0);
  }
  83.3333333333% {
    clip: rect(64px, 9999px, 55px, 0);
  }
  86.6666666667% {
    clip: rect(27px, 9999px, 10px, 0);
  }
  90% {
    clip: rect(65px, 9999px, 83px, 0);
  }
  93.3333333333% {
    clip: rect(24px, 9999px, 132px, 0);
  }
  96.6666666667% {
    clip: rect(38px, 9999px, 48px, 0);
  }
  100% {
    clip: rect(86px, 9999px, 108px, 0);
  }
}
.buzz:before {
  content: attr(data-text);
  position: absolute;
  left: -3px;
  top: 0;
  padding: 0 4px;
  background: radial-gradient(ellipse at center, #2e3d26 0%, #040805 100%);
  background-attachment: fixed;
  animation: noise-anim-2 3s infinite linear alternate-reverse;
}
</style>
