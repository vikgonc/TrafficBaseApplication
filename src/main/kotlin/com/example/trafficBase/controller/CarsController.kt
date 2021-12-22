package com.example.trafficBase.controller

import com.example.trafficBase.model.Car
import com.example.trafficBase.model.Note
import com.example.trafficBase.model.Owner
import com.example.trafficBase.repository.CarRepository
import com.example.trafficBase.repository.NoteRepository
import com.example.trafficBase.repository.OwnerRepository
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Order
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.validation.Valid
import javax.validation.constraints.Pattern

@RestController
@Validated
@RequestMapping("/cars")
class CarsController(
    private val carRepo: CarRepository,
    private val ownerRepo: OwnerRepository,
    private val noteRepo: NoteRepository
) {
    @GetMapping // get all registered cars
    fun getAllRegisteredCars(sort: Sort): List<Car> = carRepo.findAllByArchivedIsNull()

    @GetMapping("filter")
    fun filterByCreatedAndChangedAndAmount(
        @RequestParam(value = "created") created: String?,
        @RequestParam(value = "changed") changed: String?,
        @RequestParam(value = "amount") amount: Int?,
    ): List<Car> {
        val cars = carRepo.findAllByArchivedIsNull()
        val carsLessThenCreated =
            created?.let { dat ->
                val date = LocalDateTime.parse(dat, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
                cars.filter { it.created < date }
            } ?: cars
        val carsLessThenChanged =
            changed?.let { dat ->
                val date = LocalDateTime.parse(dat, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
                carsLessThenCreated.filter { it.changed < date }
            } ?: carsLessThenCreated
        val carsWithAmountOwners =
            amount?.let { amt ->
                carsLessThenChanged.filter { it.passport.size == amt }
            } ?: carsLessThenChanged
        return carsWithAmountOwners
    }

    @GetMapping("sort") // sort cars by title and created
    fun sortByTitleAndCreated(
        @RequestParam(value = "title")
        title: String?,
        @RequestParam(value = "created")
        created: String?
    ): List<Car> {
        val titleOrder = title?.let {
            Order(Sort.Direction.fromString(title), "title")
        }
        val createdOrder = created?.let {
            Order(Sort.Direction.fromString(created), "created")
        }
        val orderList = listOfNotNull(titleOrder, createdOrder)
        return carRepo.findAllByArchivedIsNull(Sort.by(orderList))
    }

    @GetMapping("{id}") // find car by id
    fun getCarById(@PathVariable id: Long): ResponseEntity<Car> {
        val carById = carRepo.findByIdAndArchivedIsNull(id)
        val responseStatus = carById?.let { 200 } ?: 404
        return ResponseEntity
            .status(responseStatus)
            .body(carById)
    }

    @GetMapping("number") // find car by number
    fun getCarByNumber(
        @Pattern(regexp = "^[авекмнорстух][0-9]{3}[авекмнорстух]{2}[0-9]{2,3}$")
        @RequestParam(value = "number")
        number: String
    ): ResponseEntity<Car> {
        val carByNumber = carRepo.findByNumberAndArchivedIsNull(number)
        val responseStatus = carByNumber?.let { 200 } ?: 452
        return ResponseEntity
            .status(responseStatus)
            .body(carByNumber)
    }

    @GetMapping("passport/{id}") // get passport by car id
    fun getPassportByCarId(@PathVariable id: Long): ResponseEntity<List<Note>> {
        val carById = carRepo.findCarById(id)
        val responseStatus = carById?.let { 200 } ?: 404
        val responseBody = carById?.passport?.sortedByDescending { it.created } ?: listOf()
        return ResponseEntity
            .status(responseStatus)
            .body(responseBody)
    }

    @PostMapping // create car
    fun createCar(@Valid @RequestBody car: Car): ResponseEntity<Car> {
        val carByNumber = carRepo.findByNumberAndArchivedIsNull(car.number)
        val responseEntity: ResponseEntity<Car> = if (carByNumber == null) {
            val carAfterSave = carRepo.save(car)
            ResponseEntity
                .status(200)
                .body(carAfterSave)
        } else
            ResponseEntity
                .status(451)
                .body(null)
        return responseEntity
    }

    @PatchMapping("updateownerandnumber/{id}") // update owner by car id
    fun updateOwnerAndNumberByCarId(
        @PathVariable id: Long,
        @RequestBody owner: Owner,
        @Pattern(regexp = "^[авекмнорстух][0-9]{3}[авекмнорстух]{2}[0-9]{2,3}$")
        @RequestParam(value = "number") number: String
    ): ResponseEntity<Car> {
        val carById = carRepo.findByIdAndArchivedIsNull(id)
        val carByNumber = carRepo.findByNumberAndArchivedIsNull(number)
        val responseEntity: ResponseEntity<Car> = when {
            carById == null ->
                ResponseEntity
                    .status(404)
                    .body(null)
            carByNumber != null && carByNumber.id != carById.id ->
                ResponseEntity
                    .status(451)
                    .body(null)
            owner.id != null ->
                ResponseEntity
                    .status(456)
                    .body(null)
            else -> {
                val newOwner = ownerRepo.findByNameAndArchivedIsNull(owner.name) ?: ownerRepo.save(owner)
                noteRepo.save(Note(owner = newOwner, car = carById))
                val carAfterSave =
                    carRepo.save(carById.copy(number = number, changed = LocalDateTime.now(), owner = newOwner))
                ResponseEntity
                    .status(200)
                    .body(carAfterSave)
            }
        }
        return responseEntity
    }

    @DeleteMapping("{id}") // archive car by id
    fun archiveCarById(@PathVariable id: Long) {
        val carById = carRepo.findByIdAndArchivedIsNull(id)
        carById?.let {
            val changeDate = LocalDateTime.now()
            carRepo.save(carById.copy(owner = null, changed = changeDate, archived = changeDate))
        }
    }
}