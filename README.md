# low-alloc-math

[![](https://jitpack.io/v/Sztorm/KotlinLowAllocMath.svg)](https://jitpack.io/#Sztorm/KotlinLowAllocMath)

A collection of various math related types that have little to no GC memory allocation
pressure. This library makes extensive use of Kotlin's
[inline classes](https://kotlinlang.org/docs/inline-classes.html) to achieve primitive type
comparable performance; therefore, usage of the library is optimized for the Kotlin language.

[Documentation](https://sztorm.github.io/KotlinLowAllocMath)

## Installation

<details>
<summary>Gradle Kotlin</summary>

Step 1. Add it in your root `build.gradle.kts` at the end of repositories:

```kotlin
allprojects {
    repositories {
        maven("https://jitpack.io")
    }
}
```

Step 2. Add the dependency

```kotlin
dependencies {
    implementation("com.github.Sztorm.KotlinLowAllocMath:low-alloc-math:1.1.0")
}
```

</details>

<details>
<summary>Gradle Groovy</summary>

Step 1. Add it in your root `build.gradle` at the end of repositories:

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency

```groovy
dependencies {
    implementation 'com.github.Sztorm.KotlinLowAllocMath:low-alloc-math:1.1.0'
}
```

</details>

<details>
<summary>Maven</summary>

Step 1. Add it in your root `pom.xml` at the end of repositories:

```maven
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Step 2. Add the dependency

```maven
<dependency>
    <groupId>com.github.Sztorm</groupId>
    <artifactId>KotlinLowAllocMath</artifactId>
    <version>1.1.0</version>
</dependency>
```

</details>

## Samples

```kotlin
import com.sztorm.lowallocmath.*

// No GC allocation when declared locally (inside the body of a method).
// Under the hood, a long value will be created for each Vector2F.
val a = Vector2F(0.5f, 3f)
val b = Vector2F(4f, 2f)
val (ax, ay) = a

println(a + b)        // Vector2F(x=4.5, y=5.0)
println(ax * ay)      // 1.5
println(a.x * a[1])   // 1.5
println(a dot b)      // 8.0
println(a.magnitude)  // Vector2F(x=0.16439898, y=0.98639387)
println(a.normalized) // 1.0
```

```kotlin
import com.sztorm.lowallocmath.*

val a = ComplexF(3f, 2f)
val b = 3f + 2f.i
val c = ComplexF.fromPolar(3.6055512f, 0.5880026f)
val d = ComplexF.fromAngle(AngleF.fromDegrees(30f))

println(c)           // 2.9999998 + 2.0i
println(d)           // 2.9999998 + 2.0i
println(a.magnitude) // 3.6055512
println(a.phase)     // 0.5880026
println(a + b)       // 6.0 + 4.0i
println(a * b)       // 5.0 + 12.0i
```

```kotlin
import com.sztorm.lowallocmath.*

// This is an array of values, on the JVM side it is represented as long array.
val arrayA = Vector2FArray(3)
val arrayB = Vector2FArray(3) { Vector2F(it.toFloat(), it.toFloat()) }

println(arrayA) // Vector2F(x=0.0, y=0.0), Vector2F(x=0.0, y=0.0), Vector2F(x=0.0, y=0.0)
println(arrayB) // Vector2F(x=0.0, y=0.0), Vector2F(x=1.0, y=1.0), Vector2F(x=2.0, y=2.0)

// vector2FArrayOf function is currently unimplemented due to lack of support for vararg parameter
// of inline class types. This is a workaround that may be replaced in the future.
val arrayC = arrayOf(Vector2F(0f, 0f), Vector2F(1f, 1f), Vector2F(2f, 2f)).toVector2FArray()
arrayC[0] = Vector2F(4f, 5f)

println(arrayC[0])    // Vector2F(x=4.0, y=5.0)
println(arrayC[2])    // Vector2F(x=2.0, y=2.0)
println(arrayC.sum()) // Vector2F(x=7.0, y=8.0)
```

## License

*low-alloc-math* is licensed under the MIT license.

[More about license](LICENSE)