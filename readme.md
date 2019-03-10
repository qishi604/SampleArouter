# ARouter 使用指南

## [ARouter 配置](readme-config.md)

## ARouter 的使用

### Activity 传递参数跳转，并接收数据回传

1. Activity 添加注解, ***注意，path 至少需要两级，/xxx/xxx***

```java
@Route(path = "/user/detail")
public class UserDetailActivity extends BaseActivity {
    // 通过 name 来映射参数名，如果不指定name，默认以属性名来作为参数名
    @Autowired(name = "id")
    String mUserId;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 在使用 @Autowired 标注属性之前，调用 inject 方法
        ARouter.getInstance().inject(this);
        
        Log.d("param", "user id: " + mUserId);
    }
}
```

2. 发起路由操作，并在 onActivityResult 接收结果

```java
public class MainActivity extends BaseActivity {

    private void nav() {
        ARouter.getInstance() // 获取ARouter 单例
            .build("/user/detail") // 目标 Activity path
            .withString("id", "10001") // withXxx 方法设置参数，数据类型与Android 的 Bundle 一一对应
            //.navigation() // 简单的跳转
            .navigation(this, 11) // 设置Activity 和 request_code 类似于 startActivityForResult()，这样就可以在 onActivityResult 中接收数据
        ;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       
        if (resultCode != RESULT_OK && requestCode == 11) {
            // 这里获取到数据，直接跳转到用户详情
            data.getSerializableExtra("data");
            
        }
    }
}
```


### 拦截器的使用（处理登录、埋点等逻辑）

拦截器通过 aop 方式自动注册，只需要实现 `IInterceptor` 接口即可。

```java
@Interceptor(priority = 8, name = "登录拦截器")
public class LoginInterceptor implements IInterceptor {

    private boolean mIsLogin;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        final String path = postcard.getPath();
        // 可以通过 path 来过滤拦截。具体写法可以参考 demo 源码
        
        if (mIsLogin) {
            callback.onContinue(postcard);
            return;
        } else {
            showLogin();
        }
    }

    /**
     * 拦截器被初始化时调用，只会调用一次
     * 即ARouter.init(Application) 的时候会调用
     * @param context 这个 context 就是 Application
     */
    @Override
    public void init(Context context) {
    }
}
```

### 通过依赖注入解耦（可以用于组件间通信）

```java
// 声明接口，其他组件通过接口来调用服务
public interface IApp extends IProvider {

    Application getApp();
}

// 实现接口
@Route(path = "/service/app")
public class AppImpl implements IApp {

    private Application mApp;

    @Override
    public Application getApp() {
        return mApp;
    }

    @Override
    public void init(Context context) {
        mApp = (Application) context;
    }
}
```

使用服务

```java
public final class ToastManager {

    private Application mContext;
    
    // 使用依赖注入的方式发现服务，通过注解标注字段，inject 调用之后即可使用，无需手动获取
    @Autowired
    IApp mApp;

    private static class Holder {
        private static final ToastManager INSTANCE = new ToastManager();
    }

    public static ToastManager getInstance() {
        return Holder.INSTANCE;
    }

    private ToastManager() {
        ARouter.getInstance().inject(this);
    }

    private Application getContext() {
        if (null == mContext) {
            mContext = mApp.getApp();
            
            // 使用依赖查找的方式发现服务，这种方式不需要调用 inject
            // IApp iApp = ARouter.getInstance().navigation(IApp.class); 
            // IApp iApp = (IApp) ARouter.getInstance().build("/service/app").navigation();
            // iApp.getApp();
        }

        return mContext;
    }

    public void toast(CharSequence text, int duration) {
        Toast.makeText(getContext(), text, duration).show();
    }
}

```

----

### 其他姿势

1. 通过URL跳转
``` java
// 新建一个Activity用于监听Schame事件,之后直接把url传递给ARouter即可
public class SchameFilterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Uri uri = getIntent().getData();
    ARouter.getInstance().build(uri).navigation();
    finish();
    }
}
```

AndroidManifest.xml
``` xml
<activity android:name=".activity.SchameFilterActivity">
    <!-- Schame -->
    <intent-filter>
        <data
        android:host="sc.com"
        android:scheme="router"/>

        <action android:name="android.intent.action.VIEW"/>

        <category android:name="android.intent.category.DEFAULT"/>
        <category android:name="android.intent.category.BROWSABLE"/>
    </intent-filter>
</activity>
```

2. 自定义全局降级策略
``` java
// 实现DegradeService接口，并加上一个Path内容任意的注解即可
@Route(path = "/xxx/xxx")
public class DegradeServiceImpl implements DegradeService {
    @Override
    public void onLost(Context context, Postcard postcard) {
        // do something.
    }

    @Override
    public void init(Context context) {
    
    }
}
```

3. 为目标页面声明更多信息
``` java
// 我们经常需要在目标页面中配置一些属性，比方说"是否需要登陆"之类的
// 可以通过 Route 注解中的 extras 属性进行扩展，这个属性是一个 int值，换句话说，单个int有4字节，也就是32位，可以配置32个开关
// 剩下的可以自行发挥，通过字节操作可以标识32个开关，通过开关标记目标页面的一些属性，在拦截器中可以拿到这个标记进行业务逻辑判断
@Route(path = "/xxx/activity", extras = Consts.XXXX)
```

4. 获取原始的URI
``` java
String uriStr = getIntent().getStringExtra(ARouter.RAW_URI);
```

5. 重写跳转URL
``` java
// 实现PathReplaceService接口，并加上一个Path内容任意的注解即可
@Route(path = "/xxx/xxx") // 必须标明注解
public class PathReplaceServiceImpl implements PathReplaceService {
    /**
    * For normal path.
    *
    * @param path raw path
    */
    String forString(String path) {
        return path;    // 按照一定的规则处理之后返回处理后的结果
    }

    /**
    * For uri type.
    *
    * @param uri raw uri
    */
    Uri forUri(Uri uri) {
        return url;    // 按照一定的规则处理之后返回处理后的结果
    }
}
```

6. 路由中的分组概念

- SDK中针对所有的路径(/test/1 /test/2)进行分组，分组只有在分组中的某一个路径第一次被访问的时候，该分组才会被初始化
- 可以通过 @Route 注解主动指定分组，否则使用路径中第一段字符串(/*/)作为分组
- 注意：一旦主动指定分组之后，应用内路由需要使用 ARouter.getInstance().build(path, group) 进行跳转，手动指定分组，否则无法找到
``` java
@Route(path = "/test/1", group = "app")
```

7. 拦截器和服务的异同

- 拦截器和服务所需要实现的接口不同，但是结构类似，都存在 init(Context context) 方法，但是两者的调用时机不同
- 拦截器因为其特殊性，会被任何一次路由所触发，拦截器会在ARouter初始化的时候异步初始化，如果第一次路由的时候拦截器还没有初始化结束，路由会等待，直到初始化完成。
- 服务没有该限制，某一服务可能在App整个生命周期中都不会用到，所以服务只有被调用的时候才会触发初始化操作


### 详细的API说明

``` java
// 构建标准的路由请求
ARouter.getInstance().build("/home/main").navigation();

// 构建标准的路由请求，并指定分组
ARouter.getInstance().build("/home/main", "ap").navigation();

// 构建标准的路由请求，通过Uri直接解析
Uri uri;
ARouter.getInstance().build(uri).navigation();

// 构建标准的路由请求，startActivityForResult
// navigation的第一个参数必须是Activity，第二个参数则是RequestCode
ARouter.getInstance().build("/home/main", "ap").navigation(this, 5);

// 直接传递Bundle
Bundle params = new Bundle();
ARouter.getInstance()
    .build("/home/main")
    .with(params)
    .navigation();

// 指定Flag
ARouter.getInstance()
    .build("/home/main")
    .withFlags();
    .navigation();

// 获取Fragment
Fragment fragment = (Fragment) ARouter.getInstance().build("/test/fragment").navigation();
                    
// 对象传递
ARouter.getInstance()
    .withObject("key", new TestObj("Jack", "Rose"))
    .navigation();

// 觉得接口不够多，可以直接拿出Bundle赋值
ARouter.getInstance()
        .build("/home/main")
        .getExtra();

// 转场动画(常规方式)
ARouter.getInstance()
    .build("/test/activity2")
    .withTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
    .navigation(this);

// 转场动画(API16+)
ActivityOptionsCompat compat = ActivityOptionsCompat.
    makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);

// ps. makeSceneTransitionAnimation 使用共享元素的时候，需要在navigation方法中传入当前Activity

ARouter.getInstance()
    .build("/test/activity2")
    .withOptionsCompat(compat)
    .navigation();
        
// 使用绿色通道(跳过所有的拦截器)
ARouter.getInstance().build("/home/main").greenChannel().navigation();

// 使用自己的日志工具打印日志
ARouter.setLogger();

// 使用自己提供的线程池
ARouter.setExecutor();
```

## Q&A

1. 通过 URL跳转之后，在 intent 中拿不到参数如何解决？

需要注意的是，如果不使用自动注入，可以不写 `ARouter.getInstance().inject(this)`，但是需要取值的字段仍然需要标上 `@Autowired` 注解。因为
只有标上注解之后，`ARouter` 才能知道以哪一种数据类型提取 URL 中的参数并放入 Intent 中，这样才能在 Intent 中获取到对应的参数

2. Fragment 如何接收数据回传？

`ARouter` 并不提供数据回传的接口，这个需要我们自己实现。如 `Activity` 可以在`onActivityResult`接收。
由于`ARouter` 没有`navigation(Fragment)`这种接口，所以我们可以在 `Activity.onActivityResult` 接收
数据，然后再回传给 Fragment。

```
// 可以在 BaseActivity 添加如下代码
@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    List<Fragment> fragments = getSupportFragmentManager().getFragments();
    if (fragments != null && fragments.size() > 0) {
        for (Fragment fragment : fragments) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
```

也可以通过 `EventBus` 回传数据，需要注意数据类型最好能保持唯一，以免对其他页面产生影响。

其他问题请到 [ARouter issues](https://github.com/alibaba/ARouter/issues) 里查找


## 参考

- [ARouter](https://github.com/alibaba/ARouter)
- [JIMU](https://github.com/mqzhangw/JIMU)

