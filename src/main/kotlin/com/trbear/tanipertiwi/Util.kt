package com.trbear.tanipertiwi

import org.slf4j.LoggerFactory

val log = LoggerFactory.getLogger("INFO")

fun String.info()= log.info(this)
fun String.error()= log.error(this)
fun String.warn()= log.warn(this)
fun String.debug()= log.debug(this)
