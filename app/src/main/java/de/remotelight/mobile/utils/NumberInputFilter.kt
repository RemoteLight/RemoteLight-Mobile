package de.remotelight.mobile.utils

import android.text.InputFilter
import android.text.Spanned

class NumberInputFilter(var range: IntRange = IntRange(0, Int.MAX_VALUE)): InputFilter {

    override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {
        try {
            val textVal = (dest?.subSequence(0, dstart) ?: "").toString() + source?.subSequence(start, end).toString() + dest?.subSequence(dend, dest.length)
            val number = textVal.toInt()
            if(range.contains(number))
                return null
        } catch (ex: NumberFormatException) {}
        return ""
    }

}