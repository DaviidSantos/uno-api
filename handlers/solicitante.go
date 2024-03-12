package handlers

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

func GetSolicitante(ctx *gin.Context) {
	ctx.JSON(http.StatusOK, gin.H{
		"msg": "GET Solicitante",
	})
}

func PostSolicitante(ctx *gin.Context) {
	ctx.JSON(http.StatusOK, gin.H{
		"msg": "POST Solicitante",
	})
}

func PatchSolicitante(ctx *gin.Context) {
	ctx.JSON(http.StatusOK, gin.H{
		"msg": "PATCH Solicitante",
	})
}
