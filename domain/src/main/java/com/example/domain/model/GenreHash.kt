package com.example.domain.model

class GenreHash {
    private val genreMap = HashMap<String,Int>()
    init {
        genreMap["액션"] = 28
        genreMap["어드벤쳐"] = 12
        genreMap["애니메이션"] = 16
        genreMap["코미디"] = 35
        genreMap["범죄"] = 80
        genreMap["다큐멘터리"] = 99
        genreMap["드라마"] = 18
        genreMap["가족"] = 10751
        genreMap["판타지"] = 14
        genreMap["역사"] = 36
        genreMap["공포"] = 27
        genreMap["음악"] = 10402
        genreMap["미스테리"] = 9648
        genreMap["로맨스"] = 10749
        genreMap["SF"] = 878
        genreMap["TV 영화"] = 10770
        genreMap["스릴러"] = 53
        genreMap["전쟁"] = 10752
        genreMap["서부"] = 37
    }

    fun getAllKeys(): List<String>{
        val list = mutableListOf<String>()
        for(item in genreMap){
            list.add(item.key)
        }
        return list.toList()
    }
}