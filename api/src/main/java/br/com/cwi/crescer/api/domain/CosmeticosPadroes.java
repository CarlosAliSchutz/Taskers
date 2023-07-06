package br.com.cwi.crescer.api.domain;

public enum CosmeticosPadroes {
    PLANTAPADRAO("Folhagem1"),
    PETPADRAO("Cachorro1"),
    HOMEMPADRAO("HomemCasual"),
    MULHERPADRAO("MulherCasual"),
    FUNDOPADRAO("Sala1");

    private final String nome;

    CosmeticosPadroes(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return nome;
    }

}


