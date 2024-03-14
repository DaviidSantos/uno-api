package handler

import (
	"context"
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/jackc/pgx/v5"
)

func GetSolicitante(ctx *gin.Context) {
	cnpj := ctx.Query("cnpj")

	if cnpj == "" {
		sendError(ctx, http.StatusBadRequest, errParamIsRequired("cnpj", "queryParameter").Error())
		return
	}

	solicitante := SolicitanteResponse{}

	err := db.QueryRow(context.Background(), "SELECT * FROM solicitante WHERE cnpj = $1", cnpj).Scan(&solicitante.Cnpj, &solicitante.NomeFantasia, &solicitante.Cep, &solicitante.Rua, &solicitante.Numero, &solicitante.Cidade, &solicitante.Estado)

	if err != nil {
		if err.Error() == "no rows in result set" {
			sendError(ctx, http.StatusNotFound, "solicitante não encontrado!")
			return
		}

		logger.Errorf("querying error: %v", err.Error())
		sendError(ctx, http.StatusInternalServerError, err.Error())
		return
	}

	sendSuccess(ctx, "get-solicitante", solicitante)
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
