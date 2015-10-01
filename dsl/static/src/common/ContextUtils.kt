/*
 * Copyright 2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:JvmMultifileClass
@file:JvmName("ContextUtilsKt")
package org.jetbrains.anko

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Parcelable
import android.preference.PreferenceManager
import android.view.View
import org.jetbrains.anko.internals.NoBinding
import java.io.Serializable

public val Context.defaultSharedPreferences: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(this)

public val Fragment.defaultSharedPreferences: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(activity)

public val Fragment.act: Activity
    get() = activity

public val Fragment.ctx: Context
    get() = activity

public val Context.ctx: Context
    get() = this

public val Activity.act: Activity
    get() = this

public inline fun <reified T : View> View.find(id: Int): T = findViewById(id) as T
public inline fun <reified T : View> Activity.find(id: Int): T = findViewById(id) as T
public inline fun <reified T : View> Fragment.find(id: Int): T = view?.findViewById(id) as T

public inline fun <reified T : View> View.findOptional(id: Int): T? = findViewById(id) as? T
public inline fun <reified T : View> Activity.findOptional(id: Int): T? = findViewById(id) as? T
public inline fun <reified T : View> Fragment.findOptional(id: Int): T? = view?.findViewById(id) as? T

public fun <T: Fragment> T.withArguments(vararg params: Pair<String, Any>): T {
    arguments = bundleOf(*params)
    return this
}

public fun bundleOf(vararg params: Pair<String, Any>): Bundle {
    val b = Bundle()
    for (p in params) {
        val (k, v) = p
        when (v) {
            is Boolean -> b.putBoolean(k, v)
            is Byte -> b.putByte(k, v)
            is Char -> b.putChar(k, v)
            is Short -> b.putShort(k, v)
            is Int -> b.putInt(k, v)
            is Long -> b.putLong(k, v)
            is Float -> b.putFloat(k, v)
            is Double -> b.putDouble(k, v)
            is String -> b.putString(k, v)
            is CharSequence -> b.putCharSequence(k, v)
            is Parcelable -> b.putParcelable(k, v)
            is Serializable -> b.putSerializable(k, v)
            is BooleanArray -> b.putBooleanArray(k, v)
            is ByteArray -> b.putByteArray(k, v)
            is CharArray -> b.putCharArray(k, v)
            is DoubleArray -> b.putDoubleArray(k, v)
            is FloatArray -> b.putFloatArray(k, v)
            is IntArray -> b.putIntArray(k, v)
            is LongArray -> b.putLongArray(k, v)
            is Array<Parcelable> -> b.putParcelableArray(k, v)
            is ShortArray -> b.putShortArray(k, v)
            is Array<CharSequence> -> b.putCharSequenceArray(k, v)
            is Array<String> -> b.putStringArray(k, v)
            is Bundle -> b.putBundle(k, v)
            else -> throw AnkoException("Unsupported bundle component (${v.javaClass})")
        }
    }

    return b
}

public @NoBinding val Context.displayMetrics: android.util.DisplayMetrics
    get() = resources.displayMetrics

public @NoBinding val Context.configuration: android.content.res.Configuration
    get() = resources.configuration

public val android.content.res.Configuration.portrait: Boolean
    get() = orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

public val android.content.res.Configuration.landscape: Boolean
    get() = orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

public val android.content.res.Configuration.long: Boolean
    get() = (screenLayout and android.content.res.Configuration.SCREENLAYOUT_LONG_YES) != 0