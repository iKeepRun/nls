<template>
  <a-layout>
    <Header/>
    <a-layout>
      <Sider/>
      <a-layout style="padding: 0 24px 24px">
        <a-breadcrumb style="margin: 16px 0">
          <a-breadcrumb-item>Home</a-breadcrumb-item>
          <a-breadcrumb-item>List</a-breadcrumb-item>
          <a-breadcrumb-item>App</a-breadcrumb-item>
        </a-breadcrumb>
        <a-layout-content
            :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
        >
          Content
        </a-layout-content>
      </a-layout>
    </a-layout>
  </a-layout>
</template>

<script setup>
import Header from "../components/Header.vue";
import Sider from "../components/Sider.vue";
import  axios from "axios";

axios.get('/nls/test').then(res => {
  console.log(res.data)
})
</script>
<style scoped>

</style>