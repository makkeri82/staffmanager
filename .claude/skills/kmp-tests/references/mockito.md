# Mockito-Kotlin Patterns

---

## Basic mock setup

```kotlin
private val mockRepo: UserRepository = mock()
private val mockLogger: Logger = mock()

@Before
fun setUp() {
    classUnderTest = MyClass(mockRepo, mockLogger)
}
```

---

## Stubbing return values

```kotlin
whenever(mockRepo.getUser("id1")).thenReturn(User("Alice"))
whenever(mockRepo.getUser("bad")).thenThrow(NotFoundException())
whenever(mockRepo.getUsers()).thenReturn(emptyList())
```

For suspend functions:

```kotlin
whenever(mockRepo.fetchUser("id1")).thenReturn(User("Alice"))
// mockito-kotlin handles suspend automatically — no extra setup needed
```

---

## Verifying calls

```kotlin
// Called exactly once with specific arg
verify(mockRepo).saveUser(user)

// Called N times
verify(mockRepo, times(2)).log(any())

// Never called
verify(mockLogger, never()).error(any())

// Called with any argument
verify(mockRepo).getUser(anyString())
```

---

## Argument captors

Use when you need to inspect what was passed to a mock:

```kotlin
@Test
fun `saves user with normalized email`() {
    // Arrange
    val captor = argumentCaptor<User>()

    // Act
    classUnderTest.register("  ALICE@Example.com  ", "pass")

    // Assert
    verify(mockRepo).saveUser(captor.capture())
    assertEquals("alice@example.com", captor.firstValue.email)
}
```

---

## Answer blocks (dynamic responses)

```kotlin
whenever(mockRepo.transform(any())).thenAnswer { invocation ->
    val input = invocation.getArgument<String>(0)
    input.uppercase()
}
```

---

## Spies (partial mocks)

Use sparingly — prefer full mocks. Useful when testing a method that calls other methods on the same class:

```kotlin
val spy = spy(RealClass())
doReturn("fake").whenever(spy).heavyOperation()

val result = spy.methodUnderTest()
```

---

## Gradle dependency

```gradle
testImplementation "org.mockito.kotlin:mockito-kotlin:5.1.0"
testImplementation "org.mockito:mockito-core:5.5.0"
```
