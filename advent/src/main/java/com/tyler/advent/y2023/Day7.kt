package com.tyler.advent.y2023

import com.tyler.advent.addEmUp
import java.io.File
import kotlin.math.pow

object Day7 {
    private val inputFile = File("inputs/Day7.txt")

    private lateinit var cards: List<String>
    private lateinit  var regroup: (Map<Char,Int>) -> Map<Char,Int>

    private data class Hand(val cardsStr: String) {
        val handType = with(regroup(cardsStr.groupingBy { it }.eachCount())) {
            if (values.contains(5)) HandType.FiveKind
            else if (values.contains(4)) HandType.FourKind
            else if (values.contains(3) && values.contains(2)) HandType.FullHouse
            else if (values.contains(3)) HandType.ThreeKind
            else if (values.contains(2) && count { it.value == 2 } == 2) HandType.TwoPair
            else if (values.contains(2)) HandType.Pair
            else HandType.HighCard
        }

        val handScore = (HandType.values().size - HandType.values().indexOf(handType)) *
                    cards.size.toDouble().pow(cardsStr.length).toInt()

        val cardScore = cardsStr.mapIndexed { idx, card ->
            card.getRank() * cards.size.toDouble().pow(cardsStr.length - idx - 1).toInt()
        }.addEmUp()

        val rank = handScore + cardScore
    }

    private fun Char.getRank() = cards.size - cards.indexOf(toString())

    private enum class HandType { FiveKind, FourKind, FullHouse, ThreeKind, TwoPair, Pair, HighCard }

    private fun getWinnings(): Int =
        inputFile.readLines().map {
            val (hand, bid) = it.split(" ")
            Hand(hand) to bid.toInt()
        }.sortedBy { it.first.rank }
            .mapIndexed { idx, (_, bid) -> bid * (idx + 1) }
            .addEmUp()

    fun problem1(): Int {
        cards = listOf("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2")
        regroup = { cardGroups: Map<Char,Int> -> cardGroups }

        return getWinnings() // 251806792
    }

    fun problem2(): Int {
        cards = listOf("A", "K", "Q", "T", "9", "8", "7", "6", "5", "4", "3", "2", "J")
        regroup = { cardGroups: Map<Char,Int> ->
            val mutable = cardGroups.toMutableMap().apply { remove('J') }.takeIf { it.isNotEmpty() } ?: mutableMapOf('A' to 0)
            cardGroups['J']?.let { numJokers ->
                val (card, count) = mutable.maxBy { (it.value * cards.size) + it.key.getRank() }
                mutable[card] = count + numJokers
            }
            mutable
        }

        return getWinnings() // 252113488
    }
}