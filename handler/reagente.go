package handler

import (
	"context"
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/jackc/pgx/v5"
)

func GetReagentes(ctx *gin.Context) {
	query := `SELECT
							r.id_reagente, e.local, r.nome, r.fornecedor,
							r.descricao, r.unidade, r.quantidade, r.nota_fiscal, r.validade
						FROM reagente r
						INNER JOIN estoque e
							ON e.id_estoque = r.id_estoque`

	rows, err := db.Query(context.Background(), query)

	if err != nil {
		logger.Errorf("error selecting reagentes: %v", err.Error())
		sendError(ctx, http.StatusInternalServerError, "error selecting reagentes")
		return
	}
	defer rows.Close()

	var reagentes []ReagenteResponse

	if rows.Next() {
		var reagente ReagenteResponse

		err := rows.Scan(&reagente.IdReagente, &reagente.Estoque, &reagente.Nome, &reagente.Fornecedor, &reagente.Descricao, &reagente.Unidade, &reagente.Quantidade, &reagente.NotaFiscal, &reagente.Validade)

		if err != nil {
			logger.Errorf("error looping result set: %v", err.Error())
			sendError(ctx, http.StatusInternalServerError, "error selecting reagentes")
			return
		}

		reagentes = append(reagentes, reagente)
	}

	sendSuccess(ctx, "get-reagentes", reagentes)
}

func PostReagente(ctx *gin.Context) {
	request := CreateReagenteRequest{}

	ctx.BindJSON(&request)

	if err := request.Validate(); err != nil {
		logger.Errorf("validation error: %v", err.Error())
		sendError(ctx, http.StatusBadRequest, err.Error())
		return
	}

	query := `INSERT INTO reagente
							(id_estoque, nome, fornecedor, descricao, unidade, quantidade, nota_fiscal, validade)
							VALUES(@id_estoque, @nome, @fornecedor, @descricao, @unidade, @quantidade, @nota_fiscal, @validade)`

	args := pgx.NamedArgs{
		"id_estoque":  request.IdEstoque,
		"nome":        request.Nome,
		"fornecedor":  request.Fornecedor,
		"descricao":   request.Descricao,
		"unidade":     request.Unidade,
		"quantidade":  request.Quantidade,
		"nota_fiscal": request.NotaFiscal,
		"validade":    request.Validade,
	}

	_, err := db.Exec(context.Background(), query, args)

	if err != nil {
		logger.Errorf("error inserting data: %v", err)
		sendError(ctx, http.StatusInternalServerError, err.Error())
		return
	}

	sendSuccess(ctx, "create-reagente", "reagente adicionado com sucesso!")
}
