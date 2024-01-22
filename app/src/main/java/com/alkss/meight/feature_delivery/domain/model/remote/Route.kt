package com.alkss.meight.feature_delivery.domain.model.remote

import com.google.gson.annotations.SerializedName


data class Routes(
    @SerializedName("routes")
    val routes: List<Route>
)

data class Route(
    @SerializedName("id")
    val id: String,
    @SerializedName("sections")
    val sections: List<Section>
)

data class Section(
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("departure")
    val departure: Departure,
    @SerializedName("arrival")
    val arrival: Arrival,
    @SerializedName("summary")
    val summary: Summary,
    @SerializedName("polyline")
    val polyline: String? = null,
    @SerializedName("transport")
    val transport: Transport
)

data class Arrival(
    @SerializedName("time")
    val time: String,
    @SerializedName("place")
    val place: Place
)

data class Departure(
    @SerializedName("time")
    val time: String,
    @SerializedName("place")
    val place: Place
)

data class Place(
    @SerializedName("type")
    val type: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("originalLocation")
    val originalLocation: Location
)

data class Location(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)

data class Summary(
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("length")
    val length: Int,
    @SerializedName("baseDuration")
    val baseDuration: Int
)

data class Transport(
    @SerializedName("mode")
    val mode: String
)
