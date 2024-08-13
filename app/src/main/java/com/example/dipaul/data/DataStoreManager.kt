package com.example.dipaul.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private  val Context.dataStore: DataStore<Preferences> by preferencesDataStore("data_store")
class DataStoreManager( val context: Context) {

suspend fun saveAccount(account: Account){
    context.dataStore.edit {
        it[stringPreferencesKey("email")] = account.email
        it[stringPreferencesKey("roll")] = account.roll
    }
}

    fun getAccount() = context.dataStore.data.map {
        return@map Account(
            it[stringPreferencesKey("email")] ?: "",
            it[stringPreferencesKey("roll")] ?: ""
        )
    }

}