package com.tyler.advent

fun List<Int>.addEmUp() = reduceOrNull { acc, i -> acc + i } ?: 0

fun List<Long>.addEmUp() = reduceOrNull { acc, i -> acc + i } ?: 0
fun List<Long>.multiply() = reduceOrNull { acc, i -> acc * i } ?: 0
fun List<Long>.lcm(): Long {
    var result = this[0]
    drop(1).forEach { result = lcm(result, it) }
    return result
}

fun lcm(n1: Long, n2: Long): Long {
    var a = n1
    var b = n2

    while (b > 0) {
        val temp = b
        b = a % b
        a = temp
    }

    return n1 * (n2 / a)
}