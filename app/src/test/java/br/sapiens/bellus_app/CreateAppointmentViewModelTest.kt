package br.sapiens.bellus_appimport
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.dominio.redux.stores.MarketplaceStore
import br.sapiens.bellus_app.dominio.redux.stores.UserProfileStore
import br.sapiens.bellus_app.dominio.sdk.network.schemas.RequestAppointmentSchema
import br.sapiens.bellus_app.dominio.usecase.appointment.PostAppointmentUseCase
import br.sapiens.bellus_app.presentation.ui.model.Duration
import br.sapiens.bellus_app.presentation.ui.model.ServicePost
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails
import br.sapiens.bellus_app.presentation.viewmodels.CreateAppointmentViewModel
import br.sapiens.bellus_app.presentation.viewmodels.CreateAppointmentViewModel.ViewState
import br.sapiens.bellus_app.utils.State
import com.nhaarman.mockitokotlin2.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.*
import org.robolectric.RobolectricTestRunner


@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class CreateAppointmentViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var postAppointmentUseCase: PostAppointmentUseCase

    @Mock
    private lateinit var storeUser: UserProfileStore

    @Mock
    private lateinit var storeMarketplace: MarketplaceStore

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: CreateAppointmentViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        postAppointmentUseCase = mock()
        storeUser = mock()
        storeMarketplace = mock()
        viewModel = CreateAppointmentViewModel(postAppointmentUseCase, storeUser, storeMarketplace)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test successful appointment creation`() = runTest {
        // Dados de entrada para o agendamento
        val appointmentDTO = AppointmentDTO(
            id = "1",
            userId = "user1",
            establishmentId = "establishment1",
            serviceId = "service1",
            scheduled_date = "2024-10-21T14:00:00",
            statusRequest = "Pendente",
            completionDate = ""
        )
        val requestSchema = RequestAppointmentSchema(
            user_id = "user1",
            establishment_id = "establishment1",
            service_id = "service1",
            scheduled_date = "2024-10-21T14:00:00",
            status_request = "Pendente",
            completion_date = ""
        )

        // Criar uma instância de ServiceDetails
        val serviceDetails = ServiceDetails(
            id = "service1",
            name = "Serviço Teste",
            duration = Duration(type = "hour", value = 1.0f),
            preco = 100.0f
        )

        // Configuração dos mocks para o usuário e serviço
        whenever(storeUser.getCurrentUserId()).thenReturn("user1")
        whenever(storeMarketplace.getEstablishmentItemId()).thenReturn("establishment1")
        whenever(storeMarketplace.getCurrentServiceDetails()).thenReturn(serviceDetails)
        whenever(postAppointmentUseCase.invoke(any())).thenReturn(State.Success(appointmentDTO))

        // Observador para capturar os estados da view
        val observer = Observer<ViewState> { viewState ->
            println("Observer received state: $viewState")
        }
        viewModel.viewState.observeForever(observer)

        // Trigger do evento com o objeto `ServicePost`
        val servicePost = ServicePost(
            name = "Serviço Teste",
            duration = Duration(type = "hour", value = 1.0f),
            preco = 100.0f
        )

        // Configura a data e hora do agendamento
        viewModel.selectedDate = 1683091200000 // Data simulada
        viewModel.selectedTimeSlot = "14:00"
        viewModel.triggerEvent(CreateAppointmentViewModel.ViewEvent.CreateAppointment(servicePost))

        // Avança a execução das tarefas pendentes
        advanceUntilIdle()

        // Verificações
        val state = viewModel.viewState.value
        assert(state is ViewState.CreateAppointment)
        state as ViewState.CreateAppointment
        assertEquals(appointmentDTO, state.post)
        viewModel.viewState.removeObserver(observer)
    }
}
