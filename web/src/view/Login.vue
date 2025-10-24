<template>
  <div class="login">
    <a-row align="middle" style="height: 100vh">
      <a-col class="main" :span="6" :offset="9" >
        <div class="title">智能语音识别系统</div>
        <a-form
            :model="loginMember"
            name="normal_login"
            @finish="onFinish"
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

          <a-form-item
              name="captcha"
              :rules="[{validator: validateCaptcha,trigger: 'blur'}]"
          >
            <a-input
                v-model:value="loginMember.captcha"
                placeholder="图片验证码"
                size="large"
                class="captcha-input"
            >
              <template #prefix>
                <SafetyOutlined/>
              </template>
              <template #suffix>
                <img
                    :src="captchaImageUrl"
                    alt="图片验证码"
                    @click="refreshCaptcha"
                    style=" cursor: pointer;"
                />
              </template>
            </a-input>
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
import {reactive, computed, ref,onMounted} from 'vue';
import {useRouter} from "vue-router";
import {message} from 'ant-design-vue';
import axios from "axios";
import {hexMd5Key} from "../utils/md5.js";
import {useUserStore} from "../store/userStore.js";


const router = useRouter();
const loginMember = reactive({
  mobile: '',
  password: '',
  captchaId: '',
  captcha:''
});

const captchaImageUrl = ref('');
// 刷新验证码
const refreshCaptcha = () => {
  axios.get('/nls/web/captcha/gen').then(res => {
    console.log("获取验证码", res.data);
    captchaImageUrl.value = res.data.content.captcha;
    loginMember.captchaId = res.data.content.key;
  });
};

// 组件挂载时加载验证码
onMounted(() => {
  refreshCaptcha();
});

const validateCaptcha = async (_rule, value) => {
  if (value === '') {
    return Promise.reject('请输入图片验证码!');
  } else {
    await axios.post('/nls/web/captcha/check', {captcha: loginMember.captcha, captchaId: loginMember.captchaId}, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    }).then(res => {

      if (!res.data.success) {
        // message.error(res.data.message);
        // loginMember.captcha = '';
        return Promise.reject("结果不正确！");
      }
    })
  }
};


//登录
const onFinish = values => {
  const loginMemberParam={
    ...loginMember,
    password:hexMd5Key(loginMember.password)
  }
  axios.post('/nls/web/member/login', loginMemberParam).then(res => {
    if (res.data.success) {
       message.success("登录成功");
      const userStore = useUserStore();
      userStore.setUserInfo(res.data.content);
      router.push('/home')
    }else{
      message.error(res.data.message||"登录失败");
    }
  })
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


.login-form-forgot {
  float: right;
}

.login-form-button {
  width: 100%;
  font-weight: bold;
}

.captcha-input {
  padding: 0 11px;
}
</style>

