package br.sapiens.bellus_app.firestore

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO

class FirestoreDatabaseService {
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun saveAppointment(appointment: AppointmentDTO) {
        firestore.collection("appointments").document(appointment.id).set(appointment).await()
    }

    suspend fun getAppointmentById(id: String): AppointmentDTO? {
        return firestore.collection("appointments").document(id).get().await().toObject(AppointmentDTO::class.java)
    }
}
