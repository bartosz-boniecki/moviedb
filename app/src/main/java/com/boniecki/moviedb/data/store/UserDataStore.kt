package com.boniecki.moviedb.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.boniecki.moviedb.data.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.io.IOException
import javax.inject.Inject

class UserDataStore @Inject constructor(private val context: Context) {

    private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

    val favouriteMovies: Flow<List<Movie>>
        get() = readPreferencesSafely().map { preference ->
            preference[stringPreferencesKey(PreferenceKeys.FAVOURITE_MOVIES_PREFERENCE_KEY)]?.let { json ->
                Json.decodeFromString(
                    ListSerializer(Movie.serializer()),
                    json
                )
            } ?: emptyList()
        }

    suspend fun saveFavouriteMovies(movies: List<Movie>) {
        context.userDataStore.edit { preferences ->
            val json = Json.encodeToString(
                ListSerializer(Movie.serializer()),
                movies
            )
            preferences[stringPreferencesKey(PreferenceKeys.FAVOURITE_MOVIES_PREFERENCE_KEY)] = json
        }
    }

    private fun readPreferencesSafely() = context.userDataStore.data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }

    private companion object {
        const val DATA_STORE_NAME = "userDataStore"
    }
}