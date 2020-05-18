package kid.game

import javafx.application.Platform
import javafx.scene.canvas.GraphicsContext
import kid.Controller
import kotlin.math.max

const val size: Double = 20.0
const val x_size: Int = 19
const val y_size: Int = 20

object GameRenderer
{
    private val KEY = Any()
    fun render(gc: GraphicsContext)
    {
        Platform.runLater {
            synchronized(KEY)
            {
                renderMap(gc)
                renderBlock(gc)
            }
        }
    }

    fun renderMap(gc: GraphicsContext)
    {
        val maxWidth = gc.canvas.width
        val maxHeight = gc.canvas.height

        gc.clearRect(0.0, 0.0, maxWidth, maxHeight)
        for (y in 0 until y_size) gc.strokeLine(0.0, y*size, maxWidth, y*size)
        for (x in 0 until x_size) gc.strokeLine(x*size, 0.0, x*size, maxHeight)

    }

    private fun renderBlock(gc: GraphicsContext)
    {
        val temp = GameMap.currentBlock.data
        for(Y in temp.indices step 1)
        {
            for (X in temp[Y].indices step 1)
            {
                val totalX = (GameMap.calculateX(X))
                val totalY = (GameMap.calculateY(Y))
                if (temp[Y][X] >= 1) gc.fillRect(totalX*size, totalY*size, size - 1.0, size - 1.0)
            }
        }

        for(Y in GameMap.map.indices)
        {
            for (X in GameMap.map[0].indices)
            {
                if (GameMap.map[Y][X] >= 1) gc.fillRect(X*size, Y*size, size - 1.0, size - 1.0)
            }
        }
    }
}