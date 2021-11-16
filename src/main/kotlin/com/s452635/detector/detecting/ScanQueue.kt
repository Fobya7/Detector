package com.s452635.detector.detecting

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.util.*

// TODO : initial scan value?
class ScanQueue(
    val preQueueSize : Int = 5,
    val postQueueSize : Int = 14,
    startingList : List<String>
) {
    val preQueue = mutableStateOf( LinkedList( listOf( "H" ) ) )
    var currentItem : MutableState<String?> = mutableStateOf( null )
    val postQueue = mutableStateOf( LinkedList( startingList ) )

    init {
        nextItem()
    }

    fun isBlocked() : Boolean
    {
        if( postQueue.value.isEmpty() ) { return true }

        return false
    }

    fun nextItem()
    {
        fun addCurrentItemToPreQueue()
        {
            if( preQueue.value.size > preQueueSize )
            {
                preQueue.value.pop()
            }
            preQueue.value.add( currentItem.value!! )
        }
        fun changeCurrentItem()
        {
            currentItem.value = postQueue.value.pop()
        }

        if( !isBlocked() )
        {
            if( currentItem.value != null ) { addCurrentItemToPreQueue() }
            changeCurrentItem()
        }
    }
}

enum class Scan { H, L } // TODO : use