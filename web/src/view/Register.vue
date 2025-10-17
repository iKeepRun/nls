<template>
  <div class="register">
    <a-row align="middle" style="height: 100vh">
      <a-col class="main" :span="6" :offset="9" >
        <div class="title">开始注册</div>
        <a-form
            :model="registerMember"
            name="normal_register"

            @finish="onFinish"
            @finishFailed="onFinishFailed"
        >
          <a-form-item
              name="mobile"
              :rules="[{ required: true, message: '请输入手机号!' }, { pattern: /^\d{11}$/, message: '手机号为11位数字', trigger: 'blur' }]"
          >
            <a-input v-model:value="registerMember.mobile" placeholder="手机号" size="large">
              <template #prefix>
                <MobileOutlined class="site-form-item-icon"/>
                <!--              <UserOutlined class="site-form-item-icon"/>-->
              </template>
            </a-input>
          </a-form-item>

          <a-form-item
              name="password"
              :rules="[{ required: true, message: '请输入验证码!' }]"
          >
            <a-input-search
                v-model:value="value"
                placeholder="请输入验证码"
                enter-button="验证码"
                @search="onSearch"
                size="large"
            />
          </a-form-item>


          <a-form-item
              name="password"
              :rules="[{ required: true, message: '请输入密码!' }]"
          >
            <a-input-password v-model:value="registerMember.password" placeholder="密码" size="large">
              <template #prefix>
                <LockOutlined class="site-form-item-icon"/>
              </template>
            </a-input-password>
          </a-form-item>

          <a-form-item
              name="confirmPassword"
              :rules="[{validator: validateConfirmPassword}]"
          >
            <a-input-password v-model:value="registerMember.confirmPassword" placeholder="确认密码" size="large">
              <template #prefix>
                <LockOutlined class="site-form-item-icon"/>
              </template>
            </a-input-password>
          </a-form-item>

          <a-form-item>
            <a-button  type="primary" html-type="submit" size="large" class="register-form-button">
              注&nbsp;册
            </a-button>
          </a-form-item>
        </a-form>
<!--        <p>-->
<!--          <router-link to="register">去注册！</router-link>-->
<!--        </p>-->
      </a-col>
    </a-row>
  </div>
</template>


<script setup>
import {reactive, computed} from 'vue';
import {useRouter} from 'vue-router';

const router = useRouter();
const registerMember = reactive({
  mobile: '',
  password: '',
  confirmPassword: ''
});
const onFinish = values => {
  console.log('Success:', values);
  router.push('/login');
};
const onFinishFailed = errorInfo => {
  console.log('Failed:', errorInfo);
};
const disabled = computed(() => {
  return !(registerMember.mobile && registerMember.password);
});


const validateConfirmPassword = async (_rule, value) => {
  if (value === '') {
    return Promise.reject('请输入确认密码');
  } else if (value !== registerMember.password) {
    return Promise.reject("两次输入的密码不匹配!");
  } else {
    return Promise.resolve();
  }
};
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

.register-form-button {
  width: 100%;
  font-weight: bold;
}
</style>

