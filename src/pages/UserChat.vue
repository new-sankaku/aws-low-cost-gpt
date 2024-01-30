<template>
  <q-layout>
    <q-header>
      <q-toolbar>
        <q-toolbar-title>
          Low Cost GeneratedAI
        </q-toolbar-title>
        <q-btn flat round dense icon="person_add" @click="newChat" label="New Chat"></q-btn>
        <q-btn flat round dense icon="logout" @click="logout" label="ログアウト"></q-btn>
      </q-toolbar>
      <div id="buttons" class="q-mb-md">
		<q-btn
		  v-for="(layer, index) in layers"
		  :key="index"
		  :text-color="index === activeLayer ? 'black' : 'grey-7'"
		  :color="index === activeLayer ? 'white' : 'black'"
		  @click="showLayer(index)"
		>
		  {{ layer }}
		</q-btn>
      </div>
    </q-header>
    <q-page-container>
      <q-page class="page-container">
        <div
          v-for="(layer, index) in layers"
          :key="index"
          v-show="index === activeLayer"
          class="layer q-pa-md"
        >
          <div class="chat-history">
            <div
              v-for="(message, messageIndex) in chatHistory[index]"
              :key="messageIndex"
              class="message"
            >
              <div
                class="message-icon"
                :class="{ 'ai-icon': message.sender === 'ai', 'user-icon': message.sender === 'user' }"
              ></div>
              <span v-html="formatMessage(message.text)"></span>
            </div>
          </div>
          <div class="input-area row items-center">
            <q-input
              v-model="inputFields[index]"
              filled
              clearable
              autogrow
              type="textarea"
              :id="'input' + index"
              @keypress.enter.prevent="sendMessage(index)"
              @keydown="processKeydown(index, $event)"
              class="col-12"
            >
              <template v-slot:append>
                <q-btn icon="send" @click="sendMessage(index)" flat></q-btn>
              </template>
            </q-input>
          </div>
        </div>
      </q-page>
    </q-page-container>
  </q-layout>
</template>
<script>
export default {
  data() {
    return {
      layers: [
        'Layer 1234567891',
        'Layer 2',
        'Layer 3',
        // 他のレイヤー...
      ],
      chatHistory: [],
      inputFields: [],
      activeLayer: 0,
    };
  },
  created() {
    this.createLayers();
  },
  methods: {
    newChat() {
      this.layers.push('next AI');
      this.chatHistory.push([]);
      this.inputFields.push('');
      this.activeLayer = this.layers.length - 1;
    },
    logout() {
      localStorage.removeItem('user-token');
      this.$router.push('/');
    },
    processKeydown(index, event) {
      if (event.shiftKey && event.keyCode === 13) {
        event.preventDefault();
        this.inputFields[index] += '\r\n';
      }
    },
    createLayers() {
      this.layers.forEach((_, index) => {
        this.chatHistory.push([]);
        this.inputFields.push('');
      });
    },
    showLayer(index) {
      this.activeLayer = index;
    },
    sendMessage(index) {
      const inputField = this.inputFields[index];
      if (inputField.trim() === '') return;
      this.chatHistory[index].push({ text: inputField, sender: 'user' });
      this.inputFields[index] = '';
    },
    formatMessage(text) {
      return text.replace(/\r\n/g, '<br>').replace(/\n/g, '<br>');
    },
  },
};
</script>



<style scoped>
#buttons {
  display: flex;
  white-space: nowrap;
  overflow-x: auto;
  overflow-y: hidden;
  align-items: center;
  
}

.layer {
  display: flex;
  flex-direction: column;
  height: 90vh;
  border: 1px solid #ccc;
  position: fixed;
  bottom: 0;
  width: 100%;
}
.chat-history {
  flex-grow: 1;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
}

.message {
  display: flex;
  align-items: center;
  margin-bottom: 5px;
  flex-wrap: nowrap;
}

.message-icon {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  margin-right: 5px;
  flex-shrink: 0;
}

.message span {
  flex-grow: 1;
}

.ai-icon {
  background-color: #007bff;
  border: 2px solid #0056b3;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.user-icon {
  background-color: #ff4081;
  border: 2px solid #c60055;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.input-area {
  display: flex;
  align-items: center;
}

.page-container{
}

textarea {
  width: 100%;
  resize: none;
  overflow-y: hidden;
  white-space: pre-wrap;
}
.q-btn {
  margin-right: 10px;
}
</style>