package handler

import (
	"fmt"
	"net/http"

	"github.com/gin-gonic/gin"
)

type SolicitanteResponse struct {
	Cnpj         string `json:"cnpj"`
	NomeFantasia string `json:"nome_fantasia"`
	Cep          string `json:"cep"`
	Rua          string `json:"rua"`
	Numero       string `json:"numero"`
	Cidade       string `json:"cidade"`
	Estado       string `json:"estado"`
}

func sendError(ctx *gin.Context, code int, msg string) {
	ctx.Header("Content-type", "application/json")
	ctx.JSON(code, gin.H{
		"message":   msg,
		"errorCode": code,
	})
}

func sendSuccess(ctx *gin.Context, op string, data interface{}) {
	ctx.Header("Content-type", "application/json")
	ctx.JSON(http.StatusOK, gin.H{
		"message": fmt.Sprintf("operation from handler: %s successfull", op),
		"data":    data,
	})
}
