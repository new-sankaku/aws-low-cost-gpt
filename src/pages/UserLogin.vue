<template>
  <div class="container login">
    <div
      class="q-pa-sm logo"
      style="
        margin-bottom: 0px;
        display: flex;
        align-items: center;
        justify-content: center;
      "
    >
      <img
        width="75"
        height="75"
        src="../assets/logo.svg"
        alt="Logo"
        class="logo"
      />
      <label>Low cost AI</label>
    </div>
    <form @submit.prevent="login">
      <div class="form-group">
        <label for="inputEmail">Mail Address</label>
        <q-input
          filled
          v-model="inputEmail"
          id="inputEmail"
          type="email"
          placeholder="email@example.com"
          class="custom-input"
        >
          <template v-slot:prepend>
            <q-icon name="mail_outline" />
          </template>
        </q-input>
      </div>
      <div class="form-group">
        <label for="inputPassword">Password</label>
        <q-input
          filled
          label="Uppe/Lower Char,Num,Special,Min12"
          v-model="inputPassword"
          id="inputPassword"
          type="password"
          placeholder="●●●●●●"
          class="custom-input"
        >
          <template v-slot:prepend>
            <q-icon name="lock_outline" />
          </template>
        </q-input>
      </div>
      <q-btn label="Login" type="submit" color="primary" class="full-width" />
    </form>
    <div class="language-select" style="margin-top: 70px">
      <q-select
        v-model="locale"
        :options="localeOptions"
        label="Language"
        dense
        filled
        borderless
        emit-value
        map-options
        options-dense
        @update:modelValue="changeLocale"
        style="min-width: 150px"
      >
        <template v-slot:prepend>
          <q-icon name="language" />
        </template>
      </q-select>
    </div>
  </div>
</template>

<script>
import { useRouter } from "vue-router";
import { ref, watch } from "vue";
import { useI18n } from "vue-i18n";
import {
  CognitoUserPool,
  CognitoUser,
  AuthenticationDetails,
} from "amazon-cognito-identity-js";

export default {
  name: "UserLogin",
  setup() {
    const router = useRouter();
    const { locale } = useI18n({ useScope: "global" });
    const inputEmail = ref("");
    const inputPassword = ref("");
    const selectedLocale = ref(
      findMatchingLocale(navigator.language || "en-US")
    );
    const localeOptions = [
      { value: "en-US", label: "English" },
      { value: "ja-JP", label: "日本語" },
    ];

    watch(selectedLocale, (newLocale) => {
      locale.value = newLocale;
    });

    const changeLocale = (newLocale) => {
      selectedLocale.value = newLocale;
      locale.value = newLocale;
    };
    changeLocale(selectedLocale.value);

    const login = async () => {
      var pool = process.env.VUE_APP_POOL_ID;
      var client = process.env.VUE_APP_CLIENT_ID;

      console.log("selectedLocale:", selectedLocale.value);

      if (pool == "COGNITO_POOL_ID") {
        alert($t("userlogin_system_error_not_set_pool_id"));
        return;
      }
      if (client == "COGNITO_CLIENT_ID") {
        alert($t("userlogin_system_error_not_set_client_id"));
        return;
      }

      var poolData = {
        UserPoolId: process.env.VUE_APP_POOL_ID,
        ClientId: process.env.VUE_APP_CLIENT_ID,
      };
      var userPool = new CognitoUserPool(poolData);

      //cognitoパラメータ設定
      var userMailAddress = inputEmail.value;
      var password = inputPassword.value;

      var authenticationData = {
        Username: userMailAddress,
        Password: password,
      };
      var authenticationDetails = new AuthenticationDetails(authenticationData);

      var userData = {
        Username: userMailAddress,
        Pool: userPool,
      };

      var cognitoUser = new CognitoUser(userData);
      cognitoUser.authenticateUser(authenticationDetails, {
        onSuccess: function (result) {
          console.log("authentication onSuccess");
          localStorage.setItem("user-token", result.getIdToken().getJwtToken());
          localStorage.setItem("user-mail-address", userMailAddress);
          router.push("/UserChat").catch((err) => console.error(err));
        },
        onFailure: function (err) {
          console.log("authentication onFailure");
          alert(err.message || JSON.stringify(err));
        },
      });
    };

    return {
      inputEmail,
      inputPassword,
      login,
      locale: selectedLocale,
      localeOptions,
      changeLocale,
    };
  },
};

function findMatchingLocale(userLocale) {
  const availableLocales = ["en-US", "ja-JP"];
  if (availableLocales.includes(userLocale)) {
    return userLocale;
  }
  const languageOnly = userLocale.split("-")[0];
  return (
    availableLocales.find((locale) => locale.startsWith(languageOnly)) ||
    "en-US"
  );
}
</script>

<style>
.container.login {
  max-width: 400px;
  margin: auto;
}

.full-width {
  width: 100%;
}

.form-group {
  margin-bottom: 1rem;
}

.language-select {
  margin-top: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
  color: #555;
}
.container.login {
  max-width: 400px;
  margin: auto;
}

.full-width {
  width: 100%;
}

.form-group {
  margin-bottom: 1rem;
}

.language-select {
  margin-top: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
  color: #555;
}
.logo {
  text-align: center;
  margin-bottom: 2rem;
}
.forgot-password {
  margin-bottom: 1rem;
  text-align: right;
}
</style>
