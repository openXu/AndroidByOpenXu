package com.openxu.runable;


import java.io.IOException;

/**
 * Author: openXu
 * Time: 2021/1/13 16:37
 * class: Test
 * Description:
 */


class Test {

    public static void main(String[] args) {
        try {
            getResult();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getResult() throws IOException {
        if(true)
            throw new RuntimeException("自定义异常");
        return 1;
    }

}


/*
    public final static main()V
    LINENUMBER 38 L0
        //① 访问GlobalScope的静态属性INSTANCE
        GETSTATIC kotlinx/coroutines/GlobalScope.INSTANCE : Lkotlinx/coroutines/GlobalScope;
        CHECKCAST kotlinx/coroutines/CoroutineScope  //检查普通对象类型
        ACONST_NULL //② 将null推送至栈顶，对应SuspendLambda的第一个构造参数
        ACONST_NULL //③ 将null推送至栈顶，对应SuspendLambda的第二个构造参数（续体）
        //internal abstract class SuspendLambda(
        //    public override val arity: Int,
        //    completion: Continuation<Any?>?
        //)
        NEW runable/SuspendKt$main$1  //④ 构造方法new SuspendLambda的对象
        DUP   //复制栈顶数值(也就是刚刚创建的SuspendLambda的对象)并将复制值压入栈顶
        ACONST_NULL  //⑤ 将null推送至栈顶，对应SuspendLambda的第一个构造参数
        //调用超类构造方法，实例初始化方法，私有方法初始化SuspendLambda
        INVOKESPECIAL runable/SuspendKt$main$1.<init> (Lkotlin/coroutines/Continuation;)V
        CHECKCAST kotlin/jvm/functions/Function2   //将SuspendLambda的对象转换为Function2
        ICONST_3      //⑥ 将int型(3)推送至栈顶
        ACONST_NULL   //⑦ 将null推送至栈顶
        INVOKESTATIC kotlinx/coroutines/BuildersKt.launch$default (
                Lkotlinx/coroutines/CoroutineScope;   //参数1：GlobalScope.INSTANCE
                Lkotlin/coroutines/CoroutineContext;  //
                Lkotlinx/coroutines/CoroutineStart;   //
                Lkotlin/jvm/functions/Function2;
                ILjava/lang/Object;) Lkotlinx/coroutines/Job;
        POP*/

  /*  public static final void main() {
        //通过GlobalScope.launch{}启动父协程
        //    context: CoroutineContext = EmptyCoroutineContext,
        //    start: CoroutineStart = CoroutineStart.DEFAULT,
        //    block: suspend CoroutineScope.() -> Unit
        BuildersKt.launch$default(
                (CoroutineScope)GlobalScope.INSTANCE,
                (CoroutineContext)null,   //param1: context: CoroutineContext = EmptyCoroutineContext,
                (CoroutineStart)null,     //param2: start: CoroutineStart = CoroutineStart.DEFAULT,
                //param3: block: suspend CoroutineScope.() -> Unit
                //SuspendLambda匿名子类对象
                (Function2)(new Function2((Continuation)null) {

            @NotNull
            public final Continuation create(@Nullable Object value, @NotNull Continuation completion) {
                Intrinsics.checkNotNullParameter(completion, "completion");
                Function2 var3 = new <anonymous constructor>(completion);
                var3.p$ = (CoroutineScope)value;
                return var3;
            }
            //执行协程时，传递参数var1， var2
            public final Object invoke(Object var1, Object var2) {
                return ((<undefinedtype>)this.create(var1, (Continuation)var2)).invokeSuspend(Unit.INSTANCE);
            }

            private CoroutineScope p$;    //父协程作用域对象
            Object L$0;
            int label;

            @Nullable
            public final Object invokeSuspend(@NotNull Object $result) {
                Object var5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                Object var10000;
                CoroutineScope $this$launch;
                switch(this.label) {
                    case 0:
                        ResultKt.throwOnFailure($result);
                        $this$launch = this.p$;
                        CoroutineContext var6 = (CoroutineContext)Dispatchers.getIO();
                        Function2 var10001 = (Function2)(new Function2((Continuation)null) {
                            private CoroutineScope p$;
                            Object L$0;
                            int label;

                            @Nullable
                            public final Object invokeSuspend(@NotNull Object $result) {
                                Object var3 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                CoroutineScope $this$withContext;
                                switch(this.label) {
                                    case 0:
                                        ResultKt.throwOnFailure($result);
                                        $this$withContext = this.p$;
                                        this.L$0 = $this$withContext;
                                        this.label = 1;
                                        if (DelayKt.delay(1000L, this) == var3) {
                                            return var3;
                                        }
                                        break;
                                    case 1:
                                        $this$withContext = (CoroutineScope)this.L$0;
                                        ResultKt.throwOnFailure($result);
                                        break;
                                    default:
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }

                                return "子协程的返回值";
                            }

                            @NotNull
                            public final Continuation create(@Nullable Object value, @NotNull Continuation completion) {
                                Intrinsics.checkNotNullParameter(completion, "completion");
                                Function2 var3 = new <anonymous constructor>(completion);
                                var3.p$ = (CoroutineScope)value;
                                return var3;
                            }

                            public final Object invoke(Object var1, Object var2) {
                                return ((<undefinedtype>)this.create(var1, (Continuation)var2)).invokeSuspend(Unit.INSTANCE);
                            }
                        });
                        this.L$0 = $this$launch;
                        this.label = 1;
                        var10000 = BuildersKt.withContext(var6, var10001, this);
                        if (var10000 == var5) {
                            return var5;
                        }
                        break;
                    case 1:
                        $this$launch = (CoroutineScope)this.L$0;
                        ResultKt.throwOnFailure($result);
                        var10000 = $result;
                        break;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }

                String result = (String)var10000;
                boolean var4 = false;
                System.out.println(result);
                return Unit.INSTANCE;
            }

        }), 3, (Object)null);
        Thread.sleep(5000L);
    }*/