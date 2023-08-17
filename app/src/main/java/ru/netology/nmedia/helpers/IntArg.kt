package ru.netology.nmedia.helpers

import android.os.Bundle
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object IntArg: ReadWriteProperty<Bundle, Int?> {

    override fun setValue(thisRef: Bundle, property: KProperty<*>, value: Int?) {
        thisRef.putInt(property.name, value ?: 0)
    }

    override fun getValue(thisRef: Bundle, property: KProperty<*>): Int =
        thisRef.getInt(property.name, 0)

}