<template>
  <q-drawer show-if-above v-model="clickLeftToggleDrawer" side="left" bordered>
    <q-list bordered separator>
      <q-item-label header>History</q-item-label>
      <q-item
        v-for="(chatRoom, index) in chatRooms.rooms"
        :key="'room-' + index"
        clickable
        v-ripple
        @click="showRoom(index)"
        :class="{ 'active-item': index === localActiveIndex }"
      >
        <q-item-section>
          <q-item-label>{{ chatRoom.title }}</q-item-label>
          <q-item-label caption>{{ chatRoom.aiModelName }}</q-item-label>
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

export default {
  setup() {
    const isLeftDrawerOpen = inject("isLeftDrawerOpen");

    const chatRooms = inject("chatRooms");
    watch(chatRooms.rooms, (newChatRooms, oldChatRooms) => {
      console.log("left watch chatRooms.rooms newChatRooms:", newChatRooms);
    });

    const localActiveIndex = ref(0);
    const activeChatRoomIndex = inject("activeChatRoomIndex");
    const showRoom = (index) => {
      localActiveIndex.value = index;
      activeChatRoomIndex.value = index;
    };

    const calculating = inject("calculating");

    return {
      calculating,
      clickLeftToggleDrawer: isLeftDrawerOpen,
      chatRooms,
      showRoom,
      localActiveIndex,
    };
  },
  created() {
    this.fetchChatRoomHistory();
  },
  methods: {
    fetchChatRoomHistory() {
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
          this.chatRooms.chatRoomHistorys.push([]);
          this.chatRooms.chatInputFields.push([]);
          this.calculating.push(false);
        })
        .catch((error) =>
          console.error("Error fetching chat room history:", error)
        );
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

@media (max-width: 650px) {
  .mobile-none {
    display: none;
  }
}
</style>
