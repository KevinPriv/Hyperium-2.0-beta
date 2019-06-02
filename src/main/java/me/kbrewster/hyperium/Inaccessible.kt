package me.kbrewster.hyperium

/**
 * Only used during the premain phase, therefore can be omitted after
 * all the classes have been transformed onto a different [ClassLoader]
 */
@Retention(AnnotationRetention.SOURCE)
annotation class Inaccessible