package com.sarftec.lovequotes.data.json

import kotlinx.serialization.Serializable

@Serializable
class JsonQuotes(
    val quotes: List<String>
)