package com.rafaelleal.android.turmasfirebaseproject.application

import android.app.Application
import com.rafaelleal.android.turmasfirebaseproject.repository.TurmasRepository


// Lembrar de adicionar ao manifesto dentro da tag application:
// android:name=".application.TurmasApplication"

class TurmasApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        TurmasRepository.initialize()
    }

}