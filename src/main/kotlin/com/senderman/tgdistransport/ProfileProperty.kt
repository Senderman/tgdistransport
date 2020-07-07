package com.senderman.tgdistransport

import java.util.*

class ProfileProperty(profile: String? = null) : Properties() {

    private val envPattern = Regex("\\$\\{([\\w]+)}")

    init {
        val prof = if (profile == null) "" else "-$profile"
        val fileName = "/application$prof.properties"
        this::class.java.getResourceAsStream(fileName).use { load(it) }
    }

    override fun getProperty(key: String): String {
        val value = this[key]?.toString() ?: throw NullPointerException("No value for $key")
        return if (value.matches(envPattern))
            System.getenv(value.replace(envPattern, "\$1"))
        else value
    }

}