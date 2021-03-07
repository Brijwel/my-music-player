package com.brijwel.mymusicplayer.repo

/**
 * Created by Brijwel on 07-03-2021.
 */
object WaveFormData {

    fun getWaveDate(): IntArray {

        val listInt = arrayListOf<Int>()
        for (i in 1..12) {
            for (j in 1..5) {
                when (j) {
                    1, 5 -> listInt.add(1)
                    2, 4 -> listInt.add(2)
                    3 -> listInt.add(3)
                }
            }
        }
        val intArray = IntArray(listInt.size)
        listInt.forEachIndexed { index, i ->
            intArray[index] = i
        }
        return intArray
    }
}