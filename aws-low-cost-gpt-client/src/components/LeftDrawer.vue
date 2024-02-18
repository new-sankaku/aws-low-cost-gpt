<template>
  <q-drawer show-if-above v-model="clickLeftToggleDrawer" side="left" bordered>
    <div
      v-if="isChatRoomLoading"
      class="q-pa-md flex flex-center"
      style="height: 90vh"
    >
      <q-spinner-pie size="70px" color="primary" />
    </div>

    <q-list class="reverse-order" bordered separator>
      <q-item
        v-for="(chatRoom, index) in chatRooms.rooms"
        :key="'room-' + index"
        clickable
        v-ripple
        @click="showRoom(index)"
        :class="{ 'active-item': index === activeChatRoomIndex }"
      >
        <q-item-section>
          <q-item-label>{{ chatRoom.title }}</q-item-label>
        </q-item-section>
        <q-item-section side top>
          <q-item-label caption>${{ chatRoom.sumTotal }}</q-item-label>
        </q-item-section>
      </q-item>
    </q-list>
  </q-drawer>
</template>

<script>
import { ref, watch, inject } from "vue";
import { getData } from "./../api/RestService";
import { useQuasar, QSpinnerGears } from "quasar";

export default {
  setup() {
    const $q = useQuasar();

    const isLeftDrawerOpen = inject("isLeftDrawerOpen");

    const chatRooms = inject("chatRooms");
    watch(chatRooms.rooms, (newChatRooms, oldChatRooms) => {
      console.log("left watch chatRooms.rooms newChatRooms:", newChatRooms);
    });

    const activeChatRoomIndex = inject("activeChatRoomIndex");
    const showRoom = (index) => {
      activeChatRoomIndex.value = index;
    };

    const calculating = inject("calculating");
    const isChatRoomLoading = ref(false);
    return {
      isChatRoomLoading,
      calculating,
      clickLeftToggleDrawer: isLeftDrawerOpen,
      chatRooms,
      showRoom,
      activeChatRoomIndex,
    };
  },
  created() {
    this.fetchChatRoomHistory();
  },
  methods: {
    fetchChatRoomHistory() {
      this.isChatRoomLoading = true;

      getData("ChatRooms")
        .then((chatRoomsFromServer) => {
          this.chatRooms.rooms.splice(
            0,
            this.chatRooms.rooms.length,
            ...chatRoomsFromServer.map((room) => ({
              roomId: room.roomId,
              title: room.roomTitle,
              aiModelName: room.aiModel,
              sumTotal: room.sumTotal,
            }))
          );

          chatRoomsFromServer.forEach(() => {
            this.chatRooms.chatRoomHistorys.push([]);
            this.chatRooms.chatInputFields.push([]);
            this.calculating.push(false);
          });
        })
        .catch((error) =>
          console.error("Error fetching chat room history:", error)
        )
        .finally(() => {
          this.isChatRoomLoading = false;
        });
      console.log("left fetchChatRoomHistory end");
    },
  },
};
</script>

<style scoped>
.active-item {
  background-color: #c2cbff;
  color: rgb(0, 0, 0);
  font-weight: bold;
}

/* q-item の表示サイズを小さくするスタイル */
.q-item {
  font-size: 0.8rem; /* フォントサイズを小さくする */
  padding: 8px 12px; /* パディングを調整して、全体的なサイズを小さくする */
}

.reverse-order {
  display: flex;
  flex-direction: column-reverse;
}

@media (max-width: 650px) {
  .mobile-none {
    display: none;
  }
}
</style>
