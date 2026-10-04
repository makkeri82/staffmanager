---
name: kmp-tests
description: Generate Kotlin Multiplatform (KMP) unit and UI tests using kotlin.test, JUnit5 (fallback JUnit4 for Android), fakes for repositories, and compose.uiTest for Compose Multiplatform shared UI. Covers plain classes, repositories (using fakes), ViewModels (StateFlow, UiState, coroutine scope), and Compose UI screens (node finders, interactions, async state). Use this skill whenever the user wants to write, create, or generate any kind of test for KMP or Kotlin/Android/iOS code — even if they just say "write tests for X", "add tests to my class", "test this ViewModel", "test this screen", or "cover this with unit tests". Triggers on: class names, composable names, pasted Kotlin source code, requests to improve test coverage, or any mention of JUnit, Mockito, Kotest, ViewModel, StateFlow, compose.uiTest, UI test, or coroutine tests in a Kotlin or KMP context.
---

# KMP Tests Skill

Generates idiomatic Kotlin tests following the **Arrange-Act-Assert (AAA)** pattern for Kotlin Multiplatform projects with **shared UI** (Compose Multiplatform) targeting Android and iOS.

---

## Source Set Decision — Where Does the Test Live?

This is the first decision to make for every test. Get it wrong and things won't compile.

| What you're testing | Source set | Notes |
|---|---|---|
| Shared business logic, use cases, mappers | `commonTest` | Must use `kotlin.test` only; no Mockito |
| Shared ViewModel (`commonMain`) | `commonTest` | Fakes only; no Mockito; set `Dispatchers.Main` via `kotlinx-coroutines-test` |
| Shared Compose UI (`commonMain`) | `commonTest` | Use `compose.uiTest` with `runComposeUiTest` |
| Android-specific code | `androidTest` | JUnit 4/5 + Mockito OK here |
| iOS-specific code | `iosTest` | `kotlin.test` only; no JVM libraries |

**Rule of thumb:** if the code lives in `commonMain`, the test lives in `commonTest` and must only use multiplatform-compatible libraries.

---

## Test Libraries by Source Set

### `commonTest` — multiplatform-compatible only

| Purpose | Library |
|---|---|
| Assertions | `kotlin.test` (`assertEquals`, `assertNotNull`, `assertFailsWith`) — **default for all shared tests** |
| Test annotations | `kotlin.test` (`@Test`, `@BeforeTest`, `@AfterTest`) |
| Coroutines | `kotlinx-coroutines-test` (`runTest`, `UnconfinedTestDispatcher`) |
| Mocking | ❌ **No Mockito** — use fakes instead (see Fakes section) |
| UI | `compose.uiTest` (`runComposeUiTest`) |

### `androidTest` — JVM only, Android-specific code

| Purpose | Library |
|---|---|
| Test runner | JUnit 5 (preferred) or JUnit 4 fallback |
| Mocking | Mockito-Kotlin (`mock()`, `whenever()`, `verify()`) |
| Assertions | `kotlin.test` or JUnit assertions |
| Coroutines | `kotlinx-coroutines-test` |
| ViewModel | `androidx.arch.core:core-testing` (`InstantTaskExecutorRule`) |

### `iosTest` — Kotlin/Native, iOS-specific code

| Purpose | Library |
|---|---|
| Annotations | `kotlin.test` (`@Test`, `@BeforeTest`, `@AfterTest`) |
| Assertions | `kotlin.test` |
| Coroutines | `kotlinx-coroutines-test` (limited — see iOS section) |
| Mocking | ❌ No Mockito — fakes only |

Always mention Gradle dependencies at the end of your response (only the ones actually used).

---

## Gradle Setup

```kotlin
// build.gradle.kts (shared module)
kotlin {
    sourceSets {
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
            implementation(compose.uiTest)               // for Compose UI tests
        }
        androidTest.dependencies {
            implementation("org.junit.jupiter:junit-jupiter:5.10.0")
            implementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
            implementation("androidx.arch.core:core-testing:2.2.0")
            // JUnit 5 on Android needs the plugin:
            // plugins { id("de.mannodermaus.android-junit5") version "1.10.0.0" }
        }
        iosTest.dependencies {
            // kotlin("test") already included via commonTest
        }
    }
}

// Android compose UI test manifest
android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}
// In androidTest source set:
// implementation(compose.uiTestManifest)
```

---

## AAA Style Rules

Every test function **must** follow this structure:

```kotlin
@Test
fun `descriptive test name in backticks`() {
    // Arrange
    val input = ...

    // Act
    val result = classUnderTest.methodName(input)

    // Assert
    assertEquals(expected, result)
}
```

**Naming**: backtick names that read as plain English:
- ✅ `` `returns empty list when input is blank` ``
- ✅ `` `throws IllegalArgumentException when age is negative` ``
- ❌ `testReturnEmptyListWhenInputIsBlank`

**Annotations in `commonTest`**: use `kotlin.test` annotations, not JUnit:
- ✅ `@Test`, `@BeforeTest`, `@AfterTest` (from `kotlin.test`)
- ❌ `@org.junit.jupiter.api.Test`, `@Before` (JVM only)

---

## Fakes vs Mocks

**Mockito does not work in `commonTest` or `iosTest`** — it requires JVM reflection. Always use fakes for shared code.

**Use a fake when:**
- The dependency is in `commonMain` (always)
- The dependency is named `*Repository`, `*DataSource`, `*Store`, `*Cache`, or `*Dao` (even in `androidTest`)

**Use a mock (Mockito) only in `androidTest`** for Android-specific side-effectful collaborators (services, analytics, loggers).

### Fake pattern

```kotlin
// commonTest — works on Android, iOS, Desktop
class FakeUserRepository : UserRepository {
    val users = mutableListOf<User>()
    var shouldThrow = false

    override suspend fun getUser(id: String): User? {
        if (shouldThrow) throw RuntimeException("Network error")
        return users.find { it.id == id }
    }

    override suspend fun saveUser(user: User) {
        users.removeIf { it.id == user.id }
        users.add(user)
    }
}
```

Note: use `RuntimeException` (not `IOException`) in `commonTest` — `IOException` is JVM-only.

---

## What to Cover

For each class, generate tests covering:

1. **Happy path** — normal inputs produce expected outputs
2. **Edge cases** — empty strings, zero, negative numbers, empty collections
3. **Error cases** — exceptions thrown, error states returned
4. **Dependency interactions** — fakes for repositories (assert state), mocks only in `androidTest`
5. **State changes** — test before/after transitions

Aim for **one assertion per test** where practical.

---

## ViewModel Testing (`commonTest`)

ViewModels in `commonMain` are tested in `commonTest`. Use `kotlin.test` annotations and fakes only.

### Required setup

Replace `Dispatchers.Main` with a `TestDispatcher` — required on all platforms including iOS:

```kotlin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UserViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var fakeRepo: FakeUserRepository
    private lateinit var viewModel: UserViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeRepo = FakeUserRepository()
        viewModel = UserViewModel(fakeRepo)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
```

### Asserting StateFlow / UiState

```kotlin
@Test
fun `uiState shows user after successful load`() = runTest {
    // Arrange
    fakeRepo.users.add(User(id = "1", name = "Alice"))

    // Act
    viewModel.loadUser("1")

    // Assert
    val state = viewModel.uiState.value
    assertEquals(false, state.isLoading)
    assertEquals("Alice", state.user?.name)
}

@Test
fun `uiState shows error when repository throws`() = runTest {
    // Arrange
    fakeRepo.shouldThrow = true

    // Act
    viewModel.loadUser("1")

    // Assert
    val state = viewModel.uiState.value
    assertEquals(false, state.isLoading)
    assertNotNull(state.errorMessage)
}
```

### Collecting a sequence of states

```kotlin
@Test
fun `emits loading then content states`() = runTest {
    // Arrange
    fakeRepo.users.add(User(id = "1", name = "Alice"))
    val states = mutableListOf<UserUiState>()

    // Act
    val job = launch(UnconfinedTestDispatcher()) {
        viewModel.uiState.toList(states)
    }
    viewModel.loadUser("1")
    job.cancel()

    // Assert
    assertEquals(true, states[0].isLoading)
    assertEquals(false, states[1].isLoading)
    assertEquals("Alice", states[1].user?.name)
}
```

### What to cover in ViewModel tests

1. **Initial state** — assert defaults before any action
2. **Loading state** — `isLoading = true` emitted before result
3. **Success state** — correct data after repository returns
4. **Error state** — error message set when repository throws
5. **User actions** — each public fun gets at least one test
6. **State reset** — error dismissed, retry clears and reloads

---

## Compose Multiplatform UI Testing (`commonTest`)

UI tests live in `commonTest` and render real composables. They use `compose.uiTest` which works on Android, Desktop (JVM), and iOS (experimental but functional for shared UI).

### iOS caveat

`compose.uiTest` on iOS is experimental. It works for shared `commonMain` composables run via the Kotlin/Native test runner, but has some limitations:
- Slower than Android/JVM runs
- Some interaction APIs may behave differently
- Requires Xcode simulator to run `iosSimulatorArm64Test` or `iosX64Test` Gradle tasks

Always test on both Android and iOS targets to catch platform-specific rendering differences.

### Basic structure

```kotlin
import androidx.compose.ui.test.*
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class UserProfileScreenTest {

    @Test
    fun `shows user name after loading`() = runComposeUiTest {
        // Arrange
        setContent {
            UserProfileScreen(
                uiState = UserUiState(user = User(name = "Alice"), isLoading = false)
            )
        }

        // Assert
        onNodeWithText("Alice").assertIsDisplayed()
    }

    @Test
    fun `shows loading indicator while loading`() = runComposeUiTest {
        setContent {
            UserProfileScreen(uiState = UserUiState(isLoading = true))
        }

        onNodeWithContentDescription("Loading").assertIsDisplayed()
        onNodeWithTag("user_name").assertDoesNotExist()
    }

    @Test
    fun `calls onRetry when retry button clicked`() = runComposeUiTest {
        // Arrange
        var retryCalled = false
        setContent {
            ErrorScreen(onRetry = { retryCalled = true })
        }

        // Act
        onNodeWithText("Retry").performClick()

        // Assert
        assertEquals(true, retryCalled)
    }
}
```

### Node finders

| Finder | When to use |
|---|---|
| `onNodeWithText("…")` | Visible label, button text, heading |
| `onNodeWithContentDescription("…")` | Icons, images — always set on shared composables for accessibility |
| `onNodeWithTag("…")` | Dynamic content, loading states, error messages — add `Modifier.testTag("id")` |
| `onNode(hasText("…") and hasClickAction())` | Combined matcher to disambiguate |

Prefer `testTag` for dynamic content to decouple tests from copy changes.

### Common interactions

```kotlin
performClick()                        // tap
performTextInput("hello")             // type
performTextClearance()                // clear field
performScrollTo()                     // scroll into view
performTouchInput { swipeLeft() }     // gesture
```

### Waiting for async state

```kotlin
waitUntil(timeoutMillis = 2_000) {
    onAllNodesWithText("Alice").fetchSemanticsNodes().isNotEmpty()
}
onNodeWithText("Alice").assertIsDisplayed()
```

### Prefer stateless composables in tests

Pass `uiState` directly rather than a real ViewModel — keeps tests fast and deterministic on all platforms:

```kotlin
// Preferred
setContent {
    UserProfileScreen(
        uiState = UserUiState(user = User(name = "Alice"), isLoading = false),
        onRetry = {}
    )
}

// Only if testing ViewModel integration specifically
setContent {
    val viewModel = UserViewModel(FakeUserRepository().apply {
        users.add(User(id = "1", name = "Alice"))
    })
    UserProfileScreen(viewModel = viewModel)
}
```

### What to cover in UI tests

1. **Content rendering** — correct text/icons for each state (loading, success, empty, error)
2. **User interactions** — clicks, text input, scroll trigger the right callbacks
3. **Conditional visibility** — `assertIsDisplayed` / `assertDoesNotExist` per state
4. **Accessibility** — every interactive element has a content description or test tag
5. **Navigation** — tapping nav items calls the expected lambda

**Don't test in UI tests:** business logic (unit tests), ViewModel internals (ViewModel tests), pixel-perfect layout (use Roborazzi for screenshot tests).

---

## iOS-Specific Testing Notes

For code in `iosMain` (platform-specific implementations like `NSUserDefaults`, `CLLocationManager`):

- Write tests in `iosTest` using `kotlin.test` only
- No `Dispatchers.Main` available by default on iOS in tests — use `runTest` with `UnconfinedTestDispatcher` and avoid `setMain`
- No Mockito — fakes only
- Run via: `./gradlew iosSimulatorArm64Test` or `iosX64Test`

```kotlin
// iosTest
import kotlin.test.Test
import kotlin.test.assertEquals

class IosSettingsRepositoryTest {

    @Test
    fun `saves and retrieves value`() {
        // Arrange
        val repo = IosSettingsRepository()

        // Act
        repo.save("theme", "dark")

        // Assert
        assertEquals("dark", repo.get("theme"))
    }
}
```

---

## Output Format

Structure your response as:

1. **Source set** — state which source set the test belongs in and why
2. **Brief analysis** — what the class does, what you'll test (2–4 sentences)
3. **Assumptions** (only if source wasn't provided)
4. **The test file** — complete, compilable Kotlin with correct package/imports
5. **Gradle dependencies** — only what was used

The test class should be named `<ClassName>Test`, placed in the same package as the class under test, in the correct source set.

---

## Full Example (`commonTest`, shared ViewModel)

```kotlin
// commonTest/kotlin/com/example/app/UserViewModelTest.kt

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class UserViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var fakeRepo: FakeUserRepository
    private lateinit var viewModel: UserViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeRepo = FakeUserRepository()
        viewModel = UserViewModel(fakeRepo)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state has no user and is not loading`() {
        // Assert
        val state = viewModel.uiState.value
        assertEquals(false, state.isLoading)
        assertNull(state.user)
        assertNull(state.errorMessage)
    }

    @Test
    fun `uiState shows user after successful load`() = runTest {
        // Arrange
        fakeRepo.users.add(User(id = "1", name = "Alice"))

        // Act
        viewModel.loadUser("1")

        // Assert
        val state = viewModel.uiState.value
        assertEquals(false, state.isLoading)
        assertEquals("Alice", state.user?.name)
        assertNull(state.errorMessage)
    }

    @Test
    fun `uiState shows error when repository throws`() = runTest {
        // Arrange
        fakeRepo.shouldThrow = true

        // Act
        viewModel.loadUser("1")

        // Assert
        val state = viewModel.uiState.value
        assertEquals(false, state.isLoading)
        assertNotNull(state.errorMessage)
        assertNull(state.user)
    }
}
```

**Gradle:**
```kotlin
commonTest.dependencies {
    implementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
}
```

---

## Reference Files

- `references/coroutines.md` — patterns for testing suspend functions, flows, and ViewModels with coroutines
- `references/mockito.md` — Mockito-Kotlin patterns for `androidTest` only: argument captors, answer blocks, spy usage

Read the relevant reference file if the class under test uses coroutines/flows or complex mocking patterns in Android-specific tests.
