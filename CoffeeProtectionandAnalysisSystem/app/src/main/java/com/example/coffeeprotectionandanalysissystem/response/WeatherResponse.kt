package com.example.coffeeprotectionandanalysissystem.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse(

	@field:SerializedName("current")
	val current: Current? = null,

	@field:SerializedName("location")
	val location: Location? = null
)

data class Current(

	@field:SerializedName("feelslike_c")
	val feelslikeC: Any? = null,

	@field:SerializedName("feelslike_f")
	val feelslikeF: Any? = null,

	@field:SerializedName("wind_degree")
	val windDegree: Int? = null,

	@field:SerializedName("windchill_f")
	val windchillF: Any? = null,

	@field:SerializedName("windchill_c")
	val windchillC: Any? = null,

	@field:SerializedName("last_updated_epoch")
	val lastUpdatedEpoch: Int? = null,

	@field:SerializedName("temp_c")
	val tempC: Any? = null,

	@field:SerializedName("temp_f")
	val tempF: Any? = null,

	@field:SerializedName("cloud")
	val cloud: Int? = null,

	@field:SerializedName("wind_kph")
	val windKph: Any? = null,

	@field:SerializedName("wind_mph")
	val windMph: Any? = null,

	@field:SerializedName("humidity")
	val humidity: Int? = null,

	@field:SerializedName("dewpoint_f")
	val dewpointF: Any? = null,

	@field:SerializedName("uv")
	val uv: Any? = null,

	@field:SerializedName("last_updated")
	val lastUpdated: String? = null,

	@field:SerializedName("heatindex_f")
	val heatindexF: Any? = null,

	@field:SerializedName("dewpoint_c")
	val dewpointC: Any? = null,

	@field:SerializedName("is_day")
	val isDay: Int? = null,

	@field:SerializedName("precip_in")
	val precipIn: Any? = null,

	@field:SerializedName("heatindex_c")
	val heatindexC: Any? = null,

	@field:SerializedName("air_quality")
	val airQuality: AirQuality? = null,

	@field:SerializedName("wind_dir")
	val windDir: String? = null,

	@field:SerializedName("gust_mph")
	val gustMph: Any? = null,

	@field:SerializedName("pressure_in")
	val pressureIn: Any? = null,

	@field:SerializedName("gust_kph")
	val gustKph: Any? = null,

	@field:SerializedName("precip_mm")
	val precipMm: Any? = null,

	@field:SerializedName("condition")
	val condition: Condition? = null,

	@field:SerializedName("vis_km")
	val visKm: Any? = null,

	@field:SerializedName("pressure_mb")
	val pressureMb: Any? = null,

	@field:SerializedName("vis_miles")
	val visMiles: Any? = null
)

data class Condition(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("text")
	val text: String? = null
)

data class Location(

	@field:SerializedName("localtime")
	val localtime: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("localtime_epoch")
	val localtimeEpoch: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("lon")
	val lon: Any? = null,

	@field:SerializedName("region")
	val region: String? = null,

	@field:SerializedName("lat")
	val lat: Any? = null,

	@field:SerializedName("tz_id")
	val tzId: String? = null
)

data class AirQuality(

	@field:SerializedName("no2")
	val no2: Any? = null,

	@field:SerializedName("o3")
	val o3: Any? = null,

	@field:SerializedName("us-epa-index")
	val usEpaIndex: Int? = null,

	@field:SerializedName("so2")
	val so2: Any? = null,

	@field:SerializedName("pm2_5")
	val pm25: Any? = null,

	@field:SerializedName("pm10")
	val pm10: Any? = null,

	@field:SerializedName("co")
	val co: Any? = null,

	@field:SerializedName("gb-defra-index")
	val gbDefraIndex: Int? = null
)
