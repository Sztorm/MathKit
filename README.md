# MathKit

[![](https://jitpack.io/v/Sztorm/MathKit.svg)](https://jitpack.io/#Sztorm/MathKit)
![tests status badge](misc/testsStatus.svg)
![test coverage badge](misc/testCoverage.svg)

Kotlin library that contains collection of various math related types with focus on lightweightness
and ease of use. Library provides types like ComplexF, Vector2F and various shapes like Circle,
Rectangle, etc., and transformation methods for them.

Vectors and Complex numbers use [geometric algebra](https://en.wikipedia.org/wiki/Geometric_algebra)
which especially can be seen in usage of math operations like multiplication and division.

The library makes extensive use of Kotlin's
[inline classes](https://kotlinlang.org/docs/inline-classes.html) to achieve primitive type
comparable performance; and therefore usage of the library is optimal in Kotlin projects.

Almost every public method in the library is unit tested, with wide usage of parameterized tests
that test edge cases of complex methods and functions. Currently, the number of tests exceeds
12,000.

### [Documentation](https://sztorm.github.io/KotlinLowAllocMath)

## Installation

<details>
<summary>Gradle Kotlin</summary>

Step 1. Add it in your root `build.gradle.kts` file:

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
    implementation("com.github.Sztorm.MathKit:MathKit:2.0.0")
}
```

</details>

<details>
<summary>Gradle Groovy</summary>

Step 1. Add it in your root `build.gradle` file:

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
    implementation 'com.github.Sztorm.MathKit:MathKit:2.0.0'
}
```

</details>

<details>
<summary>Maven</summary>

Step 1. Add it in your root `pom.xml` file:

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
    <artifactId>MathKit</artifactId>
    <version>2.0.0</version>
</dependency>
```

</details>

## Samples

```kotlin
val a = Vector2F(0.5f, 3f)
val b = Vector2F(4f, 2f)
val z: ComplexF = a * b
val (ax, ay) = a

println(a + b)                // Vector2F(x=4.5, y=5.0)
println(ax * ay)              // 1.5
println(a.x * a[1])           // 1.5
println(a dot b)              // 8.0
println(a.magnitude)          // Vector2F(x=0.16439898, y=0.98639387)
println(a.normalized)         // 1.0
println(z.phaseAngle.degrees) // -53.972626
println(z.magnitude)          // 13.601471
```

```kotlin
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
val arrayA = Vector2FArray(3)
val arrayB = Vector2FArray(3) { Vector2F(it.toFloat(), it.toFloat()) }

println(arrayA) // Vector2F(x=0.0, y=0.0), Vector2F(x=0.0, y=0.0), Vector2F(x=0.0, y=0.0)
println(arrayB) // Vector2F(x=0.0, y=0.0), Vector2F(x=1.0, y=1.0), Vector2F(x=2.0, y=2.0)

val arrayC = vector2FArrayOf(Vector2F(0f, 0f), Vector2F(1f, 1f), Vector2F(2f, 2f))
arrayC[0] = Vector2F(4f, 5f)

println(arrayC[0])    // Vector2F(x=4.0, y=5.0)
println(arrayC[2])    // Vector2F(x=2.0, y=2.0)
println(arrayC.sum()) // Vector2F(x=7.0, y=8.0)
```

```kotlin
val squareA = Square(
    center = Vector2F(2f, 3f),
    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
    sideLength = 4f
)
val squareB = squareA
    .movedBy(Vector2F(-2f, 1f))
    .rotatedTo(ComplexF.ONE)

println(squareA.position) // Vector2F(x=2.0, y=3.0)
println(squareB.position) // Vector2F(x=0.0, y=4.0)
println(squareB.pointIterator().asSequence().joinToString(", "))
// Vector2F(x=2.0, y=6.0), Vector2F(x=-2.0, y=6.0), Vector2F(x=-2.0, y=2.0), Vector2F(x=2.0, y=2.0)
```

## License

*MathKit* is licensed under the MIT license.

[More about license](LICENSE)