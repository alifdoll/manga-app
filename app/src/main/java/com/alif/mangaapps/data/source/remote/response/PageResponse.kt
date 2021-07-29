package com.alif.mangaapps.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PageResponse(

	@field:SerializedName("result")
	val result: String,

	@field:SerializedName("data")
	val data: PageData
)

data class PageData(

	@field:SerializedName("attributes")
	val attributes: PageAttributes,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("type")
	val type: String
)


data class PageAttributes(

	@field:SerializedName("volume")
	val volume: String,

	@field:SerializedName("chapter")
	val chapter: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("data")
	val data: List<String>,

	@field:SerializedName("publishAt")
	val publishAt: String,

	@field:SerializedName("translatedLanguage")
	val translatedLanguage: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("version")
	val version: Int,

	@field:SerializedName("hash")
	val hash: String,

	@field:SerializedName("dataSaver")
	val dataSaver: List<String>,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
