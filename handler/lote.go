package handler

import (
	"context"
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/jackc/pgx/v5"
)

func PostLote(ctx *gin.Context) {
	request := CreateLoteRequest{}

	ctx.BindJSON(&request)

	if err := request.Validate(); err != nil {
		logger.Errorf("validation error: %v", err.Error())
		sendError(ctx, http.StatusBadRequest, err.Error())
		return
	}

	query := `INSERT INTO lote(id_sa, amostra, quantidade) VALUES(@id_sa, @amostra, @quantidade)`
	args := pgx.NamedArgs{
		"id_sa":      request.IdSA,
		"amostra":    request.Amostra,
		"quantidade": request.Quantidade,
	}

	_, err := db.Exec(context.Background(), query, args)

	if err != nil {
		logger.Errorf("error inserting data: %v", err)
		sendError(ctx, http.StatusInternalServerError, err.Error())
		return
	}

	sendSuccess(ctx, "create-lote", "lote adicionado com sucesso!")
}
