package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.regex.*;

@Entity
public class Anuncio {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String titulo;

    @Column
	private String descricao;

    @Column
	private String cidade;

    @Column
	private String bairro;

    @Column
    private boolean interesseEmFormarBanda = false;

    @Column
    private boolean interesseEmTocarOcasionalmente = false;

    @Column
	private ArrayList<String> listaEstiloGosta;

    @Column
	private ArrayList<String> listaEstiloNaoGosta;

    @Column
	private ArrayList<String> listaDeInstrumentos;

    @Column
    private String[] formaDeContato;

    public Anuncio() {
    }

	public Anuncio(String titulo, String descricao, String cidade, String bairro,
                   boolean interesseEmFormarBanda, boolean interesseEmTocarOcasionalmente, ArrayList<String> listaDeInstrumentos, String email,
                   String perfilFacebook, ArrayList<String> listaEstiloGosta, ArrayList<String> listaEstiloNaoGosta) throws Exception {

        formaDeContato = new String[2];
        setTitulo(titulo);
        setDescricao(descricao);
        setCidade(cidade);
        setBairro(bairro);
        setInteresseEmFormarBanda(interesseEmFormarBanda);
        setInteresseEmTocarOcasionalmente(interesseEmTocarOcasionalmente);
        setListaEstiloGosta(listaEstiloGosta);
        setListaEstiloNaoGosta(listaEstiloGosta);
        setListaDeInstrumentos(listaDeInstrumentos);
        addContato(email, perfilFacebook);
	}

    public Long getId() { return id;}

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) throws Exception {
		if (titulo.trim() == "" || titulo == null)
            throw new Exception("Por favor, insira um titulo ao seu anuncio.");
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) throws Exception {
		if (descricao.trim() == "" || descricao == null)
            throw new Exception("Por favor, insira uma descricao ao seu anuncio.");
        this.descricao = descricao;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) throws Exception {
		if (cidade.trim() == "" || cidade == null)
            throw new Exception("Por favor, insira sua cidade.");
        this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) throws Exception {
        if (bairro.trim() == "" || bairro.trim() == "")
            throw new Exception("Por favor, insira seu bairro.");
        this.bairro = bairro;
	}

    public boolean getInteresseEmFormarBanda() { return interesseEmFormarBanda;}

    public void setInteresseEmFormarBanda(boolean interessado) {
        if (interessado)
            interesseEmFormarBanda = true;
        else
            interesseEmFormarBanda = false;
    }

    public boolean getInteresseEmTocarOcasionalmente() { return interesseEmTocarOcasionalmente;}

    public void setInteresseEmTocarOcasionalmente(boolean interessado) {
        if (interessado)
            interesseEmTocarOcasionalmente = true;
        else
            interesseEmTocarOcasionalmente = false;
    }

	public void addListaQueGosta(String estilo) throws Exception {
        if (estilo != null) {
            if (listaEstiloGosta.contains(estilo)) {
                throw new Exception("Voce ja adicionou esse estilo.");
            }
            listaEstiloGosta.add(estilo);
        }
	}

    public void setListaEstiloGosta(ArrayList<String> listaEstiloGosta) {
        this.listaEstiloGosta = listaEstiloGosta;
    }
	
	public void addListaQueNaoGosta(String estilo) throws Exception {
        if (estilo != null) {
            if (listaEstiloNaoGosta.contains(estilo)) {
                throw new Exception("Voce ja adicionou esse estilo.");
            }
            listaEstiloNaoGosta.add(estilo);
        }
	}

    public void setListaEstiloNaoGosta(ArrayList<String> listaEstiloNaoGosta) {
        this.listaEstiloNaoGosta = listaEstiloNaoGosta;
    }

	public void addInstrumentos(String instrumento) throws Exception{
		if (instrumento.trim() == "" || instrumento == null) {
			 throw new Exception("Especifique os instrumentos que voce toca.");
		}
		listaDeInstrumentos.add(instrumento);
	}

    public void setListaDeInstrumentos(ArrayList<String> listaDeInstrumentos) throws Exception {
        if (listaDeInstrumentos.isEmpty()) {
            throw  new Exception("Voce precisa dizer quais instrumentos toca.");
        }

        this.listaDeInstrumentos = listaDeInstrumentos;
    }

    public void addContato(String email, String perfilFacebook) throws Exception {
        if ((email.trim() == "") && (perfilFacebook.trim() == "") || (email == null && perfilFacebook == null)) {
            throw new Exception("Insira pelo menos uma forma de contato.");
        }

        if (email.trim() == "" || email == null) {
            formaDeContato[1] = perfilFacebook;
        }

        else if (perfilFacebook.trim() == "" || perfilFacebook == null) {
            validEmail(email);
            formaDeContato[0] = email;
        }

        else {
            formaDeContato[1] = perfilFacebook;
            validEmail(email);
            formaDeContato[0] = email;
        }
    }

    public boolean validEmail(String email) throws Exception {
        Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher m = p.matcher(email);
        if (m.find()){
            return true;
        }
        else{
            throw new Exception("Este e-mail nao e válido");
        }
    }


}
