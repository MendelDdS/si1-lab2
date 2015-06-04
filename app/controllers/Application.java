package controllers;

import models.Anuncio;
import models.DAO.GenericDAO;
import play.Logger;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.cadastro;
import views.html.sobre;

import java.util.Collections;
import java.util.List;

public class Application extends Controller {
	private static Form<Anuncio> adForm = Form.form(Anuncio.class);
    private static final GenericDAO DAO = new GenericDAO();
    private static int adFinished = 0;

    @Transactional
    public static Result index() {
        return anuncios();
    }

    @Transactional
    public static Result anuncios() {
        List<Anuncio> result = DAO.findAllByClass(Anuncio.class);
        Collections.sort(result);

        return ok(views.html.index.render(result));
    }

    @Transactional
    public static Result novoAnuncio() {
    	// O formulário do Anuncio Preenchido
    	Form<Anuncio> filledForm = adForm.bindFromRequest();
		
    	if (filledForm.hasErrors()) {
            List<Anuncio> result = DAO.findAllByClass(Anuncio.class);
            //TODO falta colocar na interface mensagem de erro.
			return badRequest(views.html.index.render(result));
    	} else {
            Anuncio novoAnuncio = filledForm.get();
            Logger.debug("Criando anuncio: " + filledForm.data().toString() + " como " + novoAnuncio.getTitulo());
			// Persiste o Livro criado
			DAO.persist(novoAnuncio);
			// Espelha no Banco de Dados
			DAO.flush();
            /*
             * Usar routes.Application.<uma action> é uma forma de
             * evitar colocar rotas literais (ex: "/books")
             * hard-coded no código. Dessa forma, se mudamos no
             * arquivo routes, continua funcionando.
             */
			return redirect(routes.Application.index());
		}
    }
    
    @Transactional
    public static Result sobre() {
    	return ok(sobre.render());
    }
    
    @Transactional
    public static Result cadastro() {
    	return ok(cadastro.render());
    }    
}
