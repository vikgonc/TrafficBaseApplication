package com.example.trafficBase.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "cars")
data class Car(
    @Id
    @Column(name = "car_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "title")
    val title: String,

    @Column(name = "number")
    val number: String? = null,

    @Column(name = "reg_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    val regDate: Date? = null,

    @Column(name = "last_change_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    var lastChangeDate: Date? = null,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JsonIgnore
    var passport: MutableList<Note>? = null
)