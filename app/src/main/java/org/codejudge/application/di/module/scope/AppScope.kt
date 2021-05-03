package org.codejudge.application.di.module.scope

import javax.inject.Scope

/**
 * Scope for the entire app runtime.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FUNCTION)
annotation class AppScope