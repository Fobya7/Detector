
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.application
import com.s452635.detector.detecting.FileValues
import com.s452635.detector.detecting.GearSystem
import com.s452635.detector.detecting.GenValues
import com.s452635.detector.styling.doublePrint
import com.s452635.detector.windows.GeneratorState
import com.s452635.detector.windows.GeneratorWindow
import org.junit.jupiter.api.Test
import java.lang.Thread.sleep

@ExperimentalFoundationApi
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

        // thread.start()

        val genState = GeneratorState (
            isEnabled = mutableStateOf( true ),
            isVisible = mutableStateOf( true ),
            isAlone = mutableStateOf( true ),
            onClose = {},
            genValues = mutableStateOf( genValues ),
            fileValues = FileValues(),
            onClickGenerate = {},
            onClickDir = {},
            onClickGS = {}
            )

        GeneratorWindow( genState )

    }

}

