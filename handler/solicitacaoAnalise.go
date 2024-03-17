package handler

import (
	"context"
	"fmt"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/jackc/pgx/v5"
)

func PostSolicitacaoAnalise(ctx *gin.Context) {
	request := CreateSolicitacaoAnaliseRequest{}

	ctx.BindJSON(&request)

	if err := request.Validate(); err != nil {
		logger.Errorf("validation error: %v", err.Error())
		sendError(ctx, http.StatusBadRequest, err.Error())
		return
	}

	currentYear := time.Now().Year()
	count := 0

	query := fmt.Sprintf("SELECT COUNT(DISTINCT id_sa) FROM solicitacao_analise WHERE id_sa LIKE '%%%d'", currentYear)

	err := db.QueryRow(context.Background(), query).Scan(&count)

	if err != nil {
		logger.Errorf("error counting sa: %v", err)
		sendError(ctx, http.StatusInternalServerError, "error counting sa")
		return
	}

	for _, id_tipo_analise := range request.IdTipoAnalise {
		query = `INSERT INTO solicitacao_analise(id_sa, cnpj, nome_projeto, id_tipo_analise, prazo_acordado) VALUES(@id_sa, @cnpj, @nome_projeto, @id_tipo_analise, @prazo_acordado)`

		args := pgx.NamedArgs{
			"id_sa":           fmt.Sprintf("SA-%04d/%d", count+1, currentYear),
			"cnpj":            request.Cnpj,
			"nome_projeto":    request.NomeProjeto,
			"id_tipo_analise": id_tipo_analise,
			"prazo_acordado":  request.PrazoAcordado,
		}

		_, err = db.Exec(context.Background(), query, args)

		if err != nil {
			logger.Errorf("error inserting data: %v", err)
			sendError(ctx, http.StatusInternalServerError, err.Error())
			return
		}
	}

	sendSuccess(ctx, "create-solicitacao-analise", "solicitação de análise adicionada com sucesso!")
}
