package com.example.trafficBase.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
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
    @JsonFormat(pattern = "dd-MM-yyyy")
    val regDate: Date? = null,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val cars: MutableList<Car>? = null
)