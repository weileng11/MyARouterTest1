# MyARouterTest
使用阿里Arouter路由实现模块化开发简介

**lib 说明：lib_base 公共的包**

         **app:程序的入口**

         **lib_icon 图片**

         **module_chat 测试模块服务调用以及回调 微聊**

         **module_home 主要演示 首页**

         **module_me 演示module,我的**

         **module_recom 无 推荐**


**包含:1.登录（跨模块跳转Activity）**
     **2.使用eventBus夸模块通信 (lib_base)**
     **3.使用url应用跳转，并使用eventBus返回数据**
     **4.旧版本转场动画**
     **5.新版本转场动画**
     **6.通过URL跳转（webview）**
     **7.如果利用重新分组，就需要在build中进行指定的分组不然没有效果**
     **8.拦截器操作(利用原有分组)**
     **9.拦截器操作(绿色通道，跳过拦截器)**
     **10.必须先初始化JsonServiceImpl实现SerializationService
        配置说明:build必须配置,否则找不到JsonServiceImpl testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
                     javaCompileOptions {
                         annotationProcessorOptions {
                             arguments = [moduleName: project.getName()]
                         }**
      **11.//模块间服务调用**
        //例如home模块调用chat模块的方法**
      **12.模块间通过路径名称调用服务**
      **13.模块间通过类名调用服务**
      **14.跳转失败**

**路由使用: 1.// 替换成最新版本, 需要注意的是api
         // 要与compiler匹配使用，均使用最新版可以保证兼容
         compile 'com.alibaba:arouter-api:x.x.x'
         annotationProcessor 'com.alibaba:arouter-compiler:x.x.x'
         **2.if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
               ARouter.openLog();     // 打印日志
               ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
           }
           ARouter.init(mApplication); // 尽可能早，推荐在Application中初始化
        **3.
        // 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
        ARouter.getInstance().build("/test/activity").navigation();

        **// 2. 跳转并携带参数
        ARouter.getInstance().build("/test/1")
            .withLong("key1", 666L)
            .withString("key3", "888")
            .withObject("key4", new Test("Jack", "Rose"))
            .navigation();
        详情见demo


**bulid 配置
      **1.if (!isNeedRecomModule.toBoolean()) {
            apply plugin: 'com.android.application'
        } else {
            apply plugin: 'com.android.library'
        }
       **2. testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
                 javaCompileOptions {
                     annotationProcessorOptions {
                         arguments = [ moduleName : project.getName() ]
                     }
                 }
                 //打包时需要用到
        **3. javaCompileOptions {
                      annotationProcessorOptions {
                          arguments = [moduleName: project.getName()]
                      }
                  }
         **4. compile project(':lib_base')
            compile project(':lib_icon')
         **5.gradle.properties 设置是module还是app



**常见问题:
**1.There's no router matched!
在组件化开发过程中，我在宿主App模块引用模块C中的Activity，一直不能成功，界面及日志提示"W/ARouter::: ARouter::There is no route match the path [/xxx/xxx], in group [xxx][ ]"
最终定位原因是需要在宿主App的build.gradle中，把需要路由的模块（上例中的模块C）引入进来
//builde.gradle文件
compile project(":moudlec")
compile project(":moudled")
在组件化开发中，各模块建议不要有依赖关系。宿主App在打包编译时可依赖各组件，组件之间可以使用ARouter进行界面跳转。
上例中模块c和d之间无依赖关系，但是再宿主app中可以进行跳转。

**2.ARouter::Extract the default group failed
**根据github的使用文档，路由路径至少需要有两级：/xx/xx。
**使用路由启动Activity时，犯了一个错误，路径名称忘记写首字符/，导致出现了如下报错，引以为戒。

