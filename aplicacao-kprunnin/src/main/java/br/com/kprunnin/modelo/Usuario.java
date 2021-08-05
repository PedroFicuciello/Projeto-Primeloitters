/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.kprunnin.modelo;

/**
 *
 * @author olive
 */
public class Usuario {
    
    private Integer id;
    private String nome;
    private String login;
    private String senha;
    
    public Usuario(String login, String senha){
        this.login = login;
        this.senha = senha;
    }
    
    public Usuario(Integer id, String nome, String login, String senha) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    @Override
    public String toString() {
        return String.format("Usuário: %s, id: %d, login: %s, senha: %s", this.nome, this.id, this.login, this.senha);
    }
  
}
