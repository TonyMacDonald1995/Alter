package org.alter.plugins.content.mechanics.music

data class Music(val name: String, val hint: String, val musicId: Int, val duration: Int,
    val regionIds: List<Int>?)