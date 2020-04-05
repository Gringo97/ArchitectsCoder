package com.oscardelafuente.data.source

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}