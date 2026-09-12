import com.mackbex.rickdex.domain.character.repository.FakeBookmarkRepository
import com.mackbex.rickdex.domain.character.usecase.ToggleBookmarkUseCase
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test


class ToggleBookmarkUseCaseTest {

  @Test
  fun `북마크가 없으면 추가된다`() = runTest {
    val repo = FakeBookmarkRepository()
    val useCase = ToggleBookmarkUseCase(repo)

    useCase(5)

    assertTrue(repo.observeBookmarkIds().first().contains(5))
  }

  @Test
  fun `북마크가 있으면 제거된다`() = runTest {
    val repo = FakeBookmarkRepository()
    val useCase = ToggleBookmarkUseCase(repo)

    useCase(5)
    useCase(5)

    assertTrue(repo.observeBookmarkIds().first().isEmpty())
  }
}