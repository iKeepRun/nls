<template>
  <div class="reset">
    <a-row align="middle" style="height: 100vh">
      <a-col class="main" :span="6" :offset="9" >
        <div class="title">重置密码</div>
        <a-form
            :model="resetMember"
            name="normal_reset"

            @finish="onFinish"
            @finishFailed="onFinishFailed"
        >
          <a-form-item
              name="mobile"
              :rules="[{ required: true, message: '请输入手机号!' }, { pattern: /^\d{11}$/, message: '手机号为11位数字', trigger: 'blur' }]"
          >
            <a-input v-model:value="resetMember.mobile" placeholder="手机号" size="large">
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
            <a-input-password v-model:value="resetMember.password" placeholder="密码" size="large">
              <template #prefix>
                <LockOutlined class="site-form-item-icon"/>
              </template>
            </a-input-password>
          </a-form-item>

          <a-form-item
              name="confirmPassword"
              :rules="[{validator: validateConfirmPassword}]"
          >
            <a-input-password v-model:value="resetMember.confirmPassword" placeholder="确认密码" size="large">
              <template #prefix>
                <LockOutlined class="site-form-item-icon"/>
              </template>
            </a-input-password>
          </a-form-item>

          <a-form-item>
            <a-button  type="primary" html-type="submit" size="large" class="reset-form-button">
              重置密码
            </a-button>
          </a-form-item>
        </a-form>
<!--        <p>-->
<!--          <router-link to="reset">去注册！</router-link>-->
<!--        </p>-->
      </a-col>
    </a-row>
  </div>
</template>


<script setup>
import {reactive, computed} from 'vue';
import {useRouter} from 'vue-router';
import {message} from 'ant-design-vue';


const router = useRouter();
const resetMember = reactive({
  mobile: '',
  password: '',
  confirmPassword: ''
});
const onFinish = values => {
  console.log('Success:', values);
  message.success('重置密码成功！');
  router.push('/login');
};
const onFinishFailed = errorInfo => {
  console.log('Failed:', errorInfo);
};
const disabled = computed(() => {
  return !(resetMember.mobile && resetMember.password);
});


const validateConfirmPassword = async (_rule, value) => {
  if (value === '') {
    return Promise.reject('请输入确认密码');
  } else if (value !== resetMember.password) {
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

.reset-form-button {
  width: 100%;
  font-weight: bold;
}
</style>

