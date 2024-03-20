package org.alter.plugins.content.mechanics.music

load_service(MusicService())

on_world_init {
    world.getService(MusicService::class.java)!!.let {service ->
        service.music.forEach { music ->
            if (!music.regionIds.isNullOrEmpty()) {
                music.regionIds.forEach { regionId ->
                    on_enter_region(regionId) {
                        player.playSong(music.musicId)
                    }
                }
            }
        }
    }
}