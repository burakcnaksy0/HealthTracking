IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='users' AND xtype='U')
CREATE TABLE users (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    full_name NVARCHAR(200),
    username NVARCHAR(50) NOT NULL UNIQUE,
    email NVARCHAR(100) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    age INT,
    weight DECIMAL(5,2),
    height DECIMAL(5,2),
    goal NVARCHAR(50),
    activity_level NVARCHAR(50),
    role NVARCHAR(20) NOT NULL,
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME2
);

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='exercise_types' AND xtype='U')
CREATE TABLE exercise_types (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100),
    met DECIMAL(4,2)
);

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='meal_log' AND xtype='U')
CREATE TABLE meal_log (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT NOT NULL,
    meal_time NVARCHAR(50),
    food_name NVARCHAR(255),
    calories DECIMAL(6,2),
    amount DECIMAL(5,2),
    unit NVARCHAR(50),
    protein DECIMAL(5,2),
    carbs DECIMAL(5,2),
    fat DECIMAL(5,2),
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME2,
    CONSTRAINT fk_meal_log_user FOREIGN KEY (user_id) REFERENCES users(id)
);
