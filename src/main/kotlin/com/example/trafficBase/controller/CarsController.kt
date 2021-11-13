package com.example.trafficBase.controller

import com.example.trafficBase.exception.BadRequestException
import com.example.trafficBase.exception.NotFoundException
import com.example.trafficBase.model.Car
import com.example.trafficBase.model.CarWithOwnerName
import com.example.trafficBase.repository.CarRepository
import com.example.trafficBase.repository.OwnerRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cars")
class CarsController(
    private val carRepo: CarRepository,
    private val ownerRepo: OwnerRepository
) {
    @GetMapping
    fun all(): Iterable<Car> = carRepo.findAll()

    @GetMapping("registered") // get all registered cars
    fun allRegistered(): Iterable<CarWithOwnerName> {
        val res: MutableList<CarWithOwnerName> = mutableListOf()
        carRepo.findAll().forEach {
            if (it.number != null)
                res.add(carWithName(it))
        }
        return res
    }

    @PostMapping // create car
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody car: Car) {
        carRepo.save(car)
    }

    @GetMapping("{id}") // find car by id
    @ResponseStatus(HttpStatus.FOUND)
    fun readById(@PathVariable id: Long): CarWithOwnerName {
        val res = carRepo.findById(id)
        if (res.isPresent) {
            return carWithName(res.get())
        } else
            throw NotFoundException()
    }

    @GetMapping("number/{num}") // find car by number
    @ResponseStatus(HttpStatus.FOUND)
    fun readByNumber(@PathVariable num: String): CarWithOwnerName {
        val res = carRepo.findCarByNumber(num)
        if (res.isPresent)
            return carWithName(res.get())
        else
            throw NotFoundException()
    }

    @PatchMapping("register/{id}") // registering car
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateNumber(@PathVariable id: Long, @RequestBody car: Car) {
        val res = carRepo.findById(id)
        if (!res.isPresent)
            throw NotFoundException()
        val foundCar = res.get()
        if (carRepo.findCarByNumber(car.number).isPresent)
            throw BadRequestException()
        if (foundCar.regDate == null)
            carRepo.save(foundCar.copy(number = car.number, regDate = car.regDate))
        else
            carRepo.save(foundCar.copy(number = car.number))
    }

    @PatchMapping("unregister/{id}") // unregister car
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun removeNumber(@PathVariable id: Long) {
        val res = carRepo.findById(id)
        if (!res.isPresent)
            throw NotFoundException()
        val foundCar = res.get()
        carRepo.save(foundCar.copy(number = null))
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) = carRepo.deleteById(id)

    fun carWithName(car: Car): CarWithOwnerName {
        val owner = ownerRepo.findOwnerByCarId(car.id)
        return if (owner.isPresent)
            CarWithOwnerName(car, fullName = ownerRepo.findOwnerByCarId(car.id).get().fullName)
        else
            CarWithOwnerName(car)
    }
}