# Coroutines & Flow Testing Patterns

Use these patterns when the class under test contains `suspend` functions, returns `Flow`, or is a ViewModel.

---

## Setup

Always add to the test class:

```kotlin
@get:Rule
val mainDispatcherRule = MainDispatcherRule()
```

And the rule implementation (include once per project):

```kotlin
class MainDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
```

---

## Testing suspend functions

```kotlin
@Test
fun `returns user when id is valid`() = runTest {
    // Arrange
    val userId = "abc123"
    whenever(mockRepo.getUser(userId)).thenReturn(User("abc123", "Alice"))

    // Act
    val result = viewModel.loadUser(userId)

    // Assert
    assertEquals("Alice", result.name)
}
```

---

## Testing StateFlow / LiveData emissions

```kotlin
@Test
fun `emits Loading then Success when fetch succeeds`() = runTest {
    // Arrange
    val emissions = mutableListOf<UiState>()
    val job = launch(UnconfinedTestDispatcher()) {
        viewModel.uiState.collect { emissions.add(it) }
    }
    whenever(mockRepo.getData()).thenReturn(listOf("a", "b"))

    // Act
    viewModel.fetchData()
    advanceUntilIdle()

    // Assert
    assertEquals(UiState.Loading, emissions[0])
    assertEquals(UiState.Success(listOf("a", "b")), emissions[1])

    job.cancel()
}
```

---

## Testing a cold Flow

```kotlin
@Test
fun `flow emits all items from repository`() = runTest {
    // Arrange
    val fakeFlow = flowOf(1, 2, 3)
    whenever(mockRepo.countFlow()).thenReturn(fakeFlow)

    // Act
    val result = classUnderTest.countFlow().toList()

    // Assert
    assertEquals(listOf(1, 2, 3), result)
}
```

---

## ViewModel with LiveData

Add `InstantTaskExecutorRule` alongside `MainDispatcherRule`:

```kotlin
@get:Rule
val instantExecutorRule = InstantTaskExecutorRule()

@Test
fun `liveData posts user name after load`() = runTest {
    // Arrange
    whenever(mockRepo.getUser()).thenReturn(User("Alice"))

    // Act
    viewModel.load()
    advanceUntilIdle()

    // Assert
    assertEquals("Alice", viewModel.userName.value)
}
```

---

## Gradle dependencies for coroutine tests

```gradle
testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3"
testImplementation "androidx.arch.core:core-testing:2.2.0"
```
