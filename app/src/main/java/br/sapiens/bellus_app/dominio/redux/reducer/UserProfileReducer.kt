package br.sapiens.bellus_app.dominio.redux.reducer

import br.sapiens.bellus_app.dominio.model.event.UserProfileEvent
import br.sapiens.bellus_app.dominio.model.state.UserProfileState
import br.sapiens.bellus_app.dominio.model.state.UserProfileType
import br.sapiens.bellus_app.dominio.redux.ApplicationState
import br.sapiens.bellus_app.dominio.redux.stores.IStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserProfileReducer @Inject constructor() {

    fun reduce(store: IStore<ApplicationState>): Flow<ApplicationState> {
        return store.stateFlow.map { currentState ->
            val newState: UserProfileState =
                when (val event: UserProfileEvent? = store.getLastAction() as? UserProfileEvent) {
                    is UserProfileEvent.SetClientProfile -> {
                        currentState.userProfileState.copy(
                            currentProfileType = UserProfileType.CLIENT,
                            clientInfo = event.clientInfo
//                                ?: currentState.userProfileState.clientInfo
                        )
                    }

                    is UserProfileEvent.SetProfessionalProfile -> {
                        currentState.userProfileState.copy(
                            currentProfileType = UserProfileType.PROFESSIONAL,
                            professionalInfo = event.professionalInfo
//                                ?: currentState.userProfileState.professionalInfo
                        )
                    }

                    null -> currentState.userProfileState
                }
            ApplicationState(userProfileState = newState)
        }
    }
}