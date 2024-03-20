package handler

import (
	"context"
	"net/http"

	"github.com/gin-gonic/gin"
)

func PostEstoque(ctx *gin.Context) {
	request := CreateEstoqueRequest{}

	ctx.BindJSON(&request)

	if err := request.Validate(); err != nil {
		logger.Errorf("validation error: %v", err.Error())
		sendError(ctx, http.StatusBadRequest, err.Error())
		return
	}

	_, err := db.Exec(context.Background(), "INSERT INTO estoque(local) VALUES($1)", request.Local)

	if err != nil {
		logger.Errorf("error inserting data: %v", err.Error())
		sendError(ctx, http.StatusInternalServerError, "erro ao cadastrar estoque")
		return
	}

	sendSuccess(ctx, "create-estoque", "estoque adicionado com sucesso!")
}
