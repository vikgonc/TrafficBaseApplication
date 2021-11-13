package com.example.trafficBase.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "notes")
data class Note(
    @Id
    @Column(name = "note_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "note_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    val changeDate: Date? = null,

    @ManyToOne
    val owner: Owner? = null
)