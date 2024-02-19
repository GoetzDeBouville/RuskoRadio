package com.prosto.myapplication.core.utils

import android.content.Context
import java.io.InputStream
import java.util.Properties

/**
 * Объект ConfigTool должен быть прроинициализирован до извлечения данных из properties
 */
object ConfigTool {
    private lateinit var properties: AppConfig

    fun init(context: Context) {
        val inputStream: InputStream = context.assets.open("app_config.properties")
        val loadedProperties = Properties()
        loadedProperties.load(inputStream)

        properties = AppConfig(
            streamUrl = loadedProperties.getProperty("streamUrl", ""),
            websiteUrl = loadedProperties.getProperty("websiteUrl", ""),
            songInfoUrl = loadedProperties.getProperty("songInfoUrl", ""),
            adUnitId = loadedProperties.getProperty("adUnitId", "")
        )
    }

    fun getAppConfig(): AppConfig {
        if (!::properties.isInitialized) {
            throw IllegalStateException("Config has not been initialized. Call init() first.")
        }
        return properties
    }
}

data class AppConfig(
    val streamUrl: String,
    val websiteUrl: String,
    val songInfoUrl: String,
    val adUnitId: String
)
