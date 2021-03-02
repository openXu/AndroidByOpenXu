Kotlin Android Extensions sythentics


## findViewById

项目框架中推荐使用DataBinding， 如果要穿插使用sythentics，请一定要小心空指针

- ButterKnife已经退出历史舞台
- Kotlin Android Extensions sythentics：它是一个Kotlin插件，使用它可以非常方便的在Activities, Fragments, 和自定义的Views 中无缝的绑定View。此外绑定View有缓存，多次获取的速度更快。
- View Binding：比synthetic更加强大，而且比synthetic更有优势
- Data Binding比View Binding功能更多

View Binding的优势：

- 类型安全(Type-safe)：Layout中id对应的View和代码中View属性的类型是一致的。
- 空安全(Null-safe)：如果Layout中的View在某个配置情况下是不存在的，那代码中View属性的就会加上@Nullable。
- 只引用当前Layout的View，避免项目中存在相同id时，引用出错导致空指针。synthetic却能随便引用
- 代码量与synthetic差不多
- 同时支持Kotlin和Java

sythentics存在的问题：

layout中不可跨模块使用include，编译报错：Unresolved reference: xxx
多个layout中有相同ID的View时可能导致导包引用错误


