package com.picPaySimplificado

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class PicPaySimplificadoApplication

fun main(args: Array<String>) {
	runApplication<PicPaySimplificadoApplication>(*args)
}
