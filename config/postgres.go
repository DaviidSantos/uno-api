package config

import (
	"context"
	"os"

	"github.com/jackc/pgx/v5"
	"github.com/joho/godotenv"
)

func initializePostgres() (*pgx.Conn, error) {
	logger := GetLogger("postgres")

	err := godotenv.Load()
	if err != nil {
		logger.Errorf("error loading .env file: %v", err)
		return nil, err
	}

	db, err := pgx.Connect(context.Background(), os.Getenv("DATABASE_URL"))
	if err != nil {
		logger.Errorf("postgres initialization error: %v", err)
		return nil, err
	}

	return db, nil
}
