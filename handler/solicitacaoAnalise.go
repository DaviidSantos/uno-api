package handler

import (
	"context"
	"fmt"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/jackc/pgx/v5"
)

func GetSolicitacaoAnalise(ctx *gin.Context) {
	query := `SELECT 
		sa.id_row, sa.id_sa, s.nome_fantasia,
		sa.nome_projeto, ta.tipo_analise,
		sa.prazo_acordado, sa.inicio_projeto, sa.conclusao_projeto
	FROM solicitacao_analise sa
	INNER JOIN solicitante s
		ON s.cnpj = sa.cnpj
	INNER JOIN tipo_analise ta
		ON ta.id_tipo_analise = sa.id_tipo_analise
	`

	rows, err := db.Query(context.Background(), query)

	if err != nil {
		logger.Errorf("error selecting solicitacoes de analise: %v", err.Error())
		sendError(ctx, http.StatusInternalServerError, "error selecting solicitacoes de analise")
		return
	}

	var solicitacoesAnalise []SolicitacaoAnaliseResponse

	for rows.Next() {
		var solicitacaoAnalise SolicitacaoAnaliseResponse

		err := rows.Scan(&solicitacaoAnalise.IdRow, &solicitacaoAnalise.IdSa, &solicitacaoAnalise.Solicitante, &solicitacaoAnalise.NomeProjeto, &solicitacaoAnalise.TipoAnalise, &solicitacaoAnalise.PrazoAcordado, &solicitacaoAnalise.InicioProjeto, &solicitacaoAnalise.ConclusaoProjeto)

		if err != nil {
			logger.Errorf("error looping result set: %v", err.Error())
			sendError(ctx, http.StatusInternalServerError, "error selecting solicitacoes de analise")
			return
		}

		solicitacoesAnalise = append(solicitacoesAnalise, solicitacaoAnalise)
	}

	sendSuccess(ctx, "get-all-solicitacoes-analise", solicitacoesAnalise)
}

func PostSolicitacaoAnalise(ctx *gin.Context) {
	request := CreateSolicitacaoAnaliseRequest{}

	ctx.BindJSON(&request)

	if err := request.Validate(); err != nil {
		logger.Errorf("validation error: %v", err.Error())
		sendError(ctx, http.StatusBadRequest, err.Error())
		return
	}

	currentYear := time.Now().Year()
	count := 0

	query := fmt.Sprintf("SELECT COUNT(DISTINCT id_sa) FROM solicitacao_analise WHERE id_sa LIKE '%%%d'", currentYear)

	err := db.QueryRow(context.Background(), query).Scan(&count)

	if err != nil {
		logger.Errorf("error counting sa: %v", err)
		sendError(ctx, http.StatusInternalServerError, "error counting sa")
		return
	}

	for _, id_tipo_analise := range request.IdTipoAnalise {
		query = `INSERT INTO solicitacao_analise(id_sa, cnpj, nome_projeto, id_tipo_analise, prazo_acordado) VALUES(@id_sa, @cnpj, @nome_projeto, @id_tipo_analise, @prazo_acordado)`

		args := pgx.NamedArgs{
			"id_sa":           fmt.Sprintf("SA-%04d/%d", count+1, currentYear),
			"cnpj":            request.Cnpj,
			"nome_projeto":    request.NomeProjeto,
			"id_tipo_analise": id_tipo_analise,
			"prazo_acordado":  request.PrazoAcordado,
		}

		_, err = db.Exec(context.Background(), query, args)

		if err != nil {
			logger.Errorf("error inserting data: %v", err)
			sendError(ctx, http.StatusInternalServerError, err.Error())
			return
		}
	}

	sendSuccess(ctx, "create-solicitacao-analise", "solicitação de análise adicionada com sucesso!")
}

func PatchSolicitacaoAnalise(ctx *gin.Context) {
	idRow := ctx.Query("id_row")

	if idRow == "" {
		sendError(ctx, http.StatusBadRequest, errParamIsRequired("id_row", "string").Error())
		return
	}

	request := PatchSolicitacaoAnaliseRequest{}

	ctx.BindJSON(&request)

	if err := request.Validate(); err != nil {
		logger.Errorf("validation error: %v", err.Error())
		sendError(ctx, http.StatusBadRequest, err.Error())
		return
	}

	solicitacaoAnalise := SolicitacaoAnaliseResponse{}

	err := db.QueryRow(context.Background(), "SELECT * FROM solicitacao_analise WHERE id_row = $1", idRow).Scan(&solicitacaoAnalise.IdRow, &solicitacaoAnalise.IdSa, &solicitacaoAnalise.Solicitante, &solicitacaoAnalise.NomeProjeto, &solicitacaoAnalise.TipoAnalise, &solicitacaoAnalise.PrazoAcordado, &solicitacaoAnalise.InicioProjeto, &solicitacaoAnalise.ConclusaoProjeto)

	if err != nil {
		if err.Error() == "no rows in result set" {
			sendError(ctx, http.StatusNotFound, "solicitacao de análise não encontrada!")
			return
		}

		logger.Errorf("querying error: %v", err.Error())
		sendError(ctx, http.StatusInternalServerError, err.Error())
		return
	}

	if request.InicioProjeto != "" {
		parsedDate, _ := time.Parse("2006-01-02", request.InicioProjeto)
		solicitacaoAnalise.InicioProjeto = &parsedDate
	}

	if request.ConclusaoProjeto != "" {
		parsedDate, _ := time.Parse("2006-01-02", request.ConclusaoProjeto)
		solicitacaoAnalise.ConclusaoProjeto = &parsedDate
	}

	query := `UPDATE solicitacao_analise SET inicio_projeto = @inicio_projeto, conclusao_projeto = @conclusao_projeto WHERE id_row = @id_row`

	args := pgx.NamedArgs{
		"inicio_projeto":    solicitacaoAnalise.InicioProjeto,
		"conclusao_projeto": solicitacaoAnalise.ConclusaoProjeto,
		"id_row":            idRow,
	}

	_, err = db.Exec(context.Background(), query, args)

	if err != nil {
		logger.Errorf("error updating data: %v", err)
		sendError(ctx, http.StatusInternalServerError, err.Error())
		return
	}

	sendSuccess(ctx, "update-solicitacao-analise", "solicitação de análise alterado com sucesso!")
}
