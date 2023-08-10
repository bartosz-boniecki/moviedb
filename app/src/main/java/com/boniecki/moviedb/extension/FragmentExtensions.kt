package com.boniecki.moviedb.extension

import androidx.fragment.app.Fragment

internal inline fun <reified Listener> Fragment.requireListener(): Listener = when (activity) {
    is Listener -> activity as Listener
    else -> throw ClassCastException(
        "$activity must implement ${Listener::class.java.simpleName}"
    )
}