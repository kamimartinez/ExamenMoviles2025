package com.app.examen2025.data.mapper

import com.app.examen2025.data.remote.dto.CelebrityDto
import com.app.examen2025.domain.model.Celebrity

fun CelebrityDto.toDomain(): Celebrity =
    Celebrity(
        name = this.name,
        net_worth = this.net_worth,
        gender = this.gender,
        nationality = this.nationality,
        occupation = this.occupation,
        height = this.height,
        birthday = this.birthday,
    )
