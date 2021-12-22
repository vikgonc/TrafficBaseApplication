package com.example.trafficBase.repository

import com.example.trafficBase.model.Car
import org.springframework.data.domain.Sort
import org.springframework.data.repository.CrudRepository

interface CarRepository : CrudRepository<Car, Long> {
    fun findAllByArchivedIsNull(): List<Car>

    fun findAllByArchivedIsNull(sort: Sort): List<Car>

    fun findByNumberAndArchivedIsNull(number: String): Car?

    //        Тут оставил findCarById, так как в CrudRepository есть
//    обычный findById, возвращающий optional, а мне нужен nullable
    fun findCarById(id: Long): Car?

    fun findByIdAndArchivedIsNull(id: Long): Car?
}