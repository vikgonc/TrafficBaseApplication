package com.example.trafficBase.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "lines")
data class LineInTDP(
    @Id
    @Column(name = "line_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "change_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    val ChangeDate: Date? = null,

    @ManyToOne
    val owner:Owner? = null,
)