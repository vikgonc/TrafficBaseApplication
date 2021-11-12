package com.example.trafficBase.repository

import com.example.trafficBase.model.Owner
import org.springframework.data.repository.CrudRepository

interface OwnerRepository : CrudRepository<Owner, Long> {

}