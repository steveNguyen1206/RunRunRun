package com.example.travlingfocus.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineUserRepository  @Inject constructor
    (private val userDao: UserDao) : UserRepository  {
    override fun getAllusers(): Flow<List<User>> = userDao.getAllUsers()

    override  fun getUserStream(id: Int): Flow<User?> = userDao.getUser(id)
    override fun getUserByEmail(email: String): Flow<User?> = userDao.getUserByEmail(email)

    override suspend fun insertUser(user: User) =  userDao.insert(user)

    override suspend fun deleteUser(user: User) =  userDao.delete(user)

    override suspend fun updateUser(user: User) = userDao.update(user)

}