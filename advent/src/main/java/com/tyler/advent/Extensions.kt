package com.tyler.advent

fun List<Int>.addEmUp() = reduceOrNull { acc, i -> acc + i } ?: 0

fun List<Long>.addEmUp() = reduceOrNull { acc, i -> acc + i } ?: 0
fun List<Long>.multiply() = reduceOrNull { acc, i -> acc * i } ?: 0
