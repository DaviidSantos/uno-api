package config

import (
	"fmt"

	"github.com/jackc/pgx/v5"
)

var (
	logger *Logger
	db     *pgx.Conn
)

func Init() error {
	var err error
	
	// Initialize postgres
	db, err = initializePostgres()

	if err != nil {
		return fmt.Errorf("error initializing postgres: %v", err)
	}

	return nil
}

func GetLogger(p string) *Logger {
	logger = NewLogger(p)
	return logger
}

func GetPostgres() *pgx.Conn {
	return db
}
