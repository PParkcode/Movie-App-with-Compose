package com.example.domain.model

data class Genre(val id: Int, val name: String)

object Genres {
    val ACTION = Genre(28, "액션")
    val ADVENTURE = Genre(12, "어드벤쳐")
    val ANIMATION = Genre(16, "애니메이션")
    val COMEDY = Genre(35, "코미디")
    val CRIME = Genre(80, "범죄")
    val DOCUMENTARY = Genre(99, "다큐멘터리")
    val DRAMA = Genre(18, "드라마")
    val FAMILY = Genre(10751, "가족")
    val FANTASY = Genre(14, "판타지")
    val HISTORY = Genre(36, "역사")
    val HORROR = Genre(27, "공포")
    val MUSIC = Genre(10402, "음악")
    val MYSTERY = Genre(9648, "미스테리")
    val ROMANCE = Genre(10749, "로맨스")
    val SF = Genre(878, "SF")
    val TV_MOVIE = Genre(10770, "TV 영화")
    val THRILLER = Genre(53, "스릴러")
    val WAR = Genre(10752, "전쟁")
    val WESTERN = Genre(37, "서부")

    fun getEverything(): List<Genre> {
        return listOf(
            ACTION, ADVENTURE, ANIMATION, COMEDY, CRIME, DOCUMENTARY, DRAMA, FAMILY, FANTASY,
            HISTORY, HORROR, MUSIC, MYSTERY, ROMANCE, SF, TV_MOVIE, THRILLER, WAR, WESTERN
        )
    }
}


