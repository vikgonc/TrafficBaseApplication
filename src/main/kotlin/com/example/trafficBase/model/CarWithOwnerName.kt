package com.example.trafficBase.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

class CarWithOwnerName(
    car: Car,

    var id: Long? = car.id,

    val title: String? = car.title,

    val number: String? = car.number,

    @JsonFormat(pattern = "dd-MM-yyyy")
    val regDate: Date? = car.regDate,

    @JsonFormat(pattern = "dd-MM-yyyy")
    val lastChangeDate: Date? = car.lastChangeDate,

    val fullName: String? = null,
)
