package com.example.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.data.model.SubsystemType
import com.example.ui.components.ConnectionProblemDialog
import com.example.ui.screens.AIRouterScreen
import com.example.ui.screens.AgentOrchestratorScreen
import com.example.ui.screens.BrowserAgentScreen
import com.example.ui.screens.ClientPlatformsScreen
import com.example.ui.screens.CreativeStudioScreen
import com.example.ui.screens.EnterpriseHubScreen
import com.example.ui.screens.ErrorManagerScreen
import com.example.ui.screens.ExecutorScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MemoryScreen
import com.example.ui.screens.MonorepoScreen
import com.example.ui.screens.PlannerScreen
import com.example.ui.screens.RAGPipelineScreen
import com.example.ui.screens.SDLCWorkflowScreen
import com.example.ui.screens.SecurityScreen
import com.example.ui.screens.SelfImprovementScreen
import com.example.ui.screens.VoiceAgentScreen
import com.example.ui.viewmodel.JarvisViewModel

object JarvisDestinations {
    const val HOME = "home"
    const val CLIENTS = "clients"
    const val PLANNER = "planner"
    const val EXECUTOR = "executor"
    const val MEMORY = "memory"
    const val BROWSER_AGENT = "browser_agent"
    const val AI_ROUTER = "ai_router"
    const val SECURITY = "security"
    const val SDLC = "sdlc"
    const val ENTERPRISE = "enterprise"
    const val RAG = "rag"
    const val CREATIVE = "creative"
    const val ORCHESTRATOR = "orchestrator"
    const val SELF_LEARNING = "self_learning"
    const val MONOREPO = "monorepo"
    const val VOICE_AGENT = "voice_agent"
    const val ERROR_MANAGER = "error_manager"
}

@Composable
fun JarvisNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: JarvisViewModel = viewModel()
) {
    val isConnectionProblemVisible by viewModel.connectionProblemDialogVisible.collectAsState()
    val connectionProblemMsg by viewModel.connectionProblemMessage.collectAsState()
    val isRetrying by viewModel.isAutoRetrying.collectAsState()
    val retryCountdown by viewModel.retryCountdown.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = JarvisDestinations.HOME,
            modifier = modifier,
            enterTransition = { fadeIn(animationSpec = tween(220)) },
            exitTransition = { fadeOut(animationSpec = tween(220)) }
        ) {
            composable(JarvisDestinations.HOME) {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToClients = {
                        navController.navigate(JarvisDestinations.CLIENTS)
                    },
                    onNavigateToSubsystem = { subsystem ->
                        val route = when (subsystem) {
                            SubsystemType.PLANNER -> JarvisDestinations.PLANNER
                            SubsystemType.EXECUTOR -> JarvisDestinations.EXECUTOR
                            SubsystemType.MEMORY -> JarvisDestinations.MEMORY
                            SubsystemType.BROWSER_AGENT -> JarvisDestinations.BROWSER_AGENT
                            SubsystemType.AI_ROUTER -> JarvisDestinations.AI_ROUTER
                            SubsystemType.SECURITY -> JarvisDestinations.SECURITY
                        }
                        navController.navigate(route)
                    },
                    onNavigateToSDLC = { navController.navigate(JarvisDestinations.SDLC) },
                    onNavigateToEnterprise = { navController.navigate(JarvisDestinations.ENTERPRISE) },
                    onNavigateToRAG = { navController.navigate(JarvisDestinations.RAG) },
                    onNavigateToCreative = { navController.navigate(JarvisDestinations.CREATIVE) },
                    onNavigateToOrchestrator = { navController.navigate(JarvisDestinations.ORCHESTRATOR) },
                    onNavigateToSelfLearning = { navController.navigate(JarvisDestinations.SELF_LEARNING) },
                    onNavigateToMonorepo = { navController.navigate(JarvisDestinations.MONOREPO) },
                    onNavigateToVoiceAgent = { navController.navigate(JarvisDestinations.VOICE_AGENT) },
                    onNavigateToErrorManager = { navController.navigate(JarvisDestinations.ERROR_MANAGER) }
                )
            }

            composable(JarvisDestinations.CLIENTS) {
                ClientPlatformsScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(JarvisDestinations.PLANNER) {
                PlannerScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(JarvisDestinations.EXECUTOR) {
                ExecutorScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(JarvisDestinations.MEMORY) {
                MemoryScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(JarvisDestinations.BROWSER_AGENT) {
                BrowserAgentScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(JarvisDestinations.AI_ROUTER) {
                AIRouterScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(JarvisDestinations.SECURITY) {
                SecurityScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(JarvisDestinations.SDLC) {
                SDLCWorkflowScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(JarvisDestinations.ENTERPRISE) {
                EnterpriseHubScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(JarvisDestinations.RAG) {
                RAGPipelineScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(JarvisDestinations.CREATIVE) {
                CreativeStudioScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(JarvisDestinations.ORCHESTRATOR) {
                AgentOrchestratorScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(JarvisDestinations.SELF_LEARNING) {
                SelfImprovementScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(JarvisDestinations.MONOREPO) {
                MonorepoScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(JarvisDestinations.VOICE_AGENT) {
                VoiceAgentScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(JarvisDestinations.ERROR_MANAGER) {
                ErrorManagerScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }

        // Global Resilient Connection Problem Dialog HUD
        ConnectionProblemDialog(
            isVisible = isConnectionProblemVisible,
            errorMessage = connectionProblemMsg,
            isRetrying = isRetrying,
            retryCountdownSeconds = retryCountdown,
            onTryAgain = { viewModel.retryConnection() },
            onOfflineMode = { viewModel.enableOfflineModeFromDialog() },
            onDismiss = { viewModel.dismissConnectionProblemDialog() },
            onOpenErrorManager = {
                viewModel.dismissConnectionProblemDialog()
                navController.navigate(JarvisDestinations.ERROR_MANAGER)
            }
        )
    }
}

