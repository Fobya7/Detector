
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.s452635.detector.detecting.GearSystem
import com.s452635.detector.detecting.GenValues
import com.s452635.detector.windows.GeneratorContent
import org.junit.jupiter.api.Test

internal class GeneratorTest
{

    private val gearSystem = GearSystem (
        toothAmount = 12,
        diameter = 100,
        detectorTick = 500
        )

    @Test
    fun gearSystemReadout()
    {
        println( "area angle: ${gearSystem.areaAngle}" )
    }

    @Test
    fun simpleTest() = application {

        val genValues = GenValues( gearSystem )
        genValues.run()

        Window (
            onCloseRequest = ::exitApplication,
            title = "The Generator",
            state = WindowState(
                position = WindowPosition( Alignment.Center ),
                size = DpSize.Unspecified
                )
            )
        {
            GeneratorContent( genValues )
        }
    }

}

