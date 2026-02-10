package com.example.bookmap.data.dto.response

import com.google.gson.annotations.SerializedName

data class Formats(
    @SerializedName("text/html")
    val textHtml: String,
    @SerializedName("application/epub+zip")
    val applicationEpubZip: String,
    @SerializedName("application/x-mobipocket-ebook")
    val applicationXMobipocketEbook: String,
    @SerializedName("application/rdf+xml")
    val applicationRdfXml: String,
    @SerializedName("image/jpeg")
    val imageJpeg: String,
    @SerializedName("application/octet-stream")
    val applicationOctetStream: String,
    @SerializedName("text/plain; charset=utf-8")
    val textPlainCharsetUtf8: String?,
    @SerializedName("text/plain; charset=us-ascii")
    val textPlainCharsetUsAscii: String?,
    @SerializedName("text/html; charset=utf-8")
    val textHtmlCharsetUtf8: String?,
)