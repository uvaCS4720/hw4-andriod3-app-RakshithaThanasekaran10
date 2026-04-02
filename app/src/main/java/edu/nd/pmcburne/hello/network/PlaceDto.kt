package edu.nd.pmcburne.hello.network

import com.google.gson.annotations.SerializedName

// Represents the API response for a single place
data class PlaceDTO(
    val id: Int,
    val name: String,
    val description: String,
    @SerializedName("tag_list") val tagList: List<String>,
    @SerializedName("visual_center") val visualCenter: VisualCenter
)

// Represents the coordinates of the place
data class VisualCenter(
    val latitude: Double,
    val longitude: Double
)