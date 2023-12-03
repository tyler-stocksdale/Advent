package com.tyler.advent

fun List<Int>.addEmUp() = reduceOrNull { acc, i -> acc + i } ?: 0