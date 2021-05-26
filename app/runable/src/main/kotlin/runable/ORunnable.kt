package runable

/**
 * Author: openXu
 * Time: 2021/1/20 10:34
 * class: ORunnable
 * Description:
 */
interface Source<out T> {
    fun nextT(): T
}

fun demo(strs: Source<String>) {
    val objects: Source<Any> = strs // 这个没问题，因为 T 是一个 out-参数
    // ……
}
data class User1(val name: String, val age: Int)


class Box<T>(t: T) {
    var value = t
}


interface ORunnable {

    fun invoke()

}

