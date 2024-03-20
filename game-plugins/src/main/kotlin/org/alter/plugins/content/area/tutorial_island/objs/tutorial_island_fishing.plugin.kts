package org.alter.plugins.content.area.tutorial_island.objs

import org.alter.plugins.content.skills.fishing.FishingLocation
import org.alter.plugins.content.skills.fishing.FishingSpot
import org.alter.plugins.content.skills.fishing.FishingSpots

val fishing = FishingLocation()
fishing.spots.add(FishingSpot(FishingSpots.TUTORIAL_NET, Tile(3103,3092,0)))
fishing.spots.add(FishingSpot(FishingSpots.TUTORIAL_NET, Tile(3101, 3092,0)))
fishing.spots.add(FishingSpot(FishingSpots.TUTORIAL_NET, Tile(3099,3090,0)))
fishing.register()
