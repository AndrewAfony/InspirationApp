package com.myapp.inspirationapp.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.myapp.inspirationapp.utils.makeNotification
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import retrofit2.HttpException
import java.io.IOException

@HiltWorker
class RandomQuoteWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val dependency: WorkersDependencies
): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        return try {

            val appContext = applicationContext

            val result = dependency.api.getRandomQuote().toRandomQuote()

            dependency.db.dao.addQuote(result)

            makeNotification("Get your inspiration", appContext)

            Result.success()

        } catch (e: HttpException) {
            Result.failure(
                workDataOf("error" to e.localizedMessage)
            )
        } catch (e: IOException) {
            Result.failure(
                workDataOf("error" to e.localizedMessage)
            )
        } catch (e: Throwable) {
            Result.failure(
                workDataOf("error" to e.localizedMessage)
            )
        }


    }
}