<template>
  <q-header class="bg-primary text-white no-wrap">
    <q-toolbar>
      <q-btn dense flat round icon="menu_open" @click="clickLeftToggleDrawer" />
      <img
        class="push-left q-ml-sm"
        width="20px"
        height="20px"
        src="../assets/logo_reverse.svg"
        alt="Logo"
      />

      <q-toolbar-title class="mobile-none">
        {{ $t("app_name") }}
      </q-toolbar-title>
      <q-select
        class="q-mx-sm deep"
        standout
        rounded
        outlined
        color="black"
        v-model="selectedAiModel"
        :options="aiModelOptions"
        @update:modelValue="updateDollerValues"
      >
        <template v-slot:prepend>
          <q-icon name="psychology" color="white" style="width: 20px" />
        </template>
      </q-select>

      <div class="q-mr-md mobile-none" style="color: aliceblue">
        <label>1K In :${{ inputDoller }}<br />1K Out:${{ outDoller }}</label>
      </div>

      <q-btn
        size="12px"
        outline
        shadow
        rounded
        icon="post_add"
        :label="newChatButtonLabel"
        class="q-mr-md"
        @click="newChat"
      ></q-btn>

      <q-btn
        size="12px"
        class="push-right"
        dense
        flat
        round
        icon="settings"
        @click="clickRightToggleDrawer"
      />
    </q-toolbar>
  </q-header>
</template>

<script>
import { computed, ref, inject } from "vue";
import { getData, postData } from "./../api/RestService";

export default {
  mounted() {
    this.fetchUserPlan();
  },
  data() {
    return {
      modelsData: [],
    };
  },
  methods: {
    fetchUserPlan() {
      getData("UsersPlan")
        .then((data) => {
          this.modelsData = data;
          this.aiModelOptions = data.map((model) => ({
            label: model.modelName,
            value: model.modelId,
          }));
          if (data.length > 0) {
            this.inputDoller = data[0].inputDoller;
            this.outDoller = data[0].outDoller;
            this.selectedAiModel = this.aiModelOptions[0];
          }
        })
        .catch((error) => {
          console.error("Failed to fetch user plan:", error);
        });
    },
    updateDollerValues() {
      const selectedAiModel = this.modelsData.find(
        (model) => model.modelId === this.selectedAiModel.value
      );

      if (selectedAiModel) {
        this.inputDoller = selectedAiModel.inputDoller;
        this.outDoller = selectedAiModel.outDoller;
      }
    },
    newChat() {
      console.log("newChat start");
      this.chatRooms.rooms.push({
        title: "next AI",
        roomId: undefined,
      });
      this.chatRooms.chatRoomHistorys.push([]);
      this.chatRooms.chatInputFields.push("");
      this.activeLayer = this.layers.length - 1;
    },
  },
  setup() {
    const selectedAiModel = inject("selectedAiModel");
    const aiModelOptions = ref([]);
    const inputDoller = ref(0);
    const outDoller = ref(0);

    // ドロワーのトグル関数の注入
    const clickRightToggleDrawer = inject("clickRightToggleDrawer");
    const clickLeftToggleDrawer = inject("clickLeftToggleDrawer");

    const newChatButtonLabel = computed(() => {
      const isMobile = window.innerWidth <= 650;
      return isMobile ? "" : "New Chat";
    });

    const chatRooms = inject("chatRooms");

    return {
      chatRooms,
      newChatButtonLabel,
      selectedAiModel,
      aiModelOptions,
      inputDoller,
      outDoller,
      clickLeftToggleDrawer,
      clickRightToggleDrawer,
    };
  },
};
</script>

<style scoped>
.monospace-label label {
  font-family: "Lucida Console", Monaco;
}

:deep(.q-field--outlined .q-field__control:before) {
  border: 1.2px solid rgba(255, 255, 255);
  margin-top: 4%;
  margin-bottom: 4%;
}

@media (max-width: 650px) {
  .mobile-none {
    display: none;
  }
}

.push-right {
  margin-left: auto;
}
.push-left {
  margin-right: auto;
}
</style>
