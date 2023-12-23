<template>

    <el-container style="display: flex; flex-direction: column; align-items: center;">
      <div class="block">
        <el-pagination
            layout="prev, pager, next"
            :total="1000">
        </el-pagination>
      </div>
    </el-container>
</template>

<style scoped>

</style>

<script>
export default {
  name: "Foot",
}
</script>