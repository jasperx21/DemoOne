package com.example.demoone.data.model

object WikiSearch {

  data class Result(
    val query: Query
  )

  data class Query(
    val search: ArrayList<Search>
  )

  data class Search(
    val pageId: Int,
    val title: String,
    val snippet: String,
    val timestamp: String
  )
}
