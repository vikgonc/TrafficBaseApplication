package com.example.trafficBase.controller

import com.example.trafficBase.model.Car
import com.example.trafficBase.repository.CarRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cars")
class CarsController(private val carRepo: CarRepository) {
    @GetMapping
    fun all(): Iterable<Car> = carRepo.findAll()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody car: Car) = carRepo.save(car)

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.FOUND)
    fun read(@PathVariable id: Long) = carRepo.findById(id)

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Long, @RequestBody car: Car) = carRepo.save(car.copy(id = id))

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) = carRepo.deleteById(id)
}