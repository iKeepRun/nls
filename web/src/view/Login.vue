<template>
  <div class="login">
    <a-row align="middle" style="height: 100vh">
      <a-col class="main" :span="6" :offset="9" >
        <div class="title">智能语音识别系统</div>
        <a-form
            :model="loginMember"
            name="normal_login"

            @finish="onFinish"
            @finishFailed="onFinishFailed"
        >
          <a-form-item
              name="mobile"
              :rules="[{ required: true, message: '请输入手机号!' }]"
          >
            <a-input v-model:value="loginMember.mobile" placeholder="手机号" size="large">
              <template #prefix>
                <MobileOutlined class="site-form-item-icon"/>
                <!--              <UserOutlined class="site-form-item-icon"/>-->
              </template>
            </a-input>
          </a-form-item>

          <a-form-item
              name="password"
              :rules="[{ required: true, message: '请输入密码!' }]"
          >
            <a-input-password v-model:value="loginMember.password" placeholder="密码" size="large">
              <template #prefix>
                <LockOutlined class="site-form-item-icon"/>
              </template>
            </a-input-password>
          </a-form-item>

          <a-form-item>
            <a-button  type="primary" html-type="submit" size="large" class="login-form-button">
              登&nbsp;录
            </a-button>
          </a-form-item>
        </a-form>
        <p>
          <router-link to="register">去注册！</router-link>
          <router-link to="reset" class="login-form-forgot">忘记密码</router-link>
        </p>
      </a-col>
    </a-row>
  </div>
</template>


<script setup>
import {reactive, computed} from 'vue';

const loginMember = reactive({
  mobile: '',
  password: '',
});
const onFinish = values => {
  console.log('Success:', values);
};
const onFinishFailed = errorInfo => {
  console.log('Failed:', errorInfo);
};
const disabled = computed(() => {
  return !(loginMember.mobile && loginMember.password);
});
</script>

<style scoped>
.main {
  background-color: #f0f0f0;
  padding: 80px;
  border-radius: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
.title {
  font-size: 1.8rem;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20px;
}


.login-form-forgot {
  float: right;
}

.login-form-button {
  width: 100%;
  font-weight: bold;
}
</style>

