package handler

import (
	"fmt"
	"time"
)

func errParamIsRequired(name, typ string) error {
	return fmt.Errorf("param: %s (type: %s) is required", name, typ)
}

func errParamMalformed(name, typ string) error {
	return fmt.Errorf("param: %s (type: %s) is malformed", name, typ)
}

type CreateSolicitanteRequest struct {
	Cnpj         string `json:"cnpj"`
	NomeFantasia string `json:"nome_fantasia"`
	Cep          string `json:"cep"`
	Rua          string `json:"rua"`
	Numero       string `json:"numero"`
	Cidade       string `json:"cidade"`
	Estado       string `json:"estado"`
}

func (r *CreateSolicitanteRequest) Validate() error {
	if r.Cnpj == "" {
		return errParamIsRequired("cnpj", "string")
	}
	if r.NomeFantasia == "" {
		return errParamIsRequired("nome_fantasia", "string")
	}
	if r.Cep == "" {
		return errParamIsRequired("cep", "string")
	}
	if r.Rua == "" {
		return errParamIsRequired("rua", "string")
	}
	if r.Numero == "" {
		return errParamIsRequired("numero", "string")
	}
	if r.Cidade == "" {
		return errParamIsRequired("cidade", "string")
	}
	if r.Estado == "" {
		return errParamIsRequired("estado", "string")
	}

	return nil
}

type PatchsolicitanteRequest struct {
	NomeFantasia string `json:"nome_fantasia"`
	Cep          string `json:"cep"`
	Rua          string `json:"rua"`
	Numero       string `json:"numero"`
	Cidade       string `json:"cidade"`
	Estado       string `json:"estado"`
}

func (r *PatchsolicitanteRequest) Validate() error {
	if r.NomeFantasia != "" || r.Cep != "" || r.Rua != "" || r.Numero != "" || r.Cidade != "" || r.Estado != "" {
		return nil
	}

	return fmt.Errorf("at least one field must be provided")
}

type CreateSolicitacaoAnaliseRequest struct {
	Cnpj          string `json:"cnpj"`
	NomeProjeto   string `json:"nome_projeto"`
	IdTipoAnalise []int  `json:"id_tipo_analise"`
	PrazoAcordado string `json:"prazo_acordado"`
}

func (r *CreateSolicitacaoAnaliseRequest) Validate() error {
	if r.Cnpj == "" {
		return errParamIsRequired("cnpj", "string")
	}

	if r.NomeProjeto == "" {
		return errParamIsRequired("nome_projeto", "string")
	}

	if len(r.IdTipoAnalise) == 0 {
		return errParamIsRequired("id_tipo_analise", "[]int")
	}

	_, err := time.Parse("2006-01-02", r.PrazoAcordado)

	if err != nil {
		logger.Errorf("error parsing prazo_acordado: %v", err.Error())
		return errParamMalformed("prazo_acordado", "date")
	}

	return nil
}

type PatchSolicitacaoAnaliseRequest struct {
	InicioProjeto    string `json:"inicio_projeto"`
	ConclusaoProjeto string `json:"conclusao_projeto"`
}

func (r *PatchSolicitacaoAnaliseRequest) Validate() error {
	if r.InicioProjeto != "" || r.ConclusaoProjeto != "" {
		return nil
	}

	return fmt.Errorf("at least one field must be provided")
}

type CreateLoteRequest struct {
	IdSA       int    `json:"id_sa"`
	Amostra    string `json:"amostra"`
	Quantidade int    `json:"quantidade"`
}

func (r *CreateLoteRequest) Validate() error {
	if r.IdSA == 0 {
		return errParamIsRequired("id_sa", "int")
	}

	if r.Amostra == "" {
		return errParamIsRequired("amostra", "string")
	}

	if r.Quantidade == 0 {
		return errParamIsRequired("quantidade", "int")
	}

	return nil
}

type CreateEstoqueRequest struct {
	Local string `json:"local"`
}

func (r *CreateEstoqueRequest) Validate() error {
	if r.Local == "" {
		return errParamIsRequired("local", "string")
	}

	return nil
}

type CreateReagenteRequest struct {
	IdEstoque  int        `json:"id_estoque"`
	Nome       string     `json:"nome"`
	Fornecedor string     `json:"fornecedor"`
	Descricao  string     `json:"descricao"`
	Unidade    string     `json:"unidade"`
	Quantidade int        `json:"quantidade"`
	NotaFiscal string     `json:"nota_fiscal"`
	Validade   string `json:"validade"`
}

func (r *CreateReagenteRequest) Validate() error {
	if r.IdEstoque == 0 {
		return errParamIsRequired("id_estoque", "int")
	}

	if r.Nome == "" {
		return errParamIsRequired("nome", "string")
	}

	if r.Fornecedor == "" {
		return errParamIsRequired("fornecedor", "string")
	}

	if r.Unidade == "" {
		return errParamIsRequired("unidade", "string")
	}

	if r.NotaFiscal == "" {
		return errParamIsRequired("nota_fiscal", "string")
	}

	_, err := time.Parse("2006-01-02", r.Validade)

	if err != nil {
		logger.Errorf("error parsing validade: %v", err.Error())
		return errParamMalformed("validade", "date")
	}


	return nil
}
