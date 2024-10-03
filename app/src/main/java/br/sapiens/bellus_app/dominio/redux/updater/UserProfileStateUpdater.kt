package br.sapiens.bellus_app.dominio.redux.updater

import br.sapiens.bellus_app.dominio.model.event.UserProfileEvent
import br.sapiens.bellus_app.dominio.model.state.UserProfileType
import br.sapiens.bellus_app.dominio.redux.ApplicationState
import javax.inject.Inject

class UserProfileStateUpdater @Inject constructor() {

    fun update(
        event: UserProfileEvent,
        currentState: ApplicationState,
    ): ApplicationState {
        val newState = when (event) {
            is UserProfileEvent.SetClientProfile -> {
                currentState.userProfileState.copy(
                    profileType = UserProfileType.CLIENT,
                    clientInfo = event.clientInfo
                )
            }

            is UserProfileEvent.SetProfessionalProfile -> {
                currentState.userProfileState.copy(
                    profileType = UserProfileType.PROFESSIONAL,
                    professionalInfo = event.professionalInfo
                )
            }
        }
        return currentState.copy(userProfileState = newState)
    }
}