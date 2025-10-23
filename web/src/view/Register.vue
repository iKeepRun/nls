<template>
  <div class="register">
    <a-row align="middle" style="height: 100vh">
      <a-col class="main" :span="6" :offset="9">
        <div class="title">开始注册</div>
        <a-form
            :model="registerMember"
            name="normal_register"
            ref="formRef"
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
              name="captcha"
              :rules="[{ required: true, message: '请输入结果!', trigger: 'blur' }]"
          >
            <a-input
                v-model:value="registerMember.captcha"
                placeholder="请输入结果"
                size="large"
                class="captcha-input"
            >
              <template #addonAfter>
                <img
                    :src="captchaImageUrl"
                    alt="图片验证码"
                    @click="refreshCaptcha"
                    style=" cursor: pointer;"
                />
              </template>
            </a-input>
          </a-form-item>


          <a-form-item
              name="code"
              :rules="[{ required: true, message: '请输入验证码!' }]"
          >
            <a-input-search
                v-model:value="registerMember.code"
                placeholder="请输入验证码"
                :enter-button="enterBtn"
                @search="onSearch"
                size="large"
                :class="{ 'countdown-disabled': isCounting }"
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
            <a-button type="primary" html-type="submit" size="large" class="register-form-button">
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
import {ref, reactive, computed,onMounted} from 'vue';
import {useRouter} from 'vue-router';
import axios from "axios";
import {message} from "ant-design-vue";

const router = useRouter();

const enterBtn = ref("获取验证码");

const formRef = ref();
const isCounting = ref(false); // 控制倒计时状态

const captchaImageUrl = ref('');

// 刷新验证码
const refreshCaptcha = () => {
  axios.get('/nls/web/member/captcha').then(res => {
    console.log("获取验证码",res.data);
    captchaImageUrl.value = res.data.content.captcha;
    registerMember.captchaId = res.data.content.key;
  });
};

// 组件挂载时加载验证码
onMounted(() => {
  refreshCaptcha();
});

const onSearch = async () => {
  // 防止重复点击
  if (isCounting.value) return;
  try {
    await formRef.value.validateFields(['mobile']);
    // isCounting.value = true;

    await axios.post('/nls/web/code/send-for-register', {mobile: registerMember.mobile}, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    }).then(res => {
      console.log(res.data);
      if (res.data.success) {
        isCounting.value = true;
        //开始60s倒计时
        let time = 59;
        enterBtn.value = "重新发送 " + time + "s";
        let timer = setInterval(() => {
          time--;
          enterBtn.value = "重新发送 " + time + "s";
          if (time <= 0) {
            clearInterval(timer)
            enterBtn.value = "获取验证码";
            isCounting.value = false;
          }
        }, 1000)
      } else {
        message.error(res.data.message);

      }
    });
  } catch (e) {
    console.log('手机号校验失败：', e);
  }

};

const registerMember = reactive({
  mobile: '',
  password: '',
  code: '',
  confirmPassword: '',
  captcha: '',
  captchaId: ''
});
const onFinish = async values => {
  console.log('Success:', values);
  console.log('registerMember', registerMember);
  await axios.post('/nls/web/member/register', registerMember).then(res => {
        console.log(res.data);

        if (res.data.success) {
          message.success('注册成功');
          router.push('/login');
        }
      }
  )
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
.countdown-disabled :deep(.ant-input-group-addon) {
  pointer-events: none; /* 禁用按钮点击事件 */
  opacity: 0.6; /* 可选：降低按钮透明度表示禁用状态 */
}

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

:deep(.ant-input-group-addon) button {
  width: 120px;
  font-size: 14px;

}

.captcha-input :deep(.ant-input-group-addon) {
  padding: 0;
}

</style>

