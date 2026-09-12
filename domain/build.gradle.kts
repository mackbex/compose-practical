plugins {
  alias(libs.plugins.jetbrains.kotlin.jvm)
}

kotlin {
  jvmToolchain(17)
}

dependencies {
  implementation(libs.androidx.paging.common)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.javax.inject)
}