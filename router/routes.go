package router

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

func initializeRoutes(r *gin.Engine) {
	v1 := r.Group("/api/v1")

	solicitante := v1.Group("solicitante")
	{
		solicitante.GET("", func(ctx *gin.Context) {
			ctx.JSON(http.StatusOK, gin.H{
				"msg": "GET Solicitante",
			})
		})

		solicitante.POST("", func(ctx *gin.Context) {
			ctx.JSON(http.StatusOK, gin.H{
				"msg": "POST Solicitante",
			})
		})

		solicitante.PATCH("", func(ctx *gin.Context) {
			ctx.JSON(http.StatusOK, gin.H{
				"msg": "PATCH Solicitante",
			})
		})
	}
}
