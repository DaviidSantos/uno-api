package router

import (
	"github.com/DaviidSantos/uno-api/handlers"
	"github.com/gin-gonic/gin"
)

func initializeRoutes(r *gin.Engine) {
	v1 := r.Group("/api/v1")

	solicitante := v1.Group("solicitante")
	{
		solicitante.GET("", handlers.GetSolicitante)

		solicitante.POST("", handlers.PostSolicitante)

		solicitante.PATCH("", handlers.PatchSolicitante)
	}
}
