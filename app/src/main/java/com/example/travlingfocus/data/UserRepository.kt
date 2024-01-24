package com.example.travlingfocus.data

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getAllusers(): Flow<List<User>>

    fun getUserStream(id: Int): Flow<User?>

    fun getUserByEmail(email: String): Flow<User?>

    suspend fun insertUser(user: User)

    suspend fun deleteUser(user: User)

    suspend fun updateUser(user: User)

}