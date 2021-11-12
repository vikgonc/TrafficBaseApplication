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
    val title: String? = null,

    @Column(name = "license_number")
    val licenseNumber: String? = null,

    @Column(name = "first_change_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    val firstChangeDate: Date? = null,

    @Column(name = "last_change_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    val lastChangeDate: Date? = null,

    @ManyToOne
    @JsonIgnore
    val owner: Owner? = null,

    @OneToMany(fetch = FetchType.LAZY,cascade = [CascadeType.ALL])
    @JsonIgnore
    var TDP: MutableList<LineInTDP>? = null
)