package router

import (
	"github.com/DaviidSantos/uno-api/handler"
	"github.com/gin-gonic/gin"
)

func initializeRoutes(r *gin.Engine) {
	v1 := r.Group("/api/v1")

	solicitante := v1.Group("solicitante")
	{
		solicitante.GET("", handler.GetSolicitante)

		solicitante.POST("", handler.PostSolicitante)

		solicitante.PATCH("", handler.PatchSolicitante)
	}
}
