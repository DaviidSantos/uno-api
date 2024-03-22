package handler

import (
	"context"
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/jackc/pgx/v5"
)

func PostAnalise(ctx *gin.Context) {
	request := CreateAnaliseRequest{}

	ctx.BindJSON(&request)

	if err := request.Validate(); err != nil {
		logger.Errorf("validation error: %v", err.Error())
		sendError(ctx, http.StatusBadRequest, err.Error())
		return
	}

	query := `INSERT INTO analise(id_lote, id_ensaio, especificacao) VALUES(@id_lote, @id_ensaio, @especificacao)`
	args := pgx.NamedArgs{
		"id_lote":       request.IdLote,
		"id_ensaio":     request.IdEnsaio,
		"especificacao": request.Especificacao,
	}

	_, err := db.Exec(context.Background(), query, args)

	if err != nil {
		logger.Errorf("error inserting data: %v", err.Error())
		sendError(ctx, http.StatusInternalServerError, "erro ao cadastrar analise")
		return
	}

	sendSuccess(ctx, "create-analise", "analise adicionada com sucesso!")
}
