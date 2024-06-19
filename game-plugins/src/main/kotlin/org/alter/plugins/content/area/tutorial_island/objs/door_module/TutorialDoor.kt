package org.alter.plugins.content.area.tutorial_island.objs.door_module

import org.alter.game.model.Tile

data class TutorialDoor(var obj: Int = -1, var replaceObj: Int = -1, var replaceRot: Int = 1, var replaceTile: Tile? = null, var doorTile: Tile = Tile(0,0,0),
                        var requiredVarpState: Int = -1, var insideTile: Tile = Tile(0,0,0), var outsideTile: Tile = Tile(0, 0, 0), var afterActionVarpState: Int = 0,
                        var afterActionVarpBar: Int = 0, var errorMsg: String? = null, var openSound: Int = -1)
data class TutorialDoubleDoor(var objLeft: Int = -1, var objRight: Int = -1, var replaceObjLeft: Int = -1, var replaceObjRight: Int = -1, var replaceRotLeft: Int = 1, var replaceRotRight: Int = 1,
                              var replaceTileLeft: Tile? = null, var replaceTileRight: Tile? = null, var doorTileLeft: Tile = Tile(0,0,0),
                              var doorTileRight: Tile = Tile(0,0,0), var requiredVarpState: Int = -1, var insideTileLeft: Tile = Tile(0,0,0),
                              var insideTileRight: Tile = Tile(0,0,0), var outsideTileLeft: Tile = Tile(0,0,0), var outsideTileRight: Tile = Tile(0,0,0),
                              var afterActionVarpState: Int = -1, var afterActionVarpBar: Int = 0, var errorMsg: String? = null, var openSound: Int = -1)

fun init_tutorial_door(init: TutorialDoorDsl.Builder.() -> Unit) {
    val builder = TutorialDoorDsl.Builder()
    init(builder)

    TutorialDoorManager.tutorialDoors.add(builder.build())
}

fun init_tutorial_double_door(init: TutorialDoubleDoorDsl.Builder.() -> Unit) {
    var builder = TutorialDoubleDoorDsl.Builder()
    init(builder)

    TutorialDoorManager.tutorialDoubleDoors.add(builder.build())
}

object TutorialDoorDsl {
    @DslMarker
    annotation class TutorialDoorDslMarker

    @TutorialDoorDslMarker
    class Builder {
        private val tutorialDoor = TutorialDoor()

        fun build() = tutorialDoor

        fun config(init: ConfigBuilder.() -> Unit) {
            val builder = ConfigBuilder()
            init(builder)

            tutorialDoor.obj = builder.obj
            tutorialDoor.replaceObj = builder.replaceObj
            tutorialDoor.replaceRot = builder.replaceRot
            tutorialDoor.replaceTile = builder.replaceTile
            tutorialDoor.doorTile = builder.doorTile
            tutorialDoor.requiredVarpState = builder.requiredVarpState
            tutorialDoor.insideTile = builder.insideTile
            tutorialDoor.outsideTile = builder.outsideTile
            tutorialDoor.afterActionVarpState = builder.afterActionVarpState
            tutorialDoor.afterActionVarpBar = builder.afterActionVarpBar
            tutorialDoor.errorMsg = builder.errorMsg
            tutorialDoor.openSound = builder.openSound

        }
    }

    class ConfigBuilder {
        var obj: Int = -1

        var replaceObj: Int = -1

        var replaceRot: Int = 1

        var doorTile: Tile = Tile(0,0,0)

        var replaceTile: Tile? = null

        var requiredVarpState: Int = -1

        var insideTile: Tile = Tile(0,0,0)

        var outsideTile: Tile = Tile(0, 0, 0)

        var afterActionVarpState: Int = -1

        var afterActionVarpBar: Int = -1

        var errorMsg: String? = null

        var openSound: Int = -1
    }
}

object TutorialDoubleDoorDsl {
    @DslMarker
    annotation class TutorialDoubleDoorDslMarker

    @TutorialDoubleDoorDslMarker
    class Builder {
        private val tutorialDoubleDoor = TutorialDoubleDoor()

        fun build() = tutorialDoubleDoor

        fun config(init: ConfigBuilder.() -> Unit) {
            val builder = ConfigBuilder()
            init(builder)

            tutorialDoubleDoor.objLeft = builder.objLeft
            tutorialDoubleDoor.objRight = builder.objRight
            tutorialDoubleDoor.replaceObjLeft = builder.replaceObjLeft
            tutorialDoubleDoor.replaceObjRight = builder.replaceObjRight
            tutorialDoubleDoor.replaceRotLeft = builder.replaceRotLeft
            tutorialDoubleDoor.replaceRotRight = builder.replaceRotRight
            tutorialDoubleDoor.replaceTileLeft = builder.replaceTileLeft
            tutorialDoubleDoor.replaceTileRight = builder.replaceTileRight
            tutorialDoubleDoor.doorTileLeft = builder.doorTileLeft
            tutorialDoubleDoor.doorTileRight = builder.doorTileRight
            tutorialDoubleDoor.requiredVarpState = builder.requiredVarpState
            tutorialDoubleDoor.insideTileLeft = builder.insideTileLeft
            tutorialDoubleDoor.insideTileRight = builder.insideTileRight
            tutorialDoubleDoor.outsideTileLeft = builder.outsideTileLeft
            tutorialDoubleDoor.outsideTileRight = builder.outsideTileRight
            tutorialDoubleDoor.afterActionVarpState = builder.afterActionVarpState
            tutorialDoubleDoor.afterActionVarpBar = builder.afterActionVarpBar
            tutorialDoubleDoor.errorMsg = builder.errorMsg
            tutorialDoubleDoor.openSound = builder.openSound

        }
    }

    class ConfigBuilder {
        var objLeft: Int = -1
        var objRight: Int = -1

        var replaceObjLeft: Int = -1
        var replaceObjRight: Int = -1

        var replaceRotLeft: Int = 1
        var replaceRotRight: Int = 1

        var doorTileLeft: Tile = Tile(0,0,0)
        var doorTileRight: Tile = Tile(0,0,0)

        var replaceTileLeft: Tile? = null
        var replaceTileRight: Tile? = null

        var requiredVarpState: Int = -1

        var insideTileLeft: Tile = Tile(0,0,0)
        var insideTileRight: Tile = Tile(0,0,0)

        var outsideTileLeft: Tile = Tile(0, 0, 0)
        var outsideTileRight: Tile = Tile(0,0,0)

        var afterActionVarpState: Int = -1

        var afterActionVarpBar: Int = -1

        var errorMsg: String? = null

        var openSound: Int = -1
    }
}