package com.example.bookmap

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import dagger.hilt.android.HiltAndroidApp
import okhttp3.Dns
import okhttp3.OkHttpClient
import java.net.Inet4Address
import java.net.InetAddress

@HiltAndroidApp
class BookApp : Application(), ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader {
        // OkHttpClient customizado que força IPv4
        val okHttpClient = OkHttpClient.Builder()
            .dns(object : Dns {
                override fun lookup(hostname: String): List<InetAddress> {
                    // Força o uso apenas de endereços IPv4
                    return Dns.SYSTEM.lookup(hostname)
                        .filter { it is Inet4Address }
                }
            })
            .build()

        return ImageLoader.Builder(this)
            .okHttpClient(okHttpClient) // Usa o cliente customizado
            .crossfade(true)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizeBytes(50 * 1024 * 1024)
                    .build()
            }
            .build()
    }
}