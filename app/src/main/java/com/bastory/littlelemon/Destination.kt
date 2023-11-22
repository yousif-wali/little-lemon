package com.bastory.littlelemon

interface Destination {
    val Route : String
}
object HomeScreen : Destination{
    override val Route = "HomeScreen"
}
object Registry   : Destination{
    override val Route = "Registry"
}
object Profile    : Destination{
    override val Route = "Profile"
}