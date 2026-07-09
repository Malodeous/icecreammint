package xyz.malefic.icecreammint

import androidx.compose.ui.graphics.vector.ImageVector
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import xyz.malefic.icecreammint.theme.icons.TablerHome
import xyz.malefic.icecreammint.theme.icons.TablerList
import xyz.malefic.icecreammint.theme.icons.TablerPencil
import xyz.malefic.icecreammint.theme.icons.TablerSettings

// Navigation

interface RootComponent {
    val stack: Value<ChildStack<*, Screen>>

    val topLevelScreens: List<Screen>

    fun navigateTo(screen: Screen)

    @Serializable
    enum class Screen(
        val title: String,
        val icon: ImageVector,
    ) {
        Home("Home", TablerHome),
        Demo("Demo", TablerPencil),
        Settings("Settings", TablerSettings),
        ToDo("To-Do", TablerList),
    }
}

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent,
    ComponentContext by componentContext {
    private val navigation = StackNavigation<RootComponent.Screen>()

    override val stack: Value<ChildStack<*, RootComponent.Screen>> =
        childStack(
            source = navigation,
            serializer = RootComponent.Screen.serializer(),
            initialConfiguration = RootComponent.Screen.Home,
            handleBackButton = true,
            childFactory = ::screenFactory,
        )

    override val topLevelScreens: List<RootComponent.Screen> =
        listOf(
            RootComponent.Screen.Home,
            RootComponent.Screen.Demo,
            RootComponent.Screen.Settings,
            RootComponent.Screen.ToDo,
        )

    private fun screenFactory(
        screen: RootComponent.Screen,
        @Suppress("UNUSED_PARAMETER") componentContext: ComponentContext,
    ): RootComponent.Screen = screen

    override fun navigateTo(screen: RootComponent.Screen) {
        println("navigateTo called: $screen")
        navigation.bringToFront(screen)
    }
}
