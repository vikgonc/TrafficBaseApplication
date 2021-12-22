package com.example.trafficBase.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.Pattern

@Entity
@Table(name = "cars")
data class Car(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "title")
    val title: String,

    @Column(name = "number")
    @field:Pattern(regexp = "^[авекмнорстух][0-9]{3}[авекмнорстух]{2}[0-9]{2,3}$")
    val number: String,

    @Column(name = "created")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    val created: LocalDateTime = LocalDateTime.now(),

    @Column(name = "changed")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    val changed: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    val owner: Owner? = null,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE], mappedBy = "car")
    @JsonIgnore
    val passport: List<Note> = listOf(),

    @JsonIgnore
    val archived: LocalDateTime? = null
)