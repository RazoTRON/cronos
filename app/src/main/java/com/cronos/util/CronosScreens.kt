package com.cronos.util

enum class CronosScreens {
    Search,
    SearchResult,
    PeopleDetails,
    Login;

    companion object {
        fun route(route: String?): CronosScreens = when (route?.substringBefore('/')) {
            Search.name -> Search
            SearchResult.name -> SearchResult
            PeopleDetails.name -> PeopleDetails
            Login.name -> Login
            else -> throw java.lang.IllegalArgumentException("Route $route is not recognized.")
        }
    }
}