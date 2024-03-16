package handler

import "fmt"

func errParamIsRequired(name, typ string) error {
	return fmt.Errorf("param: %s (type: %s) is required", name, typ)
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
