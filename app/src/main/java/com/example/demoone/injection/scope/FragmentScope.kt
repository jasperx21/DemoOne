package com.example.demoone.injection.scope

import javax.inject.Scope
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.ANNOTATION_CLASS
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.PROPERTY_GETTER
import kotlin.annotation.AnnotationTarget.PROPERTY_SETTER

@Scope
@Retention(RUNTIME)
@Target(
    ANNOTATION_CLASS, CLASS, FUNCTION,
    PROPERTY_GETTER, PROPERTY_SETTER
)
annotation class FragmentScope