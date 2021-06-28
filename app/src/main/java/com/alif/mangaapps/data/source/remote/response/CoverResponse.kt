package com.alif.mangaapps.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CoverResponse(
	@field:SerializedName("data")
	val data: Cover
)

data class Cover(
	@field:SerializedName("attributes")
	val attributes: Property
)

data class Property(
	@field:SerializedName("fileName")
	val fileName: String
)
