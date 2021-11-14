package com.example.trafficBase.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

class NumberAndDate(
    val number: String? = null,
    @JsonFormat(pattern = "dd-MM-yyyy")
    val date: Date
)