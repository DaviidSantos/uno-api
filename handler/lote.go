package handler

import (
	"context"
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
	"github.com/jackc/pgx/v5"
)

func GetLotes(ctx *gin.Context) {
	param := ctx.Query("id_sa")

	idSa, err := strconv.Atoi(param)

	if err != nil {
		logger.Errorf("error converting id_sa to integer: %v", err)
		sendError(ctx, http.StatusInternalServerError, "erro ao converter parâmetro para int")
		return
	}

	if idSa == 0 {
		sendError(ctx, http.StatusBadRequest, errParamIsRequired("id_sa", "string").Error())
		return
	}

	query := `
		SELECT 
			l.id_lote, sa.id_sa, s.nome_fantasia,
			sa.nome_projeto, tp.tipo_analise, l.amostra, l.quantidade
		FROM lote l
		INNER JOIN solicitacao_analise sa
			ON sa.id_row = l.id_sa
		INNER JOIN solicitante s
			ON s.cnpj = sa.cnpj
		INNER JOIN tipo_analise tp
			ON tp.id_tipo_analise = sa.id_tipo_analise
		WHERE l.id_sa = $1
	`

	rows, err := db.Query(context.Background(), query, idSa)

	if err != nil {
		logger.Errorf("error selecting lotes: %v", err.Error())
		sendError(ctx, http.StatusInternalServerError, "error selecting solicitacoes de analise")
		return
	}

	var lotes []LoteResponse

	for rows.Next() {
		var lote LoteResponse

		err := rows.Scan(&lote.IdLote, &lote.IdSa, &lote.Solicitante, &lote.NomeProjeto, &lote.TipoAnalise, &lote.Amostra, &lote.Quantidade)

		if err != nil {
			logger.Errorf("error looping result set: %v", err.Error())
			sendError(ctx, http.StatusInternalServerError, "error selecting lotes")
			return
		}

		lotes = append(lotes, lote)
	}

	sendSuccess(ctx, "get-lotes", lotes)
}

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
