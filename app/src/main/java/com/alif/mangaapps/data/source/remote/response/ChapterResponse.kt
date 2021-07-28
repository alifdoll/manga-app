package com.alif.mangaapps.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ChapterResponse(
/*
	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("offset")
	val offset: Int,

	@field:SerializedName("limit")
	val limit: Int,
*/
	@field:SerializedName("results")
	val results: List<ChapterItems>
)

data class ChapterItems(

	@field:SerializedName("result")
	val result: String,

	@field:SerializedName("data")
	val data: ChapterData
)


data class ChapterData(

	@field:SerializedName("attributes")
	val attributes: ChapterAttributes,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("type")
	val type: String
)

data class ChapterAttributes(

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

