package handler

import (
	"github.com/DaviidSantos/uno-api/config"
	"github.com/jackc/pgx/v5"
)

var (
	logger *config.Logger
	db     *pgx.Conn
)

func Init() {
	logger = config.GetLogger("handler")
	db = config.GetPostgres()
}
