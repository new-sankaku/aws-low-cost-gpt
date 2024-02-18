<template>
  <q-layout view="hHh LpR fFf">
    <router-view name="headerComponent"></router-view>
    <!--
    <router-view name="fotterComponent"></router-view>
  -->
    <router-view name="leftComponent"></router-view>
    <router-view name="centerComponent"></router-view>

    <router-view name="rightComponent"></router-view>
  </q-layout>
</template>

<script>
import { getData } from "src/api/RestService";
import { computed, reactive, provide, ref, onMounted } from "vue";
import { useQuasar, QSpinnerGears } from "quasar";

export default {
  components: {},
  setup() {
    const $q = useQuasar();

    const isLeftDrawerOpen = ref(false);
    const isRightDrawerOpen = ref(false);
    provide("isLeftDrawerOpen", isLeftDrawerOpen);
    provide("isRightDrawerOpen", isRightDrawerOpen);

    const clickLeftToggleDrawer = () => {
      isLeftDrawerOpen.value = !isLeftDrawerOpen.value;
    };
    const clickRightToggleDrawer = () => {
      isRightDrawerOpen.value = !isRightDrawerOpen.value;
    };
    provide("clickLeftToggleDrawer", clickLeftToggleDrawer);
    provide("clickRightToggleDrawer", clickRightToggleDrawer);

    /** right view, carouse slide position */
    const rightCarouselHeadPromptPosition = ref(1);
    const rightCarouselTailPromptPosition = ref(1);
    provide("rightCarouselHeadPromptPosition", rightCarouselHeadPromptPosition);
    provide("rightCarouselTailPromptPosition", rightCarouselTailPromptPosition);

    /** right view, prompt toggle. */
    const isRightHeadPromptCheckBox = ref(true);
    const isRightTailPromptCheckBox = ref(false);
    provide("isRightHeadPromptCheckBox", isRightHeadPromptCheckBox);
    provide("isRightTailPromptCheckBox", isRightTailPromptCheckBox);

    /** right view, carouse slide text. */
    const rightCarouselHeadPromptText1 = ref("");
    const rightCarouselHeadPromptText2 = ref("");
    const rightCarouselHeadPromptText3 = ref("");
    const rightCarouselHeadPromptText4 = ref("");
    const rightCarouselTailPromptText1 = ref("");
    const rightCarouselTailPromptText2 = ref("");
    const rightCarouselTailPromptText3 = ref("");
    const rightCarouselTailPromptText4 = ref("");

    onMounted(() => {
      getData("UserSettings")
        .then((userSettings) => {
          isRightHeadPromptCheckBox.value = userSettings.headPromptEnabled;
          isRightTailPromptCheckBox.value = userSettings.tailPromptEnabled;

          userSettings.headPromptList.forEach((prompt, index) => {
            if (index == 0) {
              rightCarouselHeadPromptText1.value = prompt;
            }
            if (index == 1) {
              rightCarouselHeadPromptText2.value = prompt;
            }
            if (index == 2) {
              rightCarouselHeadPromptText3.value = prompt;
            }
            if (index == 3) {
              rightCarouselHeadPromptText4.value = prompt;
            }
          });

          userSettings.tailPromptList.forEach((prompt, index) => {
            if (index == 0) {
              rightCarouselTailPromptText1.value = prompt;
            }
            if (index == 1) {
              rightCarouselTailPromptText2.value = prompt;
            }
            if (index == 2) {
              rightCarouselTailPromptText3.value = prompt;
            }
            if (index == 3) {
              rightCarouselTailPromptText4.value = prompt;
            }
          });
        })
        .catch(() => {
          console.error("main Failed to load user settings:", error);
        });
    });

    provide("rightCarouselHeadPromptText1", rightCarouselHeadPromptText1);
    provide("rightCarouselHeadPromptText2", rightCarouselHeadPromptText2);
    provide("rightCarouselHeadPromptText3", rightCarouselHeadPromptText3);
    provide("rightCarouselHeadPromptText4", rightCarouselHeadPromptText4);
    provide("rightCarouselTailPromptText1", rightCarouselTailPromptText1);
    provide("rightCarouselTailPromptText2", rightCarouselTailPromptText2);
    provide("rightCarouselTailPromptText3", rightCarouselTailPromptText3);
    provide("rightCarouselTailPromptText4", rightCarouselTailPromptText4);

    const currentHeadPromptText = computed(() => {
      switch (rightCarouselHeadPromptPosition.value) {
        case 1:
          return rightCarouselHeadPromptText1.value;
        case 2:
          return rightCarouselHeadPromptText2.value;
        case 3:
          return rightCarouselHeadPromptText3.value;
        case 4:
          return rightCarouselHeadPromptText4.value;
        default:
          return ""; // デフォルトのケース
      }
    });

    const currentTailPromptText = computed(() => {
      switch (rightCarouselTailPromptPosition.value) {
        case 1:
          return rightCarouselTailPromptText1.value;
        case 2:
          return rightCarouselTailPromptText2.value;
        case 3:
          return rightCarouselTailPromptText3.value;
        case 4:
          return rightCarouselTailPromptText4.value;
        default:
          return ""; // デフォルトのケース
      }
    });

    provide("currentHeadPromptText", currentHeadPromptText);
    provide("currentTailPromptText", currentTailPromptText);

    const inputRightFiles = ref(null);
    const isRightUseFileCheckBox = ref(false);
    const isRightAddFileNameCheckBox = ref(false);
    provide("inputRightFiles", inputRightFiles);
    provide("isRightUseFileCheckBox", isRightUseFileCheckBox);
    provide("isRightAddFileNameCheckBox", isRightAddFileNameCheckBox);

    const activeChatRoomIndex = ref(0);

    const chatRooms = reactive({
      rooms: [],
      chatRoomHistorys: [],
      chatInputFields: [],
    });
    provide("chatRooms", chatRooms);
    provide("activeChatRoomIndex", activeChatRoomIndex);

    const selectedAiModel = ref("");
    provide("selectedAiModel", selectedAiModel);

    const calculating = reactive([]);
    provide("calculating", calculating);

    const isChatHistoryLoading = ref(false);
    provide("isChatHistoryLoading", isChatHistoryLoading);

    return {};
  },
};
</script>

<style scoped></style>
