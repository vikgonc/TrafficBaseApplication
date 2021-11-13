package com.example.trafficBase.repository

import com.example.trafficBase.model.Owner
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface OwnerRepository : CrudRepository<Owner, Long> {
    @Query(
        "select * from owners join owners_cars on owner_id = owner_owner_id where cars_car_id = :id",
        nativeQuery = true
    )
    fun findOwnerByCarId(@Param("id") id: Long?): Optional<Owner>
}