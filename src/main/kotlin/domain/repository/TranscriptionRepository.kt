package domain.repository

import java.io.File

interface TranscriptionRepository {

    suspend fun getTranscription(audio: File):Result<String>

}

