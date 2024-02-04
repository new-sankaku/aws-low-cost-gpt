<template>
  <q-layout>
    <q-header class="custom-toolbar">
      <q-toolbar>
        <q-btn flat round dense icon="menu" @click="toggleDrawer"></q-btn>
        <q-toolbar-title>
          {{ $t("app_name") }}
        </q-toolbar-title>

        <q-select
          filled
          v-model="selectedModel"
          :options="modelOptions"
          label="Ai model"
          class="q-pr-sm custom-selection"
          @update:modelValue="updateDollerValues"
        />
        <div class="q-pr-xl label-container">
          <label class="custom-label">1K In: ${{ inputDoller }}</label>
          <label class="custom-label">1K Out: ${{ outDoller }}</label>
        </div>

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
      <q-scroll-area
        class="fit"
        :thumb-style="thumbStyle"
        :bar-style="barStyle"
      >
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
              <q-item-label>
                {{ layer.title }}
              </q-item-label>
              <q-item-label caption lines="1">
                Total ${{ layer.sumTotal }}
              </q-item-label>
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
          :class="`layer q-pt-sm ${drawer ? 'drawer-open' : 'drawer-closed'}`"
        >
          <q-scroll-area
            :thumb-style="thumbStyle"
            :bar-style="barStyle"
            style="height: 100vh"
          >
            <div class="chat-history q-pa-md">
              <div
                v-for="(message, messageIndex) in chatHistory[index]"
                :key="messageIndex"
                class="message q-pa-md"
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
          </q-scroll-area>

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
import { getData, postData } from "./../api/RestService";
import { useI18n } from "vue-i18n";
import { useRouter } from "vue-router";

export default {
  setup() {
    return {
      thumbStyle: {
        right: "4px",
        borderRadius: "5px",
        backgroundColor: "#027be3",
        width: "5px",
        opacity: 0.75,
      },

      barStyle: {
        right: "2px",
        borderRadius: "9px",
        backgroundColor: "#027be3",
        width: "9px",
        opacity: 0.2,
      },
    };
  },
  data() {
    return {
      layers: [],
      chatHistory: [],
      inputFields: [],
      activeLayer: 0,
      drawer: false,
      selectedModel: null,
      modelOptions: [],
      inputDoller: 0.0,
      outDoller: 0.0,
      modelsData: [],
    };
  },
  created() {
    this.fetchChatRoomHistory();
  },
  mounted() {
    this.fetchUserPlan();
  },
  methods: {
    fetchUserPlan() {
      console.log("fetchUserPlan");

      getData("UsersPlan")
        .then((data) => {
          this.modelsData = data;
          this.modelOptions = data.map((model) => ({
            label: model.modelName,
            value: model.modelId,
          }));
          if (data.length > 0) {
            this.inputDoller = data[0].inputDoller;
            this.outDoller = data[0].outDoller;
          }
        })
        .catch((error) => {
          console.error("Failed to fetch user plan:", error);
        });
    },
    updateDollerValues() {
      console.log("updateDollerValues");
      console.log("this.selectedModel", this.selectedModel);
      console.log("this.selectedModel.modelId", this.selectedModel.value);
      const selectedModel = this.modelsData.find(
        (model) => model.modelId === this.selectedModel.value
      );

      console.log("Selected Model ID:", selectedModel);

      if (selectedModel) {
        console.log("if (selectedModel)");

        console.log("inputDoller", selectedModel.inputDoller);
        console.log("outDoller", selectedModel.outDoller);

        this.inputDoller = selectedModel.inputDoller;
        this.outDoller = selectedModel.outDoller;
      }
    },
    toggleDrawer() {
      this.drawer = !this.drawer;
    },
    newChat() {
      this.layers.push({
        title: "next AI",
        roomId: undefined,
      });
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
          this.layers = chatRooms.map((room) => ({
            title: room.roomTitle,
            roomId: room.roomId,
            aiModel: room.aiModel,
            sumTotal: room.sumTotal,
          }));
          this.chatHistory = chatRooms.map(() => []);
          this.inputFields = chatRooms.map(() => "");

          if (
            this.activeLayer !== undefined &&
            this.activeLayer !== null &&
            this.chatHistory[this.activeLayer].length === 0
          ) {
            this.fetchChatMessages(this.activeLayer);
          }
        })
        .catch((error) =>
          console.error("Error fetching chat room history:", error)
        );
    },
    showLayer(index) {
      this.activeLayer = index;
      const roomId = this.layers[index].roomId;
      if (roomId !== undefined && this.chatHistory[index].length === 0) {
        this.fetchChatMessages(index);
      }
    },
    fetchChatMessages(index) {
      const roomId = this.layers[index].roomId;
      getData(`ChatRoom/Message/${roomId}`)
        .then((chatMessages) => {
          this.chatHistory[index] = chatMessages.map((msg) => ({
            text: msg.message,
            sender: msg.sender,
          }));
        })
        .catch((error) => {
          console.error("Error fetching chat messages:", error);
        });
    },
    async sendMessage(index) {
      const inputField = this.inputFields[index];
      if (inputField.trim() === "") return;
      this.chatHistory[index].push({ text: inputField, sender: "user" });
      this.inputFields[index] = "";

      try {
        const chatMessageList = this.chatHistory.flatMap((chatHistoryItem) =>
          chatHistoryItem.map((chat) => ({
            sender: chat.sender,
            message: chat.text,
          }))
        );

        console.log("chatMessageList:", chatMessageList);
        const response = await postData("ChatCompletions", chatMessageList);

        if (response && response.message) {
          this.chatHistory[index].push({
            text: response.message,
            sender: "ai",
          });
        }
      } catch (error) {
        console.error("Failed to send message:", error);
      }
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

.custom-toolbar > .q-toolbar {
  justify-content: center;
  max-height: 8.5vh;
}
.custom-selection {
  min-width: 300px;
}
.label-container .custom-label {
  color: #ccc;
  margin-right: 8px;
  margin-top: 8px;
  min-width: 5vh;
  display: flex;
  flex-direction: column;
  margin-top: 0px;
}
</style>
