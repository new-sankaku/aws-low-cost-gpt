<template>
  <q-layout>
    <q-header>
      <q-toolbar>
        <q-btn flat round dense icon="menu" @click="toggleDrawer"></q-btn>
        <q-toolbar-title>
          {{ $t("app_name") }}
        </q-toolbar-title>
        <q-btn
          flat
          round
          dense
          icon="person_add"
          @click="newChat"
          label="New Chat"
        ></q-btn>
        <q-btn
          flat
          round
          dense
          icon="logout"
          @click="logout"
          label="Logout"
        ></q-btn>
      </q-toolbar>
    </q-header>

    <q-drawer v-model="drawer" show-if-above :width="200" :breakpoint="500">
      <q-scroll-area class="fit">
        <q-list padding class="menu-list">
          <q-item
            v-for="(layer, index) in layers"
            :key="'layer-' + index"
            clickable
            v-ripple
            @click="showLayer(index)"
            :class="{ 'active-item': index === activeLayer }"
          >
            <q-item-section>
              {{ layer }}
            </q-item-section>
          </q-item>
        </q-list>
      </q-scroll-area>
    </q-drawer>
    <q-page-container>
      <q-page class="page-container">
        <div
          v-for="(layer, index) in layers"
          :key="index"
          v-show="index === activeLayer"
          :class="`layer q-pa-md ${drawer ? 'drawer-open' : 'drawer-closed'}`"
        >
          <div class="chat-history">
            <div
              v-for="(message, messageIndex) in chatHistory[index]"
              :key="messageIndex"
              class="message"
              :class="{
                'user-message': message.sender === 'user',
                'ai-message': message.sender === 'ai',
              }"
            >
              <div
                class="message-icon"
                :class="{
                  'ai-icon': message.sender === 'ai',
                  'user-icon': message.sender === 'user',
                }"
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
            <div class="input-notice">
              {{ $t("warning_message") }}
            </div>
          </div>
        </div>
      </q-page>
    </q-page-container>
  </q-layout>
</template>
<script>
import axios from "axios";
import { getData } from "./../api/RestService";

export default {
  data() {
    return {
      layers: [],
      chatHistory: [],
      inputFields: [],
      activeLayer: 0,
      drawer: false,
    };
  },
  created() {
    this.fetchChatRoomHistory();
  },
  methods: {
    toggleDrawer() {
      this.drawer = !this.drawer;
    },
    newChat() {
      this.layers.push("next AI");
      this.chatHistory.push([]);
      this.inputFields.push("");
      this.activeLayer = this.layers.length - 1;
      this.drawer = true;
    },
    logout() {
      localStorage.removeItem("user-token");
      this.$router.push("/");
    },
    processKeydown(index, event) {
      if (event.shiftKey && event.keyCode === 13) {
        event.preventDefault();
        this.inputFields[index] += "\r\n";
      }
    },
    createLayers() {
      this.layers.forEach((_, index) => {
        this.chatHistory.push([]);
        this.inputFields.push("");
      });
    },
    fetchChatRoomHistory() {
      getData("ChatRoomHistory")
        .then((chatRooms) => {
          this.layers = chatRooms.map((room) => room.roomTitle);
          this.chatHistory = chatRooms.map(() => []);
          this.inputFields = chatRooms.map(() => "");
        })
        .catch((error) =>
          console.error("Error fetching chat room history:", error)
        );
    },
    showLayer(index) {
      this.activeLayer = index;
    },
    sendMessage(index) {
      const inputField = this.inputFields[index];
      if (inputField.trim() === "") return;
      this.chatHistory[index].push({ text: inputField, sender: "user" });
      this.inputFields[index] = "";

      const apiEndpoint = process.env.VUE_APP_API_ENDPOINT;
      axios
        .get(`${apiEndpoint}/users`)
        .then((response) => {
          console.log(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    formatMessage(text) {
      return text.replace(/\r\n/g, "<br>").replace(/\n/g, "<br>");
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
  height: 95vh;
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
  word-wrap: break-word;
  overflow-wrap: break-word;
  white-space: normal;
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
  word-break: break-all;
}

.ai-icon {
  background-color: #007bff;
  border: 2px solid #0056b3;
}

.user-icon {
  background-color: #ff4081;
  border: 1px solid #c60055;
}

.user-message {
  background-color: #faf0e6;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
.ai-message {
  background-color: #fafad2;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.input-area {
  display: flex;
  align-items: center;
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
.drawer-open {
  width: calc(100% - 200px);
}

.drawer-closed {
  width: 100%;
}
.active-item {
  color: orange;
  font-weight: bold;
  text-transform: uppercase;
}

.menu-list .q-item {
  border-bottom: 1px solid #e0e0e0;
}

.menu-list .q-item:hover {
  cursor: pointer;
}

.input-notice {
  font-size: 12px;
  color: #666;
  margin-top: 5px;
  text-align: center;
}

.drawer-closed-bar {
  position: fixed;
  left: 0;
  bottom: 0;
  width: 40px;
  height: 4px;
  background-color: #ccc;
  z-index: 1;
}
</style>
