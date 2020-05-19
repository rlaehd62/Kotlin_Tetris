package kid.game

import javafx.animation.AnimationTimer
import kid.Controller

class GameFlowManager: AnimationTimer()
{
    private var tick = 0
    private val mainGC = Controller.mainTool
    private val subGC = Controller.subTool
    private val render: GameRenderer = GameRenderer
    private val map: GameMap = GameMap

    override fun handle(now: Long)
    {
        render.render(mainGC)
        if(++tick % 30 == 0)
        {
            map.moveTo(Direction.DOWN)
            tick = 0
        }
    }

}