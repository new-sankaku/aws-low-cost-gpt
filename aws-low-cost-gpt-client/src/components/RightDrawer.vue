<template>
  <q-drawer
    show-if-above
    v-model="clickRightToggleDrawer"
    side="right"
    bordered
    class="column inline"
  >
    <div class="q-mx-sm row">
      <q-btn
        size="12px"
        icon="feed"
        rounded
        no-wrap
        style="width: 60%"
        class="q-ma-sm"
        label="Head Prompt"
        color="primary"
        @click="rightDialogHeadPrompt = true"
      />
      <q-toggle
        v-model="isRightHeadPromptToggle"
        color="primary"
        size="md"
        icon="keyboard_double_arrow_up"
      />
      <q-btn
        size="12px"
        icon="feed"
        rounded
        no-wrap
        style="width: 60%"
        class="q-ma-sm"
        label="Tail Prompt"
        color="primary"
        counter
        @click="rightDialogTailPrompt = true"
      />
      <q-toggle
        v-model="isRightTailPromptToggle"
        color="primary"
        size="md"
        icon="keyboard_double_arrow_down"
      />

      <q-separator
        class="q-my-md q-mb-lg"
        size="2px"
        width="100%"
        color="blue"
      />

      <q-file
        :model-value="files"
        @update:model-value="updateFiles"
        counter
        standout
        disable
        hint="Disable"
        label="Input by files.(Max 20kb)."
        max-file-size="20480"
        outlined
        multiple
        :clearable="!isUploading"
        style="width: 100%"
      >
        <template v-slot:file="{ index, file }">
          <q-chip
            class="full-width q-my-xs"
            :removable="isUploading && uploadProgress[index].percent < 1"
            square
            @remove="cancelFile(index)"
          >
            <q-linear-progress
              class="absolute-full full-height"
              :value="uploadProgress[index].percent"
              :color="uploadProgress[index].color"
              track-color="grey-2"
            />
            <div
              class="ellipsis relative-position"
              style="white-space: nowrap; overflow: hidden"
            >
              {{ file.name }}
            </div>

            <q-tooltip>
              {{ file.name }}
            </q-tooltip>
          </q-chip>
        </template>
      </q-file>

      <q-toggle
        disable
        hint="Disable"
        label="Use File"
        v-model="isRightUseFileToggle"
        color="primary"
        size="md"
        icon="keyboard_double_arrow_up"
      />
      <q-toggle
        disable
        hint="Disable"
        label="Add File Name"
        v-model="isRightAddFileNameToggle"
        color="primary"
        size="md"
        icon="keyboard_double_arrow_up"
      />

      <q-separator class="q-my-md" size="2px" width="100%" color="blue" />

      <div class="q-ma-sm">
        <q-btn
          size="12px"
          icon="manage_accounts"
          rounded
          disabled
          color="primary"
          label="Account"
          @click="rightAccountSettings = true"
        />
      </div>

      <div class="q-ma-sm">
        <q-btn
          size="12px"
          rounded
          @click="logout"
          icon="logout"
          color="primary"
          label="Logout"
        ></q-btn>
      </div>
    </div>

    <q-dialog v-model="rightDialogHeadPrompt">
      <q-card style="width: 100%; height: 75%" class="mobile-none">
        <q-carousel
          v-model="rightCarouselHeadPromptPosition"
          control-color="primary"
          navigation-icon="radio_button_unchecked"
          arrows
          navigation
          infinite
          padding
          style="width: 100%; height: 100%"
          class="bg-white shadow-1 rounded-borders"
        >
          <template v-slot:navigation-icon="{ active, btnProps, onClick }">
            <q-btn
              v-if="active"
              size="lg"
              icon="change_history"
              color="primary"
              flat
              round
              dense
              @click="onClick"
            />
            <q-btn
              v-else
              size="sm"
              :icon="btnProps.icon"
              color="black"
              flat
              round
              dense
              @click="onClick"
            />
          </template>
          <q-carousel-slide :name="1" class="column no-wrap flex-center">
            <div>
              <q-icon
                name="keyboard_double_arrow_up"
                color="primary"
                size="56px"
              />
              <q-icon name="looks_one" color="primary" size="56px" />
            </div>
            <q-input
              outlined
              label="It is automatically added before the prompt."
              style="width: 100%"
              v-model="rightCarouselHeadPromptText1"
              clearable
              autofocus
              type="textarea"
            />
          </q-carousel-slide>
          <q-carousel-slide :name="2" class="column no-wrap flex-center">
            <div>
              <q-icon
                name="keyboard_double_arrow_up"
                color="primary"
                size="56px"
              />
              <q-icon name="looks_two" color="primary" size="56px" />
            </div>
            <q-input
              outlined
              label="It is automatically added before the prompt."
              style="width: 100%"
              v-model="rightCarouselHeadPromptText2"
              clearable
              type="textarea"
            />
          </q-carousel-slide>
          <q-carousel-slide :name="3" class="column no-wrap flex-center">
            <div>
              <q-icon
                name="keyboard_double_arrow_up"
                color="primary"
                size="56px"
              />
              <q-icon name="looks_3" color="primary" size="56px" />
            </div>
            <q-input
              outlined
              label="It is automatically added before the prompt."
              style="width: 100%"
              v-model="rightCarouselHeadPromptText3"
              clearable
              type="textarea"
            />
          </q-carousel-slide>
          <q-carousel-slide :name="4" class="column no-wrap flex-center">
            <div>
              <q-icon
                name="keyboard_double_arrow_up"
                color="primary"
                size="56px"
              />
              <q-icon name="looks_4" color="primary" size="56px" />
            </div>
            <q-input
              outlined
              label="It is automatically added before the prompt."
              style="width: 100%"
              v-model="rightCarouselHeadPromptText4"
              clearable
              type="textarea"
            />
          </q-carousel-slide>
        </q-carousel>
      </q-card>
    </q-dialog>

    <q-dialog v-model="rightDialogTailPrompt">
      <q-card style="width: 100%; height: 75%" class="mobile-none">
        <q-carousel
          v-model="rightCarouselTailPromptPosition"
          control-color="primary"
          navigation-icon="radio_button_unchecked"
          arrows
          navigation
          infinite
          padding
          height="100%"
          class="bg-white shadow-1 rounded-borders"
        >
          <template v-slot:navigation-icon="{ active, btnProps, onClick }">
            <q-btn
              v-if="active"
              size="lg"
              icon="change_history"
              color="primary"
              flat
              round
              dense
              @click="onClick"
            />
            <q-btn
              v-else
              size="sm"
              :icon="btnProps.icon"
              color="black"
              flat
              round
              dense
              @click="onClick"
            />
          </template>

          <q-carousel-slide :name="1" class="column no-wrap flex-center">
            <div>
              <q-icon
                name="keyboard_double_arrow_down"
                color="primary"
                size="56px"
              />
              <q-icon name="looks_one" color="primary" size="56px" />
            </div>
            <q-input
              outlined
              label="It is automatically added after the prompt."
              style="width: 100%"
              v-model="rightCarouselTailPromptText1"
              clearable
              type="textarea"
            />
          </q-carousel-slide>
          <q-carousel-slide :name="2" class="column no-wrap flex-center">
            <div>
              <q-icon
                name="keyboard_double_arrow_down"
                color="primary"
                size="56px"
              />
              <q-icon name="looks_two" color="primary" size="56px" />
            </div>
            <q-input
              outlined
              label="It is automatically added after the prompt."
              style="width: 100%"
              v-model="rightCarouselTailPromptText2"
              clearable
              type="textarea"
            />
          </q-carousel-slide>
          <q-carousel-slide :name="3" class="column no-wrap flex-center">
            <div>
              <q-icon
                name="keyboard_double_arrow_down"
                color="primary"
                size="56px"
              />
              <q-icon name="looks_3" color="primary" size="56px" />
            </div>
            <q-input
              outlined
              label="It is automatically added after the prompt."
              style="width: 100%"
              v-model="rightCarouselTailPromptText3"
              clearable
              type="textarea"
            />
          </q-carousel-slide>
          <q-carousel-slide :name="4" class="column no-wrap flex-center">
            <div>
              <q-icon
                name="keyboard_double_arrow_down"
                color="primary"
                size="56px"
              />
              <q-icon name="looks_4" color="primary" size="56px" />
            </div>
            <q-input
              outlined
              label="It is automatically added after the prompt."
              style="width: 100%"
              v-model="rightCarouselTailPromptText4"
              clearable
              type="textarea"
            />
          </q-carousel-slide>
        </q-carousel>
      </q-card>
    </q-dialog>
  </q-drawer>
</template>

<script>
import { watch, computed, onBeforeUnmount, inject, ref } from "vue";
import { getData, postData } from "./../api/RestService";
import { useQuasar, QSpinnerGears } from "quasar";

export default {
  setup() {
    const $q = useQuasar();

    const rightCarouselHeadPromptPosition = inject(
      "rightCarouselHeadPromptPosition"
    );
    const rightCarouselTailPromptPosition = inject(
      "rightCarouselTailPromptPosition"
    );

    const isRightDrawerOpen = inject("isRightDrawerOpen");

    const rightCarouselHeadPromptText1 = inject("rightCarouselHeadPromptText1");
    const rightCarouselHeadPromptText2 = inject("rightCarouselHeadPromptText2");
    const rightCarouselHeadPromptText3 = inject("rightCarouselHeadPromptText3");
    const rightCarouselHeadPromptText4 = inject("rightCarouselHeadPromptText4");
    const rightCarouselTailPromptText1 = inject("rightCarouselTailPromptText1");
    const rightCarouselTailPromptText2 = inject("rightCarouselTailPromptText2");
    const rightCarouselTailPromptText3 = inject("rightCarouselTailPromptText3");
    const rightCarouselTailPromptText4 = inject("rightCarouselTailPromptText4");

    const isRightHeadPromptToggle = inject("isRightHeadPromptToggle");
    const isRightTailPromptToggle = inject("isRightTailPromptToggle");

    const isRightUseFileToggle = inject("isRightUseFileToggle");
    const isRightAddFileNameToggle = inject("isRightAddFileNameToggle");

    const files = ref(null);
    const uploadProgress = ref([]);
    const uploading = ref(null);

    function cleanUp() {
      clearTimeout(uploading.value);
    }

    const rightDialogHeadPrompt = ref(false);
    const rightDialogTailPrompt = ref(false);
    const rightAccountSettings = ref(false);

    onBeforeUnmount(cleanUp);

    const onDialogClose = () => {
      const userData = {
        headPromptList: [
          rightCarouselHeadPromptText1.value,
          rightCarouselHeadPromptText2.value,
          rightCarouselHeadPromptText3.value,
          rightCarouselHeadPromptText4.value,
        ],
        tailPromptList: [
          rightCarouselTailPromptText1.value,
          rightCarouselTailPromptText2.value,
          rightCarouselTailPromptText3.value,
          rightCarouselTailPromptText4.value,
        ],
        headPromptEnabled: isRightHeadPromptToggle.value,
        tailPromptEnabled: isRightTailPromptToggle.value,
      };

      $q.notify({
        spinner: true,
        message: "Saving settings...",
        timeout: 1500,
      });
      postData("UserSettings", userData)
        .then((response) => {})
        .catch((error) => {
          $q.notify({
            spinner: true,
            message: "Saving error...",
            timeout: 1500,
          });
          console.error("Error sending user settings:", error);
        });
    };

    watch(rightDialogHeadPrompt, (newVal, oldVal) => {
      if (!newVal && oldVal) {
        onDialogClose();
      } else {
      }
    });

    watch(rightDialogTailPrompt, (newVal, oldVal) => {
      if (!newVal && oldVal) {
        onDialogClose();
      } else {
      }
    });

    return {
      files,
      uploadProgress,
      uploading,

      isUploading: computed(() => uploading.value !== null),
      canUpload: computed(() => files.value !== null),

      cancelFile(index) {
        this.uploadProgress[index] = {
          ...this.uploadProgress[index],
          error: true,
          color: "orange-2",
        };
      },

      updateFiles(newFiles) {
        files.value = newFiles;
        uploadProgress.value = (newFiles || []).map((file) => ({
          error: false,
          color: "green-2",
          percent: 0,
        }));
      },

      clickRightToggleDrawer: isRightDrawerOpen,
      rightCarouselHeadPromptText1,
      rightCarouselHeadPromptText2,
      rightCarouselHeadPromptText3,
      rightCarouselHeadPromptText4,
      rightCarouselTailPromptText1,
      rightCarouselTailPromptText2,
      rightCarouselTailPromptText3,
      rightCarouselTailPromptText4,
      rightCarouselHeadPromptPosition,
      rightCarouselTailPromptPosition,
      rightDialogHeadPrompt,
      rightDialogTailPrompt,
      rightAccountSettings,
      isRightHeadPromptToggle,
      isRightTailPromptToggle,
      isRightUseFileToggle,
      isRightAddFileNameToggle,
    };
  },
  methods: {
    logout() {
      localStorage.removeItem("user-token");
      this.$router.push("/");
    },
  },
};
</script>

<style scoped>
@media (max-width: 650px) {
  .mobile-none {
    width: 600px;
    height: 800px;
  }
}
</style>
