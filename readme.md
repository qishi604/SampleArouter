# ARouter 使用

## ARouter 使用者最关心几个问题

1. Activity 传递参数跳转，并接收数据回传

2. 拦截器的使用（处理登录、埋点等逻辑）

3. 提供服务（可以用于模块间 API 调用）

## 接入步骤

1. 在 app 的 `build.gradle` 添加依赖

```
android {
    defaultConfig {
        ...
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [AROUTER_MODULE_NAME: project.getName()]
            }
        }
    }
}

dependencies {
    // 替换成最新版本, 需要注意的是api
    // 要与compiler匹配使用，均使用最新版可以保证兼容
    compile 'com.alibaba:arouter-api:x.x.x'
    annotationProcessor 'com.alibaba:arouter-compiler:x.x.x'
    ...
}
```

2. 初始化 SDK

注意：最好在 Application 中初始化，debug 模式下开启调试模式

```
private void initARouter(Application app) {
    // 开启调试必须在 init 之前调用，否则会无效
    if (BuildConfig.DEBUG) {
        ARouter.openLog(); // 开启日志
        ARouter.openDebug(); // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
    }

    ARouter.init(app);
}
```

3. 添加注解

```

```

4. 发起路由操作

5. 混淆规则（proguard）

```
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

# 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

# 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
# -keep class * implements com.alibaba.android.arouter.facade.template.IProvider
```

6. 使用 gradle 插件实现路由表自动加载（可选）

project `build.gradle`

```
buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath "com.alibaba:arouter-register:1.0.2"
    }
}
```

module `build.gradle`

```
apply plugin: 'com.alibaba.arouter'
```

##