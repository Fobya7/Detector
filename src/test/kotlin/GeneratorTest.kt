
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.s452635.detector.detecting.GearSystem
import com.s452635.detector.detecting.GenValues
import com.s452635.detector.styling.doublePrint
import com.s452635.detector.windows.GeneratorContent
import org.junit.jupiter.api.Test
import java.lang.Thread.sleep

internal class GeneratorTest
{

    private val gearSystem = GearSystem (
        toothAmount = 12,
        diameter = 10,
        detectorTick = 1000
        )

    @Test
    fun gearSystemReadout()
    {
        println( "area angle: ${doublePrint( gearSystem.areaAngle )}" )
        println( "max AGS:    ${doublePrint( gearSystem.maxAGS )}" )
    }

    @Test
    fun simpleTest() = application {

        val genValues = GenValues( gearSystem )
        val thread = Thread {
            while( true ) {
                sleep( 250 )
                genValues.step()
                sleep( 250 )
                println(genValues.snap())
            }
        }

        thread.start()

        Window (
            onCloseRequest = ::exitApplication,
            title = "The Generator",
            state = WindowState(
                position = WindowPosition(Alignment.Center),
                width = Dp.Unspecified, height = Dp.Unspecified
            )
        )
        {
            GeneratorContent( genValues )
        }

    }

}

