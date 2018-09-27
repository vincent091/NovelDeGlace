package com.lnproduction.noveldeglace.utils


inline fun log(currentLevel: Int, tag: String, level: Int, message: () -> String) {
    if (currentLevel <= level) {
        Log.log(level, tag, message())
    }
}

inline fun log(currentLevel: Int, tag: String, level: Int, t: Throwable, message: () -> String) {
    if (currentLevel <= level) {
        Log.log(level, tag, message(), t)
    }
}