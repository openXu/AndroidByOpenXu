package runable

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import java.io.Closeable
import java.util.*
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class AuthUser(val name: String) : AbstractCoroutineContextElement(AuthUser) {
    companion object Key : CoroutineContext.Key<AuthUser>
}
/**定义一个协程作用域*/
class MyCoroutineScope(context: CoroutineContext) : CoroutineScope {
    override val coroutineContext: CoroutineContext = context
}


@ExperimentalStdlibApi
fun main() {
    runBlocking{}

//    println("--1 $scope 的上下文${scope.coroutineContext}")
    val job = GlobalScope.launch {
        println("--2 $this 的上下文${this.coroutineContext}")
        println("--父协程的上下文${coroutineContext}")
        coroutineScope{

        }
        withContext(Dispatchers.IO){
            println("--3 $this 的上下文${this.coroutineContext}")
            println("----子协程的上下文${coroutineContext}")
            //开启子协程，上下文 = 父协程上下文EmptyCoroutineContext+Dispatchers.IO
        }

        withContext(Dispatchers.IO){
            println("--3 $this 的上下文${this.coroutineContext}")
            println("----子协程的上下文${coroutineContext}")
            //开启子协程，上下文 = 父协程上下文EmptyCoroutineContext+Dispatchers.IO
        }
    }

    println("job = $job")
/*
    println("主线程${Thread.currentThread()}")  //Thread[main,5,main]
    //父协程，协程上下文 = 授权信息 + 执行线程 + Job
    val scope = CloseableCoroutineScope(AuthUser("openXu") + Dispatchers.Default)
    scope.launch {
        println("1 父协程执行线程 ${Thread.currentThread()}")  //Thread[DefaultDispatcher-worker-2,5,main]
        println("1 scope上下文 ${scope.coroutineContext}")  //[runable.AuthUser@7410ac0c, Dispatchers.Default]
        //★★★launch创建协程会继承scope的上下文，并且会+上协程的Job
        println("1 父协程上下文 $coroutineContext") //[runable.AuthUser@7410ac0c, StandaloneCoroutine{Active}@5d1945ee, Dispatchers.Default]
        //创建一个作用域用来分解工作
        coroutineScope {
            //★★★继承父协程的上下文，但是子协程有自己的Job，所以覆盖掉了父协程的Job
            println("--2 子协程上下文${coroutineContext}") //[runable.AuthUser@7410ac0c, ScopeCoroutine{Active}@4bb0740f, Dispatchers.Default]
            println("--2 子协程上下文元素${coroutineContext[AuthUser]}") //runable.AuthUser@7410ac0c
            println("--2 子协程上下文元素${coroutineContext[CoroutineDispatcher]}") //Dispatchers.Default
        }
        withContext(Dispatchers.IO){
            println("--3 子协程执行线程 ${Thread.currentThread()}")  //Thread[DefaultDispatcher-worker-1,5,main]
            //★★★继承父协程的上下文，但是子协程有自己的Job，所以覆盖掉了父协程的Job
            println("--3 子协程上下文${coroutineContext}") //[runable.AuthUser@7410ac0c, DispatchedCoroutine{Active}@36ac94e0, Dispatchers.IO]
            if(coroutineContext[AuthUser]!=null){
                when(coroutineContext[AuthUser]!!.name){
                    "openXu" -> println("√√√恭喜${coroutineContext[AuthUser]!!.name}认证通过了") //√√√恭喜openXu认证通过了
                    else -> println("×××${coroutineContext[AuthUser]!!.name}认证失败了")
                }
            }else{
                println("没有授权上下文")
            }
        }
        println("<<<<<<协程执行完毕 ${scope.coroutineContext}")
    }
    Timer().schedule(object:TimerTask(){
        override fun run() {
            println("-----------------------------3s时间到----------------------------")
            println("协程是否执行完毕？${scope.coroutineContext}")
            println("协程是否活动？${scope.isActive}")
            scope.cancel("取消协程，生命周期结束")

        }
    }, 3000)*/
    Thread.sleep(100000)  //阻止jvm退出
}
