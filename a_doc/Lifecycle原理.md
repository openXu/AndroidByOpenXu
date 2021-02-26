
https://mp.weixin.qq.com/s/SCNWCz9ZEIOwio9v-Tx0fA
https://blog.csdn.net/petterp/article/details/106771203
https://mp.weixin.qq.com/s/VBMDIE0QHXQAMuIjon-Fjg
https://guolin.blog.csdn.net/article/details/109787732

https://developer.android.google.cn/training/dependency-injection/manual

## Lifecycle的使用

### 引入依赖

如果要在非androidX项目中使用`Lifecycle`，可通过下面方式引入依赖：

```xml
implementation "android.arch.lifecycle:extensions:1.1.1"
```

现在的项目中基本都已经完成了androidX的迁移，如果已经迁移到androidX，可以通过下面的方式引入：

```xml
implementation 'androidx.appcompat:appcompat:1.2.0'
```

`appcompat`依赖了`androidx.fragment`，而androidx.fragment又依赖了`ViewModel`和`LiveData`，`LiveData`内部又依赖了`Lifecycle`，这样通过一行依赖配置就将Android架构组件（AAC）中最重要的几个库引入依赖了。

当然，我们也可以根据需求单独引入依赖：

```xml
//根目录的 build.gradle
repositories {
    google()
    ...
}

//app的build.gradle
dependencies {
    def lifecycle_version = "2.2.0"
    def arch_version = "2.1.0"

    // ViewModel
    implementation "androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version"
    // LiveData
    implementation "androidx.lifecycle:lifecycle-livedata:$lifecycle_version"
    // 只有Lifecycles (不带 ViewModel or LiveData)
    implementation "androidx.lifecycle:lifecycle-runtime:$lifecycle_version"

    // Saved state module for ViewModel
    implementation "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version"

    // lifecycle注解处理器
    annotationProcessor "androidx.lifecycle:lifecycle-compiler:$lifecycle_version"
    // 替换 - 如果使用Java8,就用这个替换上面的lifecycle-compiler
    implementation "androidx.lifecycle:lifecycle-common-java8:$lifecycle_version"

    //以下按需引入
    // 可选 - 帮助实现Service的LifecycleOwner
    implementation "androidx.lifecycle:lifecycle-service:$lifecycle_version"
    // 可选 - ProcessLifecycleOwner给整个 app进程 提供一个lifecycle
    implementation "androidx.lifecycle:lifecycle-process:$lifecycle_version"
    // 可选 - ReactiveStreams support for LiveData
    implementation "androidx.lifecycle:lifecycle-reactivestreams:$lifecycle_version"
    // 可选 - Test helpers for LiveData
    testImplementation "androidx.arch.core:core-testing:$arch_version"
}
```









