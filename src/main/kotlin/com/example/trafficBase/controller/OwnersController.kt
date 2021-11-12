package com.example.trafficBase.controller

import com.example.trafficBase.model.Owner
import com.example.trafficBase.repository.OwnerRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/owners")
class OwnersController(private val ownerRepo: OwnerRepository) {
    @GetMapping
    fun all(): Iterable<Owner> = ownerRepo.findAll()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody owner: Owner) = ownerRepo.save(owner)

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.FOUND)
    fun read(@PathVariable id: Long) = ownerRepo.findById(id)

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Long, @RequestBody owner: Owner) = ownerRepo.save(owner.copy(id = id))

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) = ownerRepo.deleteById(id)
}