package com.example.trafficBase.controller

import com.example.trafficBase.exception.BadRequestException
import com.example.trafficBase.exception.NotFoundException
import com.example.trafficBase.model.Note
import com.example.trafficBase.model.NumberAndDate
import com.example.trafficBase.model.Owner
import com.example.trafficBase.repository.CarRepository
import com.example.trafficBase.repository.OwnerRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/owners")
class OwnersController(
    private val ownerRepo: OwnerRepository,
    private val carRepo: CarRepository
) {
    @GetMapping // get all owners
    fun all(): Iterable<Owner> = ownerRepo.findAll()

    @PostMapping // create owner
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody owner: Owner) {
        ownerRepo.save(owner)
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.FOUND)
    fun read(@PathVariable id: Long) = ownerRepo.findById(id)


    @PatchMapping("addcar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateCarList(@PathVariable id: Long, @RequestBody data: NumberAndDate) {
        val res = ownerRepo.findById(id)
        if (!res.isPresent)
            throw NotFoundException()
        val car = carRepo.findCarByNumber(data.number)
        if (!car.isPresent)
            throw BadRequestException()
        val carInBase = car.get()
        val ownerInBase = res.get()
        ownerInBase.cars?.add(carInBase)
        carInBase.passport?.add(Note(changeDate = data.date, owner = ownerInBase))
        if (carInBase.lastChangeDate == null)
            carInBase.lastChangeDate = data.date
        else
            if (data.date > carInBase.lastChangeDate)
                carInBase.lastChangeDate = data.date
        ownerRepo.save(ownerInBase)
    }


    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) = ownerRepo.deleteById(id)
}