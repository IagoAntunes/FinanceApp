package com.iagoaf.appfinancas.core.utils

import com.iagoaf.appfinancas.core.utils.Month.values


enum class Month(val id: String, val nameM: String, val od: Int) {
    JANUARY("JAN", "January", 0),
    FEBRUARY("FEB", "February", 1),
    MARCH("MAR", "March", 2),
    APRIL("APR", "April", 3),
    MAY("MAY", "May", 4),
    JUNE("JUN", "June", 5),
    JULY("JUL", "July", 6),
    AUGUST("AUG", "August", 7),
    SEPTEMBER("SEP", "September", 8),
    OCTOBER("OCT", "October", 9),
    NOVEMBER("NOV", "November", 10),
    DECEMBER("DEC", "December", 11);

    companion object {
        val list = entries
    }
}