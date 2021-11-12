package com.example.trafficBase.model

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "owners")
data class Owner(
    @Id
    @Column(name = "owner_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "full_name")
    val fullName: String? = null,

    @Column(name = "reg_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    val carDate: Date? = null,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    val cars: MutableList<Car>? = null
)