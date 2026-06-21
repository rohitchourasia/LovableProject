# CodeGenerationLLM

### Production-Grade AI Code Generation Platform Built with Spring AI

CodeGenerationLLM is a backend-driven AI code generation platform that enables context-aware software generation through natural language interactions. The platform combines LLM-powered generation, project-aware context retrieval, streaming responses, tool calling, role-based authorization, and scalable artifact persistence to deliver an AI-native development experience.

The project was built to explore and implement production-grade LLM application patterns using Spring AI, including prompt augmentation, contextual retrieval, streaming generation, project memory, and AI-assisted software evolution.

---

# Key Features

## AI Code Generation

* Context-aware code generation
* Multi-file artifact generation
* Project-aware code modifications
* Structured artifact extraction
* Prompt augmentation using project knowledge
* Real-time streaming responses

## AI Infrastructure

* Spring AI integration
* Custom Advisor implementation
* Tool Calling support
* Context retrieval pipeline
* Structured system prompting
* Project-aware prompt augmentation

## Security & Access Control

* JWT Authentication
* Spring Security
* Method-Level Authorization
* Role-Based Access Control (RBAC)
* Project-Level Permissions

Roles:

* OWNER
* EDITOR
* VIEWER

## Persistence & Storage

* PostgreSQL for metadata storage
* MinIO for artifact storage
* Chat session persistence
* Conversation history persistence
* Project metadata management

## Subscription Management

* Stripe Integration
* Monthly Subscription Plans
* Free Tier Support
* Webhook Processing
* Subscription Persistence

---

# System Architecture

```text
Client
   │
   ▼
Spring Boot Backend
   │
   ▼
Authentication & Authorization
(JWT + RBAC)
   │
   ▼
AI Generation Service
   │
   ▼
Prompt Augmentation Layer
(Custom Advisors)
   │
   ▼
Project Context Retrieval
(File Tree + Project Knowledge)
   │
   ▼
Tool Calling Layer
   │
   ▼
OpenRouter
(GPT-OSS-20B)
   │
   ▼
Streaming Response Pipeline
   │
   ▼
Artifact Extraction Layer
   │
   ├────────────► PostgreSQL
   │               Metadata
   │
   └────────────► MinIO
                   Generated Artifacts
```

---

# AI Generation Workflow

```text
User Prompt
      │
      ▼
System Prompt Construction
      │
      ▼
Project Context Retrieval
      │
      ▼
File Tree Injection
      │
      ▼
Tool Calling (When Required)
      │
      ▼
Prompt Augmentation
      │
      ▼
LLM Invocation
      │
      ▼
Streaming Response
      │
      ▼
Artifact Extraction
      │
      ▼
Persistence Layer
```

---

# Project Context Retrieval Pipeline

The platform implements a project-aware retrieval mechanism that augments model context using project-specific information.

Instead of relying on embeddings and vector databases, the system dynamically injects project structure and relevant project knowledge directly into the prompt context.

Current retrieval sources include:

* Project File Tree
* Existing Project Artifacts
* Chat History
* Project Metadata

This enables the model to reason about the current state of a project before generating modifications.

---

# Custom Advisor Architecture

The platform uses Spring AI Advisors to enrich requests before model invocation.

Current advisor capabilities include:

* File Tree Context Injection
* Project-Aware Prompt Augmentation
* Request Context Propagation
* Retrieval Pipeline Integration

Example:

```text
Incoming Prompt
       │
       ▼
FileTreeContextAdvisor
       │
       ▼
Prompt Augmentation
       │
       ▼
LLM
```

---

# Artifact Generation & Persistence

Generated artifacts follow a structured output contract:

```xml
<file path="src/example.ts">
    ...
</file>
```

The platform automatically:

1. Extracts generated artifacts
2. Parses file metadata
3. Stores file contents in MinIO
4. Stores metadata in PostgreSQL

This separation enables scalable storage and efficient metadata querying.

---

# Storage Architecture

## PostgreSQL

Stores:

* Users
* Projects
* Memberships
* Permissions
* Chat Sessions
* Chat Messages
* Subscription Data
* File Metadata

## MinIO

Stores:

* Generated Artifacts
* Project Files
* Template Files
* Project Assets

Storage Model:

```text
PostgreSQL
    │
    ├── User Data
    ├── Project Data
    ├── Permissions
    ├── Chat History
    └── File Metadata

MinIO
    │
    ├── Generated Files
    ├── Templates
    └── Project Artifacts
```

---

# Project Initialization

Projects are initialized using predefined templates stored in MinIO.

Current Starter Template:

```text
React + Vite + TailwindCSS + DaisyUI
```

Initialization Workflow:

```text
Create Project
      │
      ▼
Copy Template Files
      │
      ▼
Persist Metadata
      │
      ▼
Project Ready
```

This significantly reduces generation cost by avoiding recreation of standard project scaffolding.

---

# Security Model

Authentication:

* JWT Access Tokens
* Stateless Security Architecture

Authorization:

* OWNER
* EDITOR
* VIEWER

Enforced through:

```java
@PreAuthorize(...)
```

Project-level permission checks ensure users can only access resources they are authorized to modify.

---

# Chat Persistence

The platform persists conversational context through:

* Chat Sessions
* Chat Messages
* Project-Specific Conversations
* Historical Conversation Retrieval

This provides the foundation for future memory and retrieval enhancements.

---

# Technology Stack

## Backend

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* Spring AI
* Project Reactor

## Database

* PostgreSQL

## Object Storage

* MinIO

## AI

* OpenRouter
* GPT-OSS-20B

## Payments

* Stripe

## Build Tool

* Maven

---

# Local Setup

## Prerequisites

* Java 21+
* PostgreSQL
* MinIO
* Maven

## Clone Repository

```bash
git clone <repository-url>
cd CodeGenerationLLM
```

## Configure Secrets

Create:

```text
src/main/resources/application-dev.yaml
```

Add your local credentials:

```yaml
spring:
  datasource:
    username: YOUR_DB_USERNAME
    password: YOUR_DB_PASSWORD

  ai:
    openai:
      api-key: YOUR_OPENROUTER_API_KEY

stripe:
  secret-key: YOUR_STRIPE_SECRET_KEY

  webhook:
    secret: YOUR_STRIPE_WEBHOOK_SECRET

minio:
  access-key: YOUR_MINIO_ACCESS_KEY
  secret-key: YOUR_MINIO_SECRET_KEY
```

## Run Application

```bash
mvn spring-boot:run
```

---

# Engineering Highlights

* Custom Spring AI Advisor Implementation
* Project-Aware Retrieval Pipeline
* Streaming AI Responses using Reactor
* Tool Calling Integration
* JWT + RBAC Security Model
* PostgreSQL + MinIO Hybrid Storage
* Stripe Subscription Integration
* Multi-File Artifact Extraction
* Template-Based Project Bootstrapping
* Persistent Project Memory

---


---

# License

This project is intended for educational, research, and portfolio purposes.
