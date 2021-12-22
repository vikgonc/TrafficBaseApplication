package com.example.trafficBase.repository

import com.example.trafficBase.model.Note
import org.springframework.data.repository.CrudRepository

interface NoteRepository : CrudRepository<Note, Long>