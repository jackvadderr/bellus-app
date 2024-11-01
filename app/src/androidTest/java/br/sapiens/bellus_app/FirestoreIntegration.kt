package br.sapiens.bellus_app

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.firestore.FirestoreDatabaseService
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import junit.framework.TestCase.assertEquals

@RunWith(AndroidJUnit4::class)
class FirestoreIntegrationTest {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var dbService: FirestoreDatabaseService

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        // Configura manualmente o FirebaseApp, caso ele ainda não esteja inicializado
        if (FirebaseApp.getApps(context).isEmpty()) {
            val options = FirebaseOptions.Builder()
                .setProjectId("bellus-app-48854")
                .setApplicationId("1:410851760544:android:c485c5c0935b0d87482cc5")
                .setApiKey("AIzaSyBQWxgUGSGKRRkVZE1vvkk2f5nRhNrEqOk")
                .build()
            FirebaseApp.initializeApp(context, options)
        }

        firestore = FirebaseFirestore.getInstance()
        dbService = FirestoreDatabaseService()
    }

    @After
    fun tearDown() {
        runBlocking {
            // Remove todos os documentos de teste criados na coleção "appointments"
            val docs = firestore.collection("appointments").get().await().documents
            docs.forEach { it.reference.delete().await() }
        }
    }

    @Test
    fun testSaveAndRetrieveAppointment() = runBlocking {
        val appointment = AppointmentDTO(
            id = "1",
            userId = "user1",
            establishmentId = "establishment1",
            serviceId = "service1",
            scheduled_date = "2024-10-21T14:00:00",
            statusRequest = "Pendente",
            completionDate = ""
        )

        // Salva o agendamento no Firestore
        dbService.saveAppointment(appointment)

        // Aguarda a busca do agendamento para verificação
        val retrievedAppointment = dbService.getAppointmentById("1")

        // Verifica se o agendamento salvo e o recuperado são iguais
        assertEquals(appointment, retrievedAppointment)
    }
}
// ainda não está funfando