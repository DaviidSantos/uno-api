package router

import (
	"github.com/DaviidSantos/uno-api/handler"
	"github.com/gin-gonic/gin"
)

func initializeRoutes(r *gin.Engine) {
	handler.Init()

	v1 := r.Group("/api/v1")

	solicitante := v1.Group("solicitante")
	{
		solicitante.GET("", handler.GetSolicitante)

		solicitante.POST("", handler.PostSolicitante)

		solicitante.PATCH("", handler.PatchSolicitante)
	}

	solicitacao_analise := v1.Group("solicitacao_analise")
	{
		solicitacao_analise.POST("", handler.PostSolicitacaoAnalise)
	}
}
