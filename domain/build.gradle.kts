plugins {
  alias(libs.plugins.jetbrains.kotlin.jvm)
}

kotlin {
  jvmToolchain(17)
}

dependencies {
  testImplementation(libs.junit)
  testImplementation(libs.mockk)
  testImplementation(libs.turbine)
  testImplementation(libs.kotlinx.coroutines.test)
  
  implementation(libs.androidx.paging.common)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.javax.inject)
}