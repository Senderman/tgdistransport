package com.senderman.tgdistransport

import java.util.*

class PropertiesLoader(profile: String? = null) {
    private val properties = Properties()
    private val envPattern = Regex("\\$\\{([\\w]+)}")

    init {
        val prof = if (profile == null) "" else "-$profile"
        val fileName = "/application$prof.properties"
        this::class.java.getResourceAsStream(fileName).use { properties.load(it) }
    }

    operator fun get(key: String): String {
        val value = properties[key] ?: throw NullPointerException("No value for $key")
        val stringValue = value.toString()
        return if (stringValue.matches(envPattern))
            System.getenv(stringValue.replace(envPattern, "\$1"))
        else stringValue
    }

}