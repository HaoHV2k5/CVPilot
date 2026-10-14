from pydantic_settings import BaseSettings

class Settings(BaseSettings):
    APP_NAME: str = "ai-agents"
    PORT: int = 8000
    DEBUG: bool = False
    
    # Gemini
    GEMINI_API_KEY: str = ""
    GEMINI_MODEL: str = "gemini-1.5-flash"
    
    # Database (PostgreSQL for SQL tool calling)
    POSTGRES_DB: str = "resultmatch"
    POSTGRES_USER: str = "postgres"
    POSTGRES_PASSWORD: str = "postgres"
    POSTGRES_HOST: str = "localhost"
    POSTGRES_PORT: int = 5432
    
    # Redis
    REDIS_URL: str = "redis://localhost:6379"
    
    # Qdrant
    QDRANT_URL: str = "http://localhost:6333"
    
    # Langfuse Cloud
    LANGFUSE_PUBLIC_KEY: str = ""
    LANGFUSE_SECRET_KEY: str = ""
    LANGFUSE_HOST: str = "https://cloud.langfuse.com"

    class Config:
        env_file = ".env"
        extra = "ignore"

settings = Settings()
