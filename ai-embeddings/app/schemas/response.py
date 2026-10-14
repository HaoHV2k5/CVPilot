from typing import Generic, TypeVar, Optional, Any
from datetime import datetime, timezone
from pydantic import BaseModel, Field

T = TypeVar("T")

class ApiResponse(BaseModel, Generic[T]):
    success: bool = True
    code: int = 200
    message: str = "Thao tác thành công"
    errorCode: Optional[str] = None
    data: Optional[T] = None
    timestamp: datetime = Field(default_factory=lambda: datetime.now(timezone.utc))

    @classmethod
    def success_response(cls, data: T = None, message: str = "Thao tác thành công") -> "ApiResponse[T]":
        return cls(
            success=True,
            code=200,
            message=message,
            data=data,
            timestamp=datetime.now(timezone.utc)
        )

    @classmethod
    def error_response(cls, code: int = 400, errorCode: str = "BAD_REQUEST", message: str = "Yêu cầu không hợp lệ") -> "ApiResponse[Any]":
        return cls(
            success=False,
            code=code,
            errorCode=errorCode,
            message=message,
            data=None,
            timestamp=datetime.now(timezone.utc)
        )
