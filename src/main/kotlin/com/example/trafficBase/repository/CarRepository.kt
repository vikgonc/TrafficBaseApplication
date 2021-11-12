package com.example.trafficBase.repository

import com.example.trafficBase.model.Car
import org.springframework.data.repository.CrudRepository

interface CarRepository : CrudRepository<Car, Long> {

}