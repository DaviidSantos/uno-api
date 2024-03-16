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
	cnpj := ctx.Query("cnpj")

	if cnpj == "" {
		sendError(ctx, http.StatusBadRequest, errParamIsRequired("cnpj", "queryParameter").Error())
		return
	}

	request := PatchsolicitanteRequest{}

	ctx.BindJSON(&request)

	if err := request.Validate(); err != nil {
		logger.Errorf("validation error: %v", err.Error())
		sendError(ctx, http.StatusBadRequest, err.Error())
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

	if request.NomeFantasia != "" {
		solicitante.NomeFantasia = request.NomeFantasia
	}

	if request.Cep != "" {
		solicitante.Cep = request.Cep
	}

	if request.Rua != "" {
		solicitante.Rua = request.Rua
	}

	if request.Cidade != "" {
		solicitante.Cidade = request.Cidade
	}

	if request.Estado != "" {
		solicitante.Estado = request.Estado
	}

	query := `UPDATE solicitante SET nome_fantasia = @nome_fantasia, cep = @cep, rua = @rua, numero = @numero, cidade = @cidade, estado = @estado WHERE cnpj = @cnpj`

	args := pgx.NamedArgs{
		"nome_fantasia": solicitante.NomeFantasia,
		"cep":           solicitante.Cep,
		"rua":           solicitante.Rua,
		"numero":        solicitante.Numero,
		"cidade":        solicitante.Cidade,
		"estado":        solicitante.Estado,
		"cnpj":          cnpj,
	}

	_, err = db.Exec(context.Background(), query, args)

	if err != nil {
		logger.Errorf("error updating data: %v", err)
		sendError(ctx, http.StatusInternalServerError, err.Error())
		return
	}

	sendSuccess(ctx, "update-solicitante", "solicitante alterado com sucesso!")
}
