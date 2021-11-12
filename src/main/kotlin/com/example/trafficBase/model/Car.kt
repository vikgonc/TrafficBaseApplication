package com.example.trafficBase.model

import org.springframework.format.annotation.DateTimeFormat
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
    val title: String? = null,

    @Column(name = "license_number")
    val licenseNumber: String? = null,

    @Column(name = "first_change_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    val firstChangeDate: Date? = null,

    @Column(name = "last_change_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    val lastChangeDate: Date? = null,

    @ManyToOne
    val owner: Owner? = null
)