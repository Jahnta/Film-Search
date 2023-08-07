package com.example.film_search.data

import com.example.film_search.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response

}