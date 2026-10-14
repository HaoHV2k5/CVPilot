from pydantic_settings import BaseSettings

class Settings(BaseSettings):
    APP_NAME: str = "ai-embeddings"
    PORT: int = 8001
    DEBUG: bool = False
    
    # Gemini Embedding
    GEMINI_API_KEY: str = ""
    EMBEDDING_MODEL: str = "gemini-embedding-001"
    EMBEDDING_DIM: int = 768
    
    # Redis Cache
    REDIS_URL: str = "redis://localhost:6379"
    
    # Qdrant
    QDRANT_URL: str = "http://localhost:6333"
    CV_COLLECTION_NAME: str = "cv_embeddings"
    JOB_COLLECTION_NAME: str = "job_embeddings"

    class Config:
        env_file = ".env"
        extra = "ignore"

settings = Settings()
