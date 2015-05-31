package controllers;

import models.Anuncio;
import models.DAO.GenericDAO;
import play.Logger;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.cadastro;
import views.html.index;
import views.html.sobre;

import java.util.Collections;
import java.util.List;

public class Application extends Controller {
    private static final GenericDAO DAO = new GenericDAO();
    private static Form<Anuncio> form = Form.form(Anuncio.class);
    private static Form<String> formFinalizar = Form.form(String.class);
    private static int adFinished = 0;

    @Transactional
    public static Result index() {
        return anuncios();
    }

    @Transactional
    public static Result anuncios() {
        List<Anuncio> result = DAO.findAllByClass(Anuncio.class);
        Collections.sort(result);

        return ok(views.html.index.render(result, false, adFinished));
    }

    @Transactional
    public static Result novoAnuncio() {
        Form<Anuncio> formPreenchido = form.bindFromRequest();

        if (formPreenchido.hasErrors()) {
            List<Anuncio> result = DAO.findAllByClass(Anuncio.class);
            Collections.sort(result);

            return badRequest(index.render(result, false, adFinished));
        } else {
            Anuncio newAnuncio = formPreenchido.get();

            DAO.persist(newAnuncio);
            DAO.flush();

            return anuncios();
        }
    }

    @Transactional
    public static Result finalizaAnuncio(String codigo, Long id) {
        Form<String> formFinalizarPreenchido = formFinalizar.bindFromRequest();
        String codigoForm = formFinalizarPreenchido.data().get("finalizar");

        if (codigoForm.equals(codigo)) {
            DAO.removeById(Anuncio.class, id);
            DAO.flush();

            adFinished++;
            return anuncios();
        } else {
            List<Anuncio> resultado = DAO.findAllByClass(Anuncio.class);
            Collections.sort(resultado);

            return ok(index.render(resultado, true, adFinished));
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
