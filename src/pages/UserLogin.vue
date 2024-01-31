<template>
  <div class="container login q-pa-md q-gutter-sm">
    <form>
      <div class="q-mb-md">
        <label for="inputEmail">Mail Address</label>
        <q-input filled v-model="inputEmail" id="inputEmail" type="email" placeholder="email@example.com" class="custom-input">
          <template v-slot:prepend>
            <q-icon name="mail_outline" />
          </template>
        </q-input>
      </div>
      <div class="q-mb-md">
        <label for="inputPassword">Password</label>
        <q-input filled v-model="inputPassword" id="inputPassword" type="password" placeholder="●●●●●●" class="custom-input">
          <template v-slot:prepend>
            <q-icon name="lock_outline" />
          </template>
        </q-input>
      </div>
      <q-btn @click.prevent="login" label="Login" color="primary" class="full-width custom-button"></q-btn>
    </form>
    <br>
    <q-select
	    v-model="locale"
	    :options="localeOptions"
	    label="Quasar Language"
	    dense
	    borderless
	    emit-value
	    map-options
	    options-dense
	    @input="changeLocale"
	    style="min-width: 150px"
	/>
  </div>
  
  
</template>


<script>

import { useRouter } from 'vue-router'
import { ref } from 'vue'
import { useI18n } from 'vue-i18n'

import {
  CognitoUserPool,
  CognitoUser,
  AuthenticationDetails
} from 'amazon-cognito-identity-js'

export default {
  name: 'UserLogin',
  methods: {
    changeLocale() {
      // 選択したロケールに基づいてi18nのロケールを変更
      this.$q.i18n.setLocale(this.selectedLocale);
    },
  },
  setup() {
    const { locale } = useI18n({ useScope: 'global' });
    
    const router = useRouter()
    console.log('router:', router);
    const inputEmail = ref('')
    const inputPassword = ref('')
    const login = async () => {
    
    console.log('UserPoolId:', process.env.VUE_APP_POOL_ID);
    console.log('ClientId:', process.env.VUE_APP_CLIENT_ID);
  
      var poolData = {
        UserPoolId: process.env.VUE_APP_POOL_ID,
        ClientId: process.env.VUE_APP_CLIENT_ID,
      };
      var userPool = new CognitoUserPool(poolData);

      //cognitoパラメータ設定
      var username = inputEmail.value;
      var password = inputPassword.value;

      var authenticationData = {
        Username: username,
        Password: password,
      };
      var authenticationDetails = new AuthenticationDetails(
        authenticationData
      );

      var userData = {
        Username: username,
        Pool: userPool,
      };

      var cognitoUser = new CognitoUser(userData);
      cognitoUser.authenticateUser(authenticationDetails, {
		onSuccess: function(result) {
		    console.log('authentication onSuccess');
		    // トークンをローカルストレージに保存
		    localStorage.setItem('user-token', result.getIdToken().getJwtToken());
		    router.push('/UserChat').catch(err => console.error(err));
		},
        onFailure: function(err) {
          console.log('authentication　onFailure');
          alert(err.message || JSON.stringify(err));
        }
      });
    }
    return {
      inputEmail,
      inputPassword,
      login,
      locale,
      localeOptions: [
        { value: 'en-US', label: 'English' },
        { value: 'ja-JP', label: '日本語' }
      ]
    }
  }
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

/* ラベルのスタイリング */
label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color: #555; /* ラベルの色 */
}
</style>