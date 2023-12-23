<template>
  <div class="header-container">
    <div class="icon-container" @click="collapse">
      <i :class="icon" class="header-icon"></i>
    </div>
    <div class="title-container">
      <span class="header-title">Stack Overflow Q&A Data</span>
    </div>
  </div>
</template>

<style scoped>
.header-container {
  display: flex;
  align-items: center;
  height: 60px;
  padding: 0 20px;
  background-color: #4285f4;
  color: #fff;
}

.icon-container {
  cursor: pointer;
}

.header-icon {
  font-size: 24px;
}

.title-container {
  flex: 1;
  text-align: center;
}

.header-title {
  font-size: 24px;
  font-weight: bold;
}
</style>

<script>
export default {
  name: "Header",
  methods: {
    toUser() {
      console.log("to_user");
    },
    logOut() {
      console.log("log_out");
    },
    collapse() {
      this.$emit("doCollapse");
    },
  },
  props: {
    icon: String,
  },
};
</script>
