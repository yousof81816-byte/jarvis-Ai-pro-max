package com.example.data.model

enum class SubsystemType(
    val title: String,
    val shortCode: String,
    val description: String,
    val defaultLatencyMs: Long
) {
    PLANNER(
        "Planner",
        "PLN-01",
        "Task breakdown, Goal decomposition, DAG dependency generation, Milestone tracking",
        35L
    ),
    EXECUTOR(
        "Executor",
        "EXE-02",
        "Sandboxed Tool Runner, Code Interpreter, Bash Terminal, REST API Dispatcher",
        120L
    ),
    MEMORY(
        "Memory",
        "MEM-03",
        "Episodic Context, Vector Embedding Store, Entity Knowledge Graph, Associative Recall",
        18L
    ),
    BROWSER_AGENT(
        "Browser Agent",
        "BRW-04",
        "Headless Chromium Automation, DOM Scraping, Vision OCR, Form Fill & Research Crawler",
        240L
    ),
    AI_ROUTER(
        "AI Router",
        "RTR-05",
        "Multi-model Dispatcher (Gemini 3.5 Flash, Gemini 3.1 Pro, Claude, SLM), Latency/Cost Optimization",
        22L
    ),
    SECURITY(
        "Security",
        "SEC-06",
        "Prompt Injection Firewall, PII Masker, Sandbox Permission Policies, Tamper-Proof Audit",
        14L
    )
}
