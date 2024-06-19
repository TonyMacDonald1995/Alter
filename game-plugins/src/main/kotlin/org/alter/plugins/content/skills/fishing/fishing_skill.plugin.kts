package org.alter.plugins.content.skills.fishing

on_world_init {

    FishingManager.fishing_locations.forEach { location ->
        location.spots.forEach { spot ->
            spot.spot.spotEntityId.forEach { entityId ->
                world.spawn(Npc(entityId, spot.tile, world))
            }
        }
    }

    FishingSpots.values().forEach { spot ->
        spot.spotEntityId.forEach { entityId ->
            spot.option.forEach { option ->
                if (npcHasOption(entityId, option))
                    on_npc_option(entityId, option) {
                        val fishing = Fishing(player, spot)
                        player.queue { fishing.startFishing(this) }
                    }
            }
        }
    }
}