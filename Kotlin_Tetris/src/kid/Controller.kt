package kid

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.Alert
import javafx.scene.control.Label
import javafx.scene.control.TextField
import java.net.URL
import java.util.*
import kotlin.properties.Delegates

class Controller : Initializable
{
    @FXML
    lateinit var label: Label

    @FXML
    lateinit var score: TextField

    @FXML
    lateinit var main: Canvas

    @FXML
    lateinit var sub: Canvas

    companion object
    {
        lateinit var mainTool: GraphicsContext
        lateinit var subTool: GraphicsContext
        lateinit var updateScore: () -> (Unit)

        var scoreNum = 0
        var width by Delegates.notNull<Double>()
        var height by Delegates.notNull<Double>()

        fun addScore(value: Int = 0, reset: Boolean = false)
        {
            if(reset) scoreNum = 0
            else scoreNum += value
        }

        fun alert()
        {
            Platform.runLater()
            {
                val alert = Alert(Alert.AlertType.INFORMATION)
                alert.title = "Game Over!"
                alert.contentText = "당신의 점수는 ${scoreNum}점!"
                alert.showAndWait()
            }
        }
    }

    override fun initialize(p0: URL?, p1: ResourceBundle?)
    {
        mainTool = main.graphicsContext2D
        subTool = sub.graphicsContext2D
        width = mainTool.canvas.width
        height = mainTool.canvas.height
        updateScore = fun() { score.text = scoreNum.toString() }
        updateScore()
    }
}
