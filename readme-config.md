## ARouter 配置

1. 在 module (如app)  `build.gradle` 添加依赖。注意，如果有多个 module 用到ARouter注解，每个 module 都需要添加

```
android {
    defaultConfig {
        ...
        // 用到注解的需要加
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [AROUTER_MODULE_NAME: project.getName()]
            }
        }
    }
}

dependencies {
    // 这个最好放到基础库，如果基础库已经有，module这里就不用再添加
    api 'com.alibaba:arouter-api:x.x.x'
    
    // 用到注解的需要加依赖
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

3. 混淆规则（proguard）

```
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

# 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

# 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
# -keep class * implements com.alibaba.android.arouter.facade.template.IProvider
```

4. 使用 gradle 插件实现路由表自动加载（可选）

    可选使用，通过 ARouter 提供的注册插件进行路由表的自动加载(power by [AutoRegister](https://github.com/luckybilly/AutoRegister))， 默认通过扫描 dex 的方式
    进行加载通过 gradle 插件进行自动注册可以缩短初始化时间解决应用加固导致无法直接访问
    dex 文件，初始化失败的问题，需要注意的是，该插件必须搭配 api 1.3.0 以上版本使用！

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

5. 生成路由文档

module `build.gradle`

```

// 添加参数 AROUTER_GENERATE_DOC = enable
// 生成的文档路径 : build/generated/source/apt/(debug or release)/com/alibaba/android/arouter/docs/arouter-map-of-${moduleName}.json
      

android {
    defaultConfig {
        ...
        // 用到注解的需要加
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [AROUTER_MODULE_NAME: project.getName(), AROUTER_GENERATE_DOC: "enable"]
            }
        }
    }
}
```