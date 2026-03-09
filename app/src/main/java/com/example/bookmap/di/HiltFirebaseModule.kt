package com.example.bookmap.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module // O modulo é a classe que instrui o hilt a criar objetos
@InstallIn(SingletonComponent::class) // decide que ciclo de vida do objeto é parelho ao ciclo de vida do app, quando o app fecha ele é encerrado
object HiltFirebaseModule {

    @Provides // providencia o objeto externo necessario
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides // providencia o objeto externo necessario
    fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()
}
