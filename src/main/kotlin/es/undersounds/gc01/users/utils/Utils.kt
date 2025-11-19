package es.undersounds.gc01.users.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.jvm.java

inline fun <reified T> logger(): Logger = LoggerFactory.getLogger(T::class.java)