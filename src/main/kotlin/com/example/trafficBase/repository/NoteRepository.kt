package com.example.trafficBase.repository

import com.example.trafficBase.model.Note
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface NoteRepository : CrudRepository<Note, Long> {
    @Query(
        "select * from notes join cars_passport on note_id = passport_note_id where car_car_id = :id order by note_date desc",
        nativeQuery = true
    )
    fun findNotesByCarIdSorted(@Param("id") id: Long?): Optional<List<Note>>
}