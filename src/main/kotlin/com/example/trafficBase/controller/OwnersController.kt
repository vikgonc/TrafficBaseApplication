package com.example.trafficBase.controller

import com.example.trafficBase.model.Car
import com.example.trafficBase.model.Owner
import com.example.trafficBase.repository.CarRepository
import com.example.trafficBase.repository.OwnerRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/owners")
class OwnersController(
    private val ownerRepo: OwnerRepository,
    private val carRepo: CarRepository
) {
    @GetMapping // get all owners
    fun getAllOwners(): List<Owner> = ownerRepo.findAllByArchivedIsNull()

    @GetMapping("{id}") // owner's registered cars by id
    fun getOwnerCarsById(@PathVariable id: Long): ResponseEntity<List<Car>> {
        val ownerById = ownerRepo.findByIdAndArchivedIsNull(id)
        val responseStatus = ownerById?.let { 200 } ?: 404
        val responseBody = ownerById?.cars ?: listOf()
        return ResponseEntity
            .status(responseStatus)
            .body(responseBody)
    }

    @PostMapping // create owner
    fun createOwner(@RequestBody owner: Owner): ResponseEntity<Owner> {
        val ownerByName = ownerRepo.findByNameAndArchivedIsNull(owner.name)
        val responseEntity: ResponseEntity<Owner> = if (ownerByName == null) {
            val ownerAfterSave = ownerRepo.save(owner)
            ResponseEntity
                .status(200)
                .body(ownerAfterSave)
        } else
            ResponseEntity
                .status(453)
                .body(null)
        return responseEntity
    }

    @DeleteMapping("{id}") // archive owner by id
    fun archiveOwnerById(@PathVariable id: Long) {
        val ownerById = ownerRepo.findByIdAndArchivedIsNull(id)
        ownerById?.let {
            ownerById.cars.forEach { // проставляем null владельца во всех машинах этого овнера
                carRepo.save(it.copy(owner = null))
            }
            ownerRepo.save(ownerById.copy(archived = LocalDateTime.now()))
        }
    }
}