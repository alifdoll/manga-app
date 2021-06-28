package com.alif.mangaapps.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MangaResponse(
	@field:SerializedName("results")
	val results: List<ResultsItem>
)

data class ResultsItem(

	@field:SerializedName("result")
	val result: String,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("relationships")
	val relationships: List<RelationshipsItem>

)

data class Data(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("attributes")
	val attributes: Attributes,

)

data class Attributes(

	@field:SerializedName("title")
	val title: Title,

	@field:SerializedName("description")
	val description: Description,

	@field:SerializedName("publicationDemographic")
	val publicationDemographic: String
)

data class Title(

	@field:SerializedName("en")
	val en: String
)

data class Description(
	@field:SerializedName("en")
	val en: String,
)

data class RelationshipsItem(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("type")
	val type: String
)


