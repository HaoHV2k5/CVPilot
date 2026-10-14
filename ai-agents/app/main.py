import logging
from fastapi import FastAPI, Request, status
from fastapi.responses import JSONResponse
from fastapi.exceptions import RequestValidationError
from fastapi.middleware.cors import CORSMiddleware
from app.core.config import settings
from app.schemas.response import ApiResponse

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger("ai-agents")

app = FastAPI(
    title="CVPilot - AI Agents Service",
    description="Market Q&A, Career Roadmap, and LangGraph Multi-agent service",
    version="1.0.0"
)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

class AppException(Exception):
    def __init__(self, code: int = 400, error_code: str = "BAD_REQUEST", message: str = "Bad Request"):
        self.code = code
        self.error_code = error_code
        self.message = message

@app.exception_handler(AppException)
async def app_exception_handler(request: Request, exc: AppException):
    logger.warning(f"AppException: [{exc.error_code}] {exc.message}")
    response = ApiResponse.error_response(
        code=exc.code,
        errorCode=exc.error_code,
        message=exc.message
    )
    return JSONResponse(status_code=exc.code, content=response.model_dump(mode="json"))

@app.exception_handler(RequestValidationError)
async def validation_exception_handler(request: Request, exc: RequestValidationError):
    logger.warning(f"Validation error: {exc.errors()}")
    error_details = "; ".join([f"{err['loc'][-1]}: {err['msg']}" for err in exc.errors()])
    response = ApiResponse.error_response(
        code=status.HTTP_400_BAD_REQUEST,
        errorCode="BAD_REQUEST",
        message=f"Dữ liệu không hợp lệ: {error_details}"
    )
    return JSONResponse(status_code=status.HTTP_400_BAD_REQUEST, content=response.model_dump(mode="json"))

@app.exception_handler(Exception)
async def generic_exception_handler(request: Request, exc: Exception):
    logger.error(f"Unhandled exception: {exc}", exc_info=True)
    response = ApiResponse.error_response(
        code=status.HTTP_500_INTERNAL_SERVER_ERROR,
        errorCode="INTERNAL_SERVER_ERROR",
        message="Đã xảy ra lỗi hệ thống nội bộ"
    )
    return JSONResponse(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, content=response.model_dump(mode="json"))

@app.get("/health", response_model=ApiResponse)
def health_check():
    return ApiResponse.success_response(data={"service": "ai-agents", "status": "UP"})

@app.get("/", response_model=ApiResponse)
def root():
    return ApiResponse.success_response(data={"message": "CVPilot AI Agents service is running"})

if __name__ == "__main__":
    import uvicorn
    uvicorn.run("app.main:app", host="0.0.0.0", port=settings.PORT, reload=True)
