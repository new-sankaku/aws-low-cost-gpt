<template>
  <q-page-container>
    <div
      v-if="isChatHistoryLoading"
      class="flex flex-center"
      style="height: 100vh; width: 100vw; position: absolute; left: 0; top: 0"
    >
      <q-spinner-pie size="100px" color="primary"></q-spinner-pie>
    </div>

    <q-page class="page-container">
      <div
        v-for="(chatRoom, index) in chatRooms.rooms"
        :key="index"
        v-show="index === localActiveIndex"
        :class="`layer q-pt-sm ${
          isLeftDrawerOpen && isRightDrawerOpen
            ? 'double-drawer-open'
            : isLeftDrawerOpen || isRightDrawerOpen
            ? 'single-drawer-closed'
            : 'double-drawer-closed'
        }`"
      >
        <!-- <div
          class="flex flex-center"
          style="height: 50vh; width: 50vw; position: absolute; left: 0; top: 0"
        >
          <q-spinner-pie size="100px" color="primary"></q-spinner-pie>
        </div> -->

        <q-scroll-area
          ref="scrollAreaRef"
          :thumb-style="thumbStyle"
          :bar-style="barStyle"
          style="height: 100vh"
        >
          <div class="chat-history q-pa-md">
            <div
              v-for="(message, messageIndex) in chatRooms.chatRoomHistorys[
                index
              ]"
              :key="messageIndex"
              class="message q-pa-md"
              :class="{
                'boxUser user-message': message.sender === 'user',
                'boxAI ai-message': message.sender === 'ai',
              }"
            >
              <div
                class="message-icon mobile-none"
                :class="{
                  'ai-icon': message.sender === 'ai',
                  'user-icon': message.sender === 'user',
                }"
              ></div>
              <span v-html="formatMessage(message.message)"></span>
            </div>
          </div>
        </q-scroll-area>

        <div class="overlay-container">
          <q-btn
            size="10px"
            rounded
            dense
            icon="arrow_downward"
            color="primary"
            @click="animateScroll"
            class="floating-button"
          />
        </div>

        <div class="q-mx-sm input-area row items-center">
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
              <q-btn
                icon="send"
                :loading="calculating[index]"
                @click="sendMessage(index)"
                flat
              >
                <template v-slot:loading>
                  <q-spinner-hourglass />
                </template>
              </q-btn>
            </template>
          </q-input>
          <div class="input-notice q-pa-sm">
            {{ $t("warning_message") }}
          </div>
        </div>
      </div>
    </q-page>
  </q-page-container>
</template>

<!--


Separator space when viewed on minimap


-->

<script>
import { getData, postData } from "./../api/RestService";
import { ref, watch, inject, onMounted } from "vue";
import { useQuasar, QSpinnerGears } from "quasar";

export default {
  setup() {
    const $q = useQuasar();

    const isLeftDrawerOpen = inject("isLeftDrawerOpen");
    const isRightDrawerOpen = inject("isRightDrawerOpen");

    const chatRooms = inject("chatRooms");

    const animateScroll = () => {
      const scrollTarget =
        scrollAreaRef.value[activeChatRoomIndex.value].getScrollTarget();
      const duration = 400;
      scrollAreaRef.value[activeChatRoomIndex.value].setScrollPosition(
        "vertical",
        scrollTarget.scrollHeight,
        duration
      );
    };

    const fetchChatMessages = (index) => {
      const roomId = chatRooms.rooms[index].roomId;
      const chatRoomHistorys = chatRooms.chatRoomHistorys[index];

      if (!roomId) {
        console.log("chat room id not found. return");
        return;
      }

      if (!chatRoomHistorys || chatRoomHistorys.length === 0) {
        console.log("chat room histories not found.");
      } else {
        console.log("chat room histories found. return");
        return;
      }

      console.log("isChatHistoryLoading then", isChatHistoryLoading.value);
      isChatHistoryLoading.value = true;

      getData(`ChatRooms/Message/${roomId}`)
        .then((chatMessages) => {
          if (chatRooms.chatRoomHistorys[index]) {
            chatRooms.chatRoomHistorys[index].push(
              ...chatMessages.map((msg) => ({
                message: msg.message,
                sender: msg.sender,
              }))
            );
          } else {
            chatRooms.chatRoomHistorys[index] = chatMessages.map((msg) => ({
              message: msg.message,
              sender: msg.sender,
            }));
          }
        })
        .catch((error) => {
          console.error("Error fetching chat messages:", error);
        })
        .finally(() => {
          console.log(
            "isChatHistoryLoading finally",
            isChatHistoryLoading.value
          );
          isChatHistoryLoading.value = false;
          animateScroll();
        });
    };

    watch(chatRooms.rooms, (newChatRooms, oldChatRooms) => {
      newChatRooms.forEach((chatRoom, index) => {
        if (index == 0) {
          fetchChatMessages(index);
        }
      });
    });

    const isChatHistoryLoading = inject("isChatHistoryLoading");
    const activeChatRoomIndex = inject("activeChatRoomIndex");
    watch(activeChatRoomIndex, (newValue, oldValue) => {
      fetchChatMessages(newValue);
    });

    const selectedAiModel = inject("selectedAiModel");

    const isRightHeadPromptCheckBox = inject("isRightHeadPromptCheckBox");
    const isRightTailPromptCheckBox = inject("isRightTailPromptCheckBox");

    const isRightUseFileCheckBox = inject("isRightUseFileCheckBox");
    const isRightAddFileNameCheckBox = inject("isRightAddFileNameCheckBox");

    const currentHeadPromptText = inject("currentHeadPromptText");
    const currentTailPromptText = inject("currentTailPromptText");
    const position = ref(300);
    const scrollAreaRef = ref(null);

    const calculating = inject("calculating");

    return {
      isChatHistoryLoading,
      calculating,
      animateScroll,
      fetchChatMessages,

      position,
      scrollAreaRef,
      currentHeadPromptText,
      currentTailPromptText,
      isRightHeadPromptCheckBox,
      isRightTailPromptCheckBox,
      isRightUseFileCheckBox,
      isRightAddFileNameCheckBox,

      selectedAiModel,
      localActiveIndex: activeChatRoomIndex,
      chatRooms,
      isLeftDrawerOpen,
      isRightDrawerOpen,
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
      loadingCounter: 0,
    };
  },
  data() {
    return {
      inputFields: [],
      activeLayer: 0,
      isLoading: false,
    };
  },
  created() {
    /**
    this.fetchChatRoomHistory();
     */
    this.initializeDummyData();
  },
  methods: {
    initializeDummyData() {
      this.inputFields = [""];
    },
    processKeydown(index, event) {
      if (event.shiftKey && event.keyCode === 13) {
        event.preventDefault();
        this.inputFields[index] += "\r\n";
      }
    },
    async sendMessage(index) {
      let inputField = this.inputFields[index];

      if (this.isRightHeadPromptCheckBox) {
        inputField = this.currentHeadPromptText + "\r\n" + inputField;
      }
      if (this.isRightTailPromptCheckBox) {
        inputField = inputField + "\r\n" + this.currentTailPromptText;
      }

      if (inputField.trim() === "") return;

      this.chatRooms.chatRoomHistorys[index].push({
        message: inputField,
        sender: "user",
      });

      this.animateScroll();

      this.inputFields[index] = "";
      this.isLoading = true;
      this.loadingCounter += 1;

      try {
        this.calculating.splice(index, 1, true);
        console.log("calculating " + index, this.calculating[index]);

        const chatMessageList = this.chatRooms.chatRoomHistorys[index].map(
          (chatHistoryItem) => ({
            sender: chatHistoryItem.sender,
            message: chatHistoryItem.message,
          })
        );
        const dataToSend = {
          chatMessageList: chatMessageList,
          selectedAiModel: this.selectedAiModel.label,
          roomId: this.chatRooms.rooms[index].roomId,
        };

        console.log("Sending data:", dataToSend);

        const response = await postData("ChatCompletions", dataToSend);

        if (response && response.message) {
          this.chatRooms.rooms[index].roomId = response.roomId;
          this.chatRooms.rooms[index].title = response.roomTitle;

          this.chatRooms.chatRoomHistorys[index].push({
            message: response.message,
            sender: "ai",
          });
        }
      } catch (error) {
        console.error("Failed to send message:", error);
      } finally {
        this.animateScroll();
        this.calculating.splice(index, 1, false);
        console.log("calculating " + index, this.calculating[index]);

        this.loadingCounter -= 1;
        if (this.loadingCounter == 0) {
          this.isLoading = false;
        }
      }
    },
    formatMessage(message) {
      return message.replace(/\r\n/g, "<br>").replace(/\n/g, "<br>");
    },
  },
};
</script>

<!--



Separator space when viewed on minimap


-->

<style scoped>
.layer {
  display: flex;
  flex-direction: column;
  height: 95vh;
  border: 1px solid #ccc;
  position: fixed;
  bottom: 0;
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
  background-color: #b2d7ff;
}

.user-icon {
  background-color: #ffcddd;
}

.user-message {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
.ai-message {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.input-area {
  display: flex;
  align-items: center;
}

textarea {
  resize: none;
  overflow-y: hidden;
  white-space: pre-wrap;
}
.q-btn {
  margin-right: 10px;
}
.double-drawer-open {
  width: calc(100% - 600px);
}
.single-drawer-closed {
  width: calc(100% - 300px);
}

.double-drawer-closed {
  width: 100%;
}

.input-notice {
  font-size: 12px;
  color: #666;
  margin-top: 5px;
  text-align: center;
}

.boxAI {
  padding: 0.5em 1em;
  margin: 0.2em 0;
  color: #232323;
  border-left: solid 10px #d3e8ff;
}
.boxAI p {
  margin: 0;
  padding: 0;
}

.boxUser {
  padding: 0.5em 1em;
  margin: 0.2em 0;
  color: #232323;
  border-left: solid 10px #ffcddd;
}
.boxUser p {
  margin: 0;
  padding: 0;
}
.overlay-container {
  opacity: 0.5;
  position: relative;
}

.floating-button {
  position: absolute;
  bottom: 10px;
  right: 2%;
  z-index: 100;
}
.flex-center {
  display: flex;
  justify-content: center;
  align-items: center;
}
@media (max-width: 650px) {
  .mobile-none {
    display: none;
  }
}
</style>
