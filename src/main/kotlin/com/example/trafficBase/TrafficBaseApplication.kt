package com.example.trafficBase

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TrafficBaseApplication

// Response codes:
// 200: successful request
// 404: Object with this id is not exists
// 451: Duplicate entrance: car number
// 452: Car with this number is not exist
// 453: Duplicate entrance: owner name
// 454: Input field must be not null
// 455: Invalid format for input date
// 456: Received owner with not null id
// 457: Request param is not present!
// 458: Invalid format for input param!
fun main(args: Array<String>) {
    runApplication<TrafficBaseApplication>(*args)
}
