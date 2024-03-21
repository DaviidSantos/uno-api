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
		solicitacao_analise.GET("", handler.GetSolicitacaoAnalise)

		solicitacao_analise.POST("", handler.PostSolicitacaoAnalise)

		solicitacao_analise.PATCH("", handler.PatchSolicitacaoAnalise)
	}

	lote := v1.Group("lote")
	{
		lote.GET("", handler.GetLotes)

		lote.POST("", handler.PostLote)
	}

	estoque := v1.Group("estoque")
	{
		estoque.POST("", handler.PostEstoque)
	}

	reagente := v1.Group("reagente")
	{
		reagente.POST("", handler.PostReagente)
	}
}
