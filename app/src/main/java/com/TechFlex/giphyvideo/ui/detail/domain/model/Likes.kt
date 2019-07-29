package com.TechFlex.giphyvideo.ui.detail.domain.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

@Entity
data class Likes(
        @Id var id: Long = 0,
        var videoId: String? = null,
        var totalLikes: Int? = 0,
        var totalDislikes: Int? = 0
)