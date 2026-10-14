# CVPilot - API Contracts & Communication Specifications

## 1. Gateway Routing Table (Port 8080 / Nginx Port 80)

| Path Prefix | Target Service | Purpose | Protocol |
|---|---|---|---|
| `/ws/**` | `bot-agent:8081` | Realtime chat streaming & upload progress notifications | WebSocket |
| `/api/v1/auth/**` | `bot-agent:8081` | Authentication (register, login, profile) | REST |
| `/api/v1/cv/**` | `bot-agent:8081` | CV upload, analysis querying, management | REST |
| `/api/v1/roadmap/**` | `bot-agent:8081` | Career roadmap generation & retrieval | REST |
| `/api/v1/jobs/**` | `bot-agent:8081` | Job matching, search, and bookmarks | REST |
| `/api/v1/ai/chat/**` | `ai-agents:8000` | Market Q&A LangGraph workflow | REST / SSE |
| `/api/v1/embeddings/**` | `ai-embeddings:8001` | Direct embedding creation and Qdrant search | REST |

---

## 2. Kafka Topics & Event Schemas

### `cv.uploaded`
Published by: `bot-agent`  
Consumed by: `worker`

```json
{
  "cvId": "123e4567-e89b-12d3-a456-426614174000",
  "userId": "123e4567-e89b-12d3-a456-426614174001",
  "fileKey": "resumes/user-uuid/filename.pdf",
  "originalFileName": "my_resume.pdf",
  "contentType": "application/pdf",
  "fileSize": 1048576,
  "uploadedAt": "2026-10-14T02:00:00Z"
}
```

### `cv.analyzed`
Published by: `worker`  
Consumed by: `bot-agent`

```json
{
  "cvId": "123e4567-e89b-12d3-a456-426614174000",
  "userId": "123e4567-e89b-12d3-a456-426614174001",
  "status": "DONE",
  "errorMessage": null,
  "completedAt": "2026-10-14T02:01:15Z"
}
```
