package br.sapiens.bellus_app.utils

import android.annotation.SuppressLint
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.TextStyle
import java.util.Locale

@SuppressLint("DefaultLocale")
fun calculateHourDifference(isoString: String, minutesToSubtract: Long): String {
    return try {
        val initialInstant = Instant.parse(isoString)
        val finalInstant = initialInstant.minus(Duration.ofMinutes(minutesToSubtract))
        val duration = Duration.between(finalInstant, initialInstant)
        val hours = duration.toHours()
        val minutes = duration.toMinutes() % 60
        val seconds = duration.seconds % 60
        val result = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        formatTimeDifference(result)
    } catch (e: Exception) {
        e.printStackTrace()
        "00:00:00" // Returns "00:00:00" in case of error
    }
}

@SuppressLint("DefaultLocale")
fun calculateTimeDifference(startTime: String, endTime: String): String {
    return try {
        val startInstant = Instant.parse(startTime)
        val endInstant = Instant.parse(endTime)
        val duration = Duration.between(startInstant, endInstant)
        val hours = duration.toHours()
        val minutes = duration.toMinutes() % 60
        val seconds = duration.seconds % 60
        val result = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        "$startInstant - ${endInstant}: ${formatTimeDifference(result)}"
    } catch (e: DateTimeParseException) {
        e.printStackTrace()
        "00:00:00 - 00:00:00: 00:00:00" // Retorna "00:00:00 - 00:00:00: 00:00:00" em caso de erro de parsing
    }
}


fun formatTimeDifference(timeDifference: String): String {
    val parts = timeDifference.split(":")
    val hours = parts[0].toInt()
    val minutes = parts[1].toInt()
    val seconds = parts[2].toInt()

    val formattedHours = if (hours > 0) "$hours horas" else ""
    val formattedMinutes = if (minutes > 0) "$minutes minutos" else ""
    val formattedSeconds = if (seconds > 0) "$seconds segundos" else ""

    return listOf(formattedHours, formattedMinutes, formattedSeconds)
        .filter { it.isNotEmpty() }
        .joinToString(", ")
}

fun formatIso8601ToDateTimeString(isoString: String): String {
    val instant = Instant.parse(isoString)
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        .withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}

fun getDayOfWeek(dateString: String): String {
    // Usar DateTimeFormatter.ISO_INSTANT para lidar com o formato UTC corretamente
    val instant = Instant.parse(dateString)

    // Converter para ZonedDateTime no UTC
    val zonedDateTime = instant.atZone(ZoneOffset.UTC)

    // Obter o dia da semana e exibir conforme o Locale do sistema
    return zonedDateTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
}


fun getDayOfMonth(dateString: String): Int {
    // Usar Instant.parse para converter diretamente
    val instant = Instant.parse(dateString)

    // Converter para ZonedDateTime no UTC
    val zonedDateTime = instant.atZone(ZoneOffset.UTC)

    // Obter o dia do mês
    return zonedDateTime.dayOfMonth
}


fun getMonth(dateString: String): String {
    // Usar Instant.parse para converter diretamente
    val instant = Instant.parse(dateString)

    // Converter para ZonedDateTime no UTC
    val zonedDateTime = instant.atZone(ZoneOffset.UTC)

    // Obter o mês e exibir conforme o Locale do sistema
    return zonedDateTime.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
}

fun getHour(dateString: String): Int {
    // Usar Instant.parse para converter diretamente
    val instant = Instant.parse(dateString)

    // Converter para ZonedDateTime no UTC
    val zonedDateTime = instant.atZone(ZoneOffset.UTC)

    // Obter a hora
    return zonedDateTime.hour
}

fun formatDuration(duration: br.sapiens.bellus_app.presentation.ui.model.Duration): String {
    val totalMinutes = when (duration.type) {
        "Hour" -> duration.value * 60
        "Minute" -> duration.value
        else -> 0f
    }.toLong()

    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60

    return when {
        hours > 0 && minutes > 0 -> "${hours}h ${minutes}m"
        hours > 0 -> "${hours}h"
        minutes > 0 -> "${minutes}m"
        else -> "0m"
    }
}