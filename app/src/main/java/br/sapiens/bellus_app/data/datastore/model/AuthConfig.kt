package br.sapiens.bellus_app.data.datastore.model

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import br.sapiens.bellus_app.AuthConfig
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object AuthConfigSerializer : Serializer<AuthConfig> {
    override val defaultValue: AuthConfig = AuthConfig.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AuthConfig {
        try {
            return AuthConfig.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: AuthConfig, output: OutputStream) = t.writeTo(output)
}