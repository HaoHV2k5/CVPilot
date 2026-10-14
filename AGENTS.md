# Quy ước phát triển dự án ResultMatch Resume AI

Tất cả các hành động liên quan đến Git (tạo nhánh, commit, merge, push) PHẢI TUÂN THỦ NGHIÊM NGẶT các quy ước sau theo [README.md](README.md):

## 1. Quy trình nhánh (Git Flow)

- **`main`**: Bản ổn định, mỗi lần gộp tương ứng một phiên bản (gắn tag). **Tuyệt đối không commit trực tiếp.**
- **`develop`**: Nhánh tích hợp, nơi gom các tính năng đã xong. **Tuyệt đối không commit trực tiếp.**
- **`feature/<tên-ngắn>`**: Phát triển tính năng mới, luôn tách nhánh từ `develop`, sau khi hoàn thiện sẽ gộp (merge/PR) lại vào `develop`.
- **`release/<phiên-bản>`**: Chuẩn bị phát hành, tách từ `develop`, kiểm thử và gộp vào cả `main` và `develop`.
- **`hotfix/<tên-ngắn>`**: Sửa lỗi khẩn cấp trực tiếp từ `main`, sau đó gộp vào cả `main` và `develop`.

*Ví dụ:* `feature/cv-analysis`, `feature/job-ingestion`, `release/1.0.0`, `hotfix/jwt-expiry`.

## 2. Quy ước commit (Conventional Commits)

Định dạng bắt buộc:
```
<type>(<scope>): <mô tả ngắn>
```

### Types:
- `feat`: Thêm tính năng mới
- `fix`: Sửa lỗi
- `docs`: Cập nhật tài liệu (README, phân tích hệ thống, diagrams,...)
- `refactor`: Tái cấu trúc mã nguồn (không đổi hành vi)
- `test`: Thêm hoặc cập nhật test
- `chore`: Cấu hình, dependencies, script build
- `ci`: Cấu hình CI/CD pipeline (GitHub Actions, Docker,...)

### Scopes:
Tên service hoặc module tương ứng: `worker`, `ai-agents`, `ai-embeddings`, `gateway`, `bot-agent`, `frontend`, `cv`, `auth`, `job`, `infra`, `readme`,...

*Ví dụ:*
```
feat(cv): add async CV analysis via Kafka
fix(gateway): reject expired JWT with 401
docs(readme): update architecture diagram
chore(docker): configure compose services
```

## 3. Nguyên tắc bổ sung
- Không commit trực tiếp lên `main` và `develop`.
- Mỗi commit nên nhỏ gọn, tập trung xử lý một nhiệm vụ cụ thể (atomic commits).
- Đảm bảo kiểm thử và build thành công trước khi gộp nhánh.
