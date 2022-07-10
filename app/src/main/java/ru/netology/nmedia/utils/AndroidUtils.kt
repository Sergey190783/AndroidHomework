package ru.netology.nmedia.utils

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

object AndroidUtils {
    fun hideKeyboard(view: View){
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

object StringArg: ReadWriteProperty<Bundle, String?>{
    override fun getValue(thisRef: Bundle, property: KProperty<*>): String? {
        return thisRef.getString(property.name)
    }

    override fun setValue(thisRef: Bundle, property: KProperty<*>, value: String?) {
        thisRef.putString(property.name, value)
    }

}