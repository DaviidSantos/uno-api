package config

import (
	"context"
	"os"

	"github.com/jackc/pgx/v5"
)

func initializePostgres() (*pgx.Conn, error) {
	logger := GetLogger("postgres")

	db, err := pgx.Connect(context.Background(), os.Getenv("DATABASE_URL"))
	if err != nil {
		logger.Errorf("postgres initialization error: %v", err)
		return nil, err
	}

	return db, nil
}