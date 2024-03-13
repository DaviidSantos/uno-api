package handler

import (
	"context"
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/jackc/pgx/v5"
)

func GetSolicitante(ctx *gin.Context) {
	ctx.JSON(http.StatusOK, gin.H{
		"msg": "GET Solicitante",
	})
}

func PostSolicitante(ctx *gin.Context) {
	request := CreateSolicitanteRequest{}

	ctx.BindJSON(&request)

	if err := request.Validate(); err != nil {
		logger.Errorf("validation error: %v", err.Error())
		sendError(ctx, http.StatusBadRequest, err.Error())
		return
	}

	query := `INSERT INTO solicitante VALUES(@cnpj, @nome_fantasia, @cep, @rua, @numero, @cidade, @estado)`
	args := pgx.NamedArgs{
		"cnpj":          request.Cnpj,
		"nome_fantasia": request.NomeFantasia,
		"cep":           request.Cep,
		"rua":           request.Rua,
		"numero":        request.Numero,
		"cidade":        request.Cidade,
		"estado":        request.Estado,
	}

	_, err := db.Exec(context.Background(), query, args)

	if err != nil {
		logger.Errorf("error inserting data: %v", err)
		sendError(ctx, http.StatusInternalServerError, err.Error())
		return
	}

	sendSuccess(ctx, "create-solicitante", "solicitante adicionado com sucesso!")
}

func PatchSolicitante(ctx *gin.Context) {
	ctx.JSON(http.StatusOK, gin.H{
		"msg": "PATCH Solicitante",
	})
}
