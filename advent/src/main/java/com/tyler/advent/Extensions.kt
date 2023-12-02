package com.tyler.advent

fun List<Int>.addEmUp() = reduce { acc, i -> acc + i }