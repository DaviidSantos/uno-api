package handler

import (
	"fmt"
	"net/http"
	"time"

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

type SolicitacaoAnaliseResponse struct {
	IdRow            int        `json:"id_row"`
	IdSa             string     `json:"id_sa"`
	Solicitante      string     `json:"solicitante"`
	NomeProjeto      string     `json:"nome_projeto"`
	TipoAnalise      string     `json:"tipo_analise"`
	PrazoAcordado    *time.Time `json:"prazo_acordado"`
	InicioProjeto    *time.Time `json:"inicio_projeto"`
	ConclusaoProjeto *time.Time `json:"conclusao_projeto"`
}

type LoteResponse struct {
	IdLote      int    `json:"id_lote"`
	IdSa        string `json:"id_sa"`
	Solicitante string `json:"solicitante"`
	NomeProjeto string `json:"nome_projeto"`
	TipoAnalise string `json:"tipo_analise"`
	Amostra     string `json:"amostra"`
	Quantidade  int    `json:"quantidade"`
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
