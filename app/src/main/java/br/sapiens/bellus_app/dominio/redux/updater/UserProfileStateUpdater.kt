package br.sapiens.bellus_app.dominio.redux.updater

import android.content.Context
import br.sapiens.bellus_app.data.datastore.impl.UserDataConfigManagerImpl.updateClientData
import br.sapiens.bellus_app.data.datastore.impl.UserDataConfigManagerImpl.updateProfessionalData
import br.sapiens.bellus_app.dominio.model.event.UserProfileEvent
import br.sapiens.bellus_app.dominio.model.state.UserProfileType
import br.sapiens.bellus_app.dominio.redux.ApplicationState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserProfileStateUpdater @Inject constructor() {

    fun update(
        event: UserProfileEvent,
        currentState: ApplicationState,
        context: Context
    ): ApplicationState {
        val newState = when (event) {

            is UserProfileEvent.SetClientProfile -> {
                CoroutineScope(Dispatchers.IO).launch {
                    context.updateClientData(
                        event.clientInfo?.name.toString(),
                        event.clientInfo?.email.toString(),
                        event.clientInfo?.phone.toString(),
                    )
                }
                currentState.userProfileState.copy(
                    currentProfileType = UserProfileType.CLIENT,
                    clientInfo = event.clientInfo
                )
            }

            is UserProfileEvent.SetProfessionalProfile -> {
                CoroutineScope(Dispatchers.IO).launch {
                    context.updateProfessionalData(
                        event.professionalInfo?.id.toString(),
                        event.professionalInfo?.name.toString(),
                        event.professionalInfo?.email.toString(),
                    )
                }
                currentState.userProfileState.copy(
                    currentProfileType = UserProfileType.PROFESSIONAL,
                    professionalInfo = event.professionalInfo
                )
            }
        }
        return currentState.copy(userProfileState = newState)
    }
}