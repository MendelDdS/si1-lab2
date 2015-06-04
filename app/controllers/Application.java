package controllers;

import models.Anuncio;
import models.DAO.GenericDAO;
import play.Logger;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.util.Collections;
import java.util.List;

public class Application extends Controller {
    private static int adsHelped = 15;
    private static final GenericDAO DAO = new GenericDAO();
    private static Form<Anuncio> form = Form.form(Anuncio.class);
    private static Form<String> formFinish = Form.form(String.class);

    @Transactional
    public static Result index() {
        return anuncios();
    }

    @Transactional
    public static Result anuncios() {
        List<Anuncio> atualizado = DAO.findAllByClass(Anuncio.class);
        Collections.sort(atualizado);

        return ok(index.render(atualizado, false, adsHelped));
    }

    @Transactional
    public static Result novoAnuncio() {
        Form<Anuncio> formPreenchido = form.bindFromRequest();

        if (formPreenchido.hasErrors()) {
            List<Anuncio> atualizado = DAO.findAllByClass(Anuncio.class);
            Collections.sort(atualizado);

            return badRequest(index.render(atualizado, false, adsHelped));
        } else {
            Anuncio newAnuncio = formPreenchido.get();

            DAO.persist(newAnuncio);
            DAO.flush();

            return anuncios();
        }
    }

    @Transactional
    public static Result finalizaAnuncio(String codigo, Long id) {
        Form<String> formFinalizarPreenchido = formFinish.bindFromRequest();
        String codigoForm = formFinalizarPreenchido.data().get("finalizar");
        String encontrouParceiros = formFinalizarPreenchido.data().get("encontrouParceiros");

        if (codigoForm.equals(codigo)) {
            DAO.removeById(Anuncio.class, id);
            DAO.flush();

            if (encontrouParceiros.equals("Sim")) {
            	adsHelped++;
            }

            return index();
        } else {
            List<Anuncio> atualizado = DAO.findAllByClass(Anuncio.class);
            Collections.sort(atualizado);

            return ok(index.render(atualizado, true, adsHelped));
        }
    }
    @Transactional
    public static Result sobre() {
    	return ok(views.html.sobre.render());
    }
    
    @Transactional
    public static Result cadastro() {
    	return ok(views.html.cadastro.render());
    }    
}
