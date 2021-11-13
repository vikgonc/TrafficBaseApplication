package com.example.trafficBase.repository

import com.example.trafficBase.model.Car
import org.springframework.data.repository.CrudRepository
import java.util.*

interface CarRepository : CrudRepository<Car, Long> {
    fun findCarByNumber(number: String?): Optional<Car>
}