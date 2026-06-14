package app.ali.titan.screens.person.domain

import app.ali.titan.screens.person.data.PersonDetail


interface PersonRepository {
    suspend fun getPersonDetail(personId: Int): PersonDetail
}
