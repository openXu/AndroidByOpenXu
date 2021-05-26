package runable
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.*
import kotlin.concurrent.schedule
import kotlin.coroutines.*

/**
 * Author: openXu
 * Time: 2021/3/29 9:39
 * class: zhengti
 * Description:
 */

class MyContinuationInterceptor :
        AbstractCoroutineContextElement(ContinuationInterceptor),
        ContinuationInterceptor {
    @ExperimentalStdlibApi
    companion object Key : AbstractCoroutineContextKey<ContinuationInterceptor, MyContinuationInterceptor>(
            ContinuationInterceptor, { it as? MyContinuationInterceptor })
    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        val newContinuation = LogContinuation(continuation)
        println("自定义续体拦截器，将原始续体对象$continuation 转换为 $newContinuation")
        return newContinuation
    }
}
//新续体
private class LogContinuation<in T>(val cont: Continuation<T>) : Continuation<T> {
    override val context: CoroutineContext = cont.context
    override fun resumeWith(result: Result<T>) {
        println("log续体拦截resumeWith: ${Thread.currentThread()}   ${result.getOrNull()}")
        cont.resumeWith(result)
    }
}


@ExperimentalStdlibApi
fun main(){
    runBlocking {
        withContext(Dispatchers.Main){
        }
    }
    GlobalScope.launch(Dispatchers.IO+MyContinuationInterceptor()) {
        withContext(Dispatchers.Main){
        }
        println("当前协程的所以上下文元素 $coroutineContext ")
        println("调度器 ${coroutineContext[ContinuationInterceptor]} ")
        println("日志打印器 ${coroutineContext[MyContinuationInterceptor]} ")
    }
    Thread.sleep(1000000)
}




/*suspend fun getUser2(): User = withContext(Dispatchers.IO) {
    Thread.sleep(3000)
    User("openXu")
}
suspend fun getUser(): User = suspendCancellableCoroutine{
    continuation ->
    Thread{
        Thread.sleep(2000)
        println("${Thread.currentThread()}返回user对象")
        continuation.resume(User("openXu"))
    }.start()
}*/
/*
suspend fun getUser1(): User = suspendCoroutineUninterceptedOrReturn {
    continuation ->
    Thread.sleep(1000)
    continuation.resume(User("openXu"))
}
suspend fun getUser2(): User = withContext(Dispatchers.IO) {
    Thread.sleep(3000)
    User("openXu")
}

fun getUser3(continuation: Continuation<User>): Any?{
    return User("openXu")
}
*/



