import java.io.IOException
import kotlin.jvm.Throws


fun main() {
    try {
        getResult()
    } catch (e: Exception) {
        println("捕获异常$e")
    }
}

@Throws(IOException::class)
fun getResult(): Int {
    try {
        return getResult1()
    } catch (e: IOException) {
        println("捕获异常$e")
        throw e
    }
}

@Throws(IOException::class)
fun getResult1(): Int {
    if (true) throw RuntimeException("自定义异常")
    return 1
}



