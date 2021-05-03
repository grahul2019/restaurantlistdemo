package org.codejudge.application.utils

interface Mapper<out Output, in Input>{
    fun mapEntity(model:Input): Output
}