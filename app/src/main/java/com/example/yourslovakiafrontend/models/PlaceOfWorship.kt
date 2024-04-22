package fiit.mtaa.yourslovakia.models

data class PlaceOfWorship(
    override val id: Long,
    override val name: String,
    override val location: GeoPoint,
    override val wikidataCode: String?,
    val religion: String?,
    val denomination: String?,
) : PointOfInterest
