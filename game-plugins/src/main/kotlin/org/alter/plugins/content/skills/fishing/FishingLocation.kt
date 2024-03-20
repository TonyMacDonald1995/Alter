package org.alter.plugins.content.skills.fishing

class FishingLocation {
    var spots: MutableList<FishingSpot> = mutableListOf()

    fun register() {
        FishingManager.fishing_locations.add(this)
    }
}