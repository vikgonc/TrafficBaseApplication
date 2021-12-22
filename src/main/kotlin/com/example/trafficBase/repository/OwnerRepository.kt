package com.example.trafficBase.repository

import com.example.trafficBase.model.Owner
import org.springframework.data.repository.CrudRepository

interface OwnerRepository : CrudRepository<Owner, Long> {
    fun findByNameAndArchivedIsNull(name: String): Owner?

    fun findByIdAndArchivedIsNull(id: Long): Owner?

    fun findAllByArchivedIsNull(): List<Owner>
}