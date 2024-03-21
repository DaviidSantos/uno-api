package handler

import (
	"context"
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/jackc/pgx/v5"
)

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
