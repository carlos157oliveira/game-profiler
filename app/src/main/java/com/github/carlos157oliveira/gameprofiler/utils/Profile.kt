package com.github.carlos157oliveira.gameprofiler.utils

import kotlin.random.Random

class Profile {

    companion object {

        private val letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@$&*+=/-\\<>,:".toCharArray()

        fun getQueryString(params: Map<String, String?>): String {
            val queryList = mutableListOf<String>()
            for((key, value) in params) {
                value?.let {
                    queryList.add("${key}=${value}")
                }
            }
            return queryList.joinToString("&")
        }

        fun generateRandomName(length: Int): String {
            val nameList = mutableListOf<Char>()
            for(i in 0 until length) {
                nameList.add(letters[Random.nextInt(letters.size)])
            }
            return nameList.joinToString("")
        }
    }
}