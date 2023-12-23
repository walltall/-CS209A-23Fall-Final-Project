<template>
  <el-menu
    style="height: 100%; border-right: none;"
    background-color="#304156"
    text-color="#bfcbd9"
    active-text-color="#ffd04b"
    default-active="/"
    :collapse="isCollapse"
    :collapse-transition="false"
  >
    <router-link :to="'/'">
      <el-menu-item :index="'/'" :width="width">
        <i class="el-icon-s-home"></i>
        <span slot="title" class="menu-item-title">话题热度</span>
      </el-menu-item>
    </router-link>

    <router-link :to="'/topic'">
      <el-menu-item :index="'/topic'" :width="width">
        <i class="el-icon-s-flag"></i>
        <span slot="title" class="menu-item-title">话题热度</span>
      </el-menu-item>
    </router-link>

    <router-link :to="'/bug'">
      <el-menu-item :index="'/bug'" :width="width">
        <i class="el-icon-s-flag"></i>
        <span slot="title" class="menu-item-title">Bug热度</span>
      </el-menu-item>
    </router-link>

    <router-link :to="'/phrase'">
      <el-menu-item :index="'/phrase'" :width="width">
        <i class="el-icon-s-flag"></i>
        <span slot="title" class="menu-item-title">Phrase搜索</span>
      </el-menu-item>
    </router-link>
  </el-menu>
</template>

<style scoped>
.menu-item-title {
  text-align: center;
  font-size: 14px;
  margin-left: 10px; /* Adjust the margin for better spacing */
}
</style>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean,
    width: '100px'
  }
};
</script>
