package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.*;


@Entity
public class Anuncio implements Comparable<Anuncio> {
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
    private String palavraChave;
 
	@Column
    private boolean interesseEmFormarBanda = false;

    @Column
    private boolean interesseEmTocarOcasionalmente = false;

    @Column
	private ArrayList<Estilos> listaEstiloGosta = new ArrayList<Estilos>();

    @Column
	private ArrayList<Estilos> listaEstiloNaoGosta = new ArrayList<Estilos>();;

    @Column
	private ArrayList<Instrumentos> listaDeInstrumentos = new ArrayList<Instrumentos>();;

    @Column
    private String email;

    @Column
    private String perfilFacebook;
    
    @Temporal(TemporalType.DATE)
    private Date data = new Date();
    
    public Anuncio() {
    }

	public Anuncio(String titulo, String descricao, String cidade, String bairro,String email, String perfilFacebook,
                   String interesse, String palavraChave, ArrayList<Instrumentos> listaDeInstrumentos, ArrayList<Estilos> listaEstiloGosta, ArrayList<Estilos> listaEstiloNaoGosta) throws Exception {

        this.titulo = titulo;
        this.descricao = descricao;
        this.cidade = cidade;
        this.bairro = bairro;
        this.email = email;
        this.palavraChave = palavraChave;
        this.perfilFacebook = perfilFacebook;
        if (interesse.equals("Banda")) {
        	this.interesseEmFormarBanda = true;
        }       
        else if (interesse.equals("Ocasionalmente")){ 
        	this.interesseEmTocarOcasionalmente = true;
        }
        
        this.listaEstiloGosta = listaEstiloGosta;
        this.listaEstiloNaoGosta = listaEstiloNaoGosta;
        this.listaDeInstrumentos = listaDeInstrumentos;
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
	
	public void setInteresse(String interesse) {
        if (interesse.equals("Banda")) {
        	this.interesseEmFormarBanda = true;
        }       
        else if (interesse.equals("Ocasionalmente")){ 
        	this.interesseEmTocarOcasionalmente = true;
        }
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
	
    public Date getData() { return data;}

    public void setData(Date data) {
        this.data = data;
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

    public String getInteresse() {
    	if (getInteresseEmFormarBanda()) {
    		return "Interessado em formar banda";
    	} else {
    		return "Interessado em tocar ocasionalmente";
    	}    	
    }

    public void setListaEstiloGosta(ArrayList<Estilos> listaEstiloGosta) {
        this.listaEstiloGosta = listaEstiloGosta;
    }
    

    public void setListaEstiloNaoGosta(ArrayList<Estilos> listaEstiloNaoGosta) {
        this.listaEstiloNaoGosta = listaEstiloNaoGosta;
    }

    public void setListaDeInstrumentos(ArrayList<Instrumentos> listaDeInstrumentos) throws Exception {
        if (listaDeInstrumentos.isEmpty()) {
            throw  new Exception("Voce precisa dizer quais instrumentos toca.");
        }

        this.listaDeInstrumentos = listaDeInstrumentos;
    }

    public void setEmail(String email) {
    	this.email = email;
    }
    
    public void setFacebook(String perfilFacebook) {
    	this.perfilFacebook = perfilFacebook;
    }
    
    public String getEmail() { return email;}
    
    public String getFacebook() { return perfilFacebook;}

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
    
    public String getPalavraChave() {
		return palavraChave;
	}

	public void setPalavraChave(String palavraChave) {
		this.palavraChave = palavraChave;
	}

    @Override
	public int compareTo(Anuncio o) {
        return getData().compareTo(o.getData()) * (-1);
    }

	public ArrayList<Estilos> getListaEstiloGosta() {
		return listaEstiloGosta;
	}

	public ArrayList<Estilos> getListaEstiloNaoGosta() {
		return listaEstiloNaoGosta;
	}

	public ArrayList<Instrumentos> getListaDeInstrumentos() {
		return listaDeInstrumentos;
	}
}
