package br.sapiens.bellus_app.data.datastore.model

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import br.sapiens.bellus_app.AppConfig
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object AppConfigSerializer : Serializer<AppConfig> {
    override val defaultValue: AppConfig = AppConfig.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AppConfig {
        try {
            return AppConfig.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: AppConfig, output: OutputStream) = t.writeTo(output)
}