package com.iagoaf.appfinancas.core.utils

import com.iagoaf.appfinancas.core.utils.Month.values


enum class Month(val id: String, val od: Int) {
    JANUARY("JAN", 0),
    FEBRUARY("FEB", 1),
    MARCH("MAR", 2),
    APRIL("APR", 3),
    MAY("MAY", 4),
    JUNE("JUN", 5),
    JULY("JUL", 6),
    AUGUST("AUG", 7),
    SEPTEMBER("SEP", 8),
    OCTOBER("OCT", 9),
    NOVEMBER("NOV", 10),
    DECEMBER("DEC", 11);

    companion object {
        val list = values().toList()
    }
}