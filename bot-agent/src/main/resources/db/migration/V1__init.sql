-- Extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 1. app_user
CREATE TABLE IF NOT EXISTS app_user (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    role VARCHAR(50) NOT NULL DEFAULT 'USER',
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- 2. cv
CREATE TABLE IF NOT EXISTS cv (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
    file_key VARCHAR(500) NOT NULL,
    original_file_name VARCHAR(255) NOT NULL,
    file_size BIGINT NOT NULL,
    content_type VARCHAR(100) NOT NULL,
    is_primary BOOLEAN NOT NULL DEFAULT FALSE,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    retry_count INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- 3. cv_analysis
CREATE TABLE IF NOT EXISTS cv_analysis (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    cv_id UUID NOT NULL UNIQUE REFERENCES cv(id) ON DELETE CASCADE,
    strengths TEXT,
    weaknesses TEXT,
    improvements TEXT,
    extracted_data JSONB,
    model_name VARCHAR(100),
    prompt_version VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- 4. skill
CREATE TABLE IF NOT EXISTS skill (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL UNIQUE,
    normalized_name VARCHAR(100) NOT NULL UNIQUE,
    category VARCHAR(100),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- 5. skill_alias
CREATE TABLE IF NOT EXISTS skill_alias (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    skill_id UUID NOT NULL REFERENCES skill(id) ON DELETE CASCADE,
    alias VARCHAR(100) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- 6. cv_skill
CREATE TABLE IF NOT EXISTS cv_skill (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    cv_id UUID NOT NULL REFERENCES cv(id) ON DELETE CASCADE,
    skill_id UUID NOT NULL REFERENCES skill(id) ON DELETE CASCADE,
    years_experience NUMERIC(4, 1),
    confidence_score NUMERIC(5, 2),
    UNIQUE (cv_id, skill_id)
);

-- 7. job_posting
CREATE TABLE IF NOT EXISTS job_posting (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    source VARCHAR(50) NOT NULL,
    source_job_id VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    company VARCHAR(255),
    location VARCHAR(255),
    is_remote BOOLEAN NOT NULL DEFAULT FALSE,
    description TEXT,
    salary_min NUMERIC(15, 2),
    salary_max NUMERIC(15, 2),
    salary_currency VARCHAR(10),
    job_url TEXT NOT NULL,
    content_hash VARCHAR(64) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    posted_at TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    UNIQUE (source, source_job_id)
);

-- 8. job_posting_skill
CREATE TABLE IF NOT EXISTS job_posting_skill (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    job_posting_id UUID NOT NULL REFERENCES job_posting(id) ON DELETE CASCADE,
    skill_id UUID NOT NULL REFERENCES skill(id) ON DELETE CASCADE,
    is_required BOOLEAN NOT NULL DEFAULT TRUE,
    UNIQUE (job_posting_id, skill_id)
);

-- 9. career_roadmap
CREATE TABLE IF NOT EXISTS career_roadmap (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
    cv_id UUID REFERENCES cv(id) ON DELETE SET NULL,
    target_role VARCHAR(255) NOT NULL,
    skill_gap JSONB,
    roadmap_data JSONB NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- 10. saved_job
CREATE TABLE IF NOT EXISTS saved_job (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
    job_posting_id UUID NOT NULL REFERENCES job_posting(id) ON DELETE CASCADE,
    saved_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    UNIQUE (user_id, job_posting_id)
);

-- 11. conversation & message
CREATE TABLE IF NOT EXISTS conversation (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
    title VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS message (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    conversation_id UUID NOT NULL REFERENCES conversation(id) ON DELETE CASCADE,
    sender_type VARCHAR(20) NOT NULL,
    content TEXT NOT NULL,
    metadata JSONB,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- 12. notification
CREATE TABLE IF NOT EXISTS notification (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    type VARCHAR(50) NOT NULL,
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- 13. ingestion_run
CREATE TABLE IF NOT EXISTS ingestion_run (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    source VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    total_fetched INT NOT NULL DEFAULT 0,
    new_jobs_count INT NOT NULL DEFAULT 0,
    error_log TEXT,
    started_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    completed_at TIMESTAMP WITH TIME ZONE
);

-- Indexes
CREATE INDEX IF NOT EXISTS idx_cv_user_id ON cv(user_id);
CREATE INDEX IF NOT EXISTS idx_job_posting_active ON job_posting(is_active);
CREATE INDEX IF NOT EXISTS idx_job_posting_content_hash ON job_posting(content_hash);
CREATE INDEX IF NOT EXISTS idx_message_conversation ON message(conversation_id);
CREATE INDEX IF NOT EXISTS idx_notification_user ON notification(user_id, is_read);
