//package br.sapiens.bellus_app
//
//import br.sapiens.bellus_app.dominio.model.AuthState
//import junit.framework.Assert.assertEquals
//import org.junit.Test
//
//class AuthStateTest {
//    @Test
//    fun testEstadoAutenticado() {
//        val authState = AuthState.Authenticated(
//            userId = "123",
//            email = "seiladog@gmail.com",
//            isEmailVerified = true,
//            providerId = "paodebatata"
//        )
//
//        assertEquals("123", authState.userId)
//        assertEquals("seiladog@gmail.com", authState.email)
//        assertEquals(true, authState.isEmailVerified)
//        assertEquals("paodebatata", authState.providerId)
//    }
//
//    @Test
//    fun testEstadoNaoAutenticado() {
//        val authState = AuthState.Unauthenticated
//        assert(authState is AuthState.Unauthenticated)
//    }
//}