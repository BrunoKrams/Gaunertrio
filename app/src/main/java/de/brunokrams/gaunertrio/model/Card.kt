package de.brunokrams.gaunertrio.model

data class Card(val gauner1: Gauner, val gauner2: Gauner, val gauner3: Gauner) {
    companion object {

        val TOTAL_NUMBER_OF_DISTINCT_CARDS = 35
        val ALL = listOf(
            Card(Gauner.GREEN, Gauner.RED, Gauner.BLACK),
            Card(Gauner.GREEN, Gauner.YELLOW, Gauner.BROWN),
            Card(Gauner.BROWN, Gauner.GREEN, Gauner.PURPLE),
            Card(Gauner.RED, Gauner.GREEN, Gauner.PURPLE),
            Card(Gauner.BLUE, Gauner.GREEN, Gauner.PURPLE),
            Card(Gauner.YELLOW, Gauner.GREEN, Gauner.PURPLE),
            Card(Gauner.BLUE, Gauner.GREEN, Gauner.BLACK),
            Card(Gauner.YELLOW, Gauner.BLUE, Gauner.GREEN),
            Card(Gauner.BROWN, Gauner.BLUE, Gauner.GREEN),
            Card(Gauner.BROWN, Gauner.BLACK, Gauner.GREEN),
            Card(Gauner.RED, Gauner.BLUE, Gauner.GREEN),
            Card(Gauner.PURPLE, Gauner.RED, Gauner.BLACK),
            Card(Gauner.BLACK, Gauner.YELLOW, Gauner.BLUE),
            Card(Gauner.BROWN, Gauner.RED, Gauner.YELLOW),
            Card(Gauner.PURPLE, Gauner.YELLOW, Gauner.BROWN),
            Card(Gauner.BLACK, Gauner.RED, Gauner.YELLOW),
            Card(Gauner.BLACK, Gauner.RED, Gauner.BLUE),
            Card(Gauner.BROWN, Gauner.YELLOW, Gauner.BLUE),
            Card(Gauner.RED, Gauner.YELLOW, Gauner.BLUE),
            Card(Gauner.BROWN, Gauner.BLACK, Gauner.RED),
            Card(Gauner.YELLOW, Gauner.BLUE, Gauner.PURPLE),
            Card(Gauner.BROWN, Gauner.BLACK, Gauner.BLUE),
            Card(Gauner.PURPLE, Gauner.BLUE, Gauner.RED),
            Card(Gauner.PURPLE, Gauner.RED, Gauner.BROWN),
            Card(Gauner.PURPLE, Gauner.YELLOW, Gauner.RED),
            Card(Gauner.BLACK, Gauner.PURPLE, Gauner.BROWN),
            Card(Gauner.BROWN, Gauner.RED, Gauner.BLUE),
            Card(Gauner.BROWN, Gauner.BLACK, Gauner.YELLOW),
            Card(Gauner.BROWN, Gauner.BLUE, Gauner.PURPLE),
            Card(Gauner.PURPLE, Gauner.YELLOW, Gauner.BLACK),
            Card(Gauner.BLACK, Gauner.BLUE, Gauner.PURPLE),
            Card(Gauner.GREEN, Gauner.BLACK, Gauner.PURPLE),
            Card(Gauner.GREEN, Gauner.YELLOW, Gauner.BLACK),
            Card(Gauner.GREEN, Gauner.YELLOW, Gauner.RED),
            Card(Gauner.GREEN, Gauner.RED, Gauner.BROWN)
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Card) return false
        return setOf(gauner1, gauner2, gauner3) ==
                setOf(other.gauner1, other.gauner2, other.gauner3)
    }

    override fun hashCode(): Int {
        return setOf(gauner1, gauner2, gauner3).hashCode()
    }
}