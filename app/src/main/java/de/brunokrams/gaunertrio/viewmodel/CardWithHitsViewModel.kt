package de.brunokrams.gaunertrio.viewmodel

import de.brunokrams.gaunertrio.model.Card

data class CardWithHitsViewModel(val id:Int, val card: Card, val numberOfHits:Int)

