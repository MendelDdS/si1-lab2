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
    private static final GenericDAO DAO = new GenericDAO();
    private static Form<Anuncio> form = Form.form(Anuncio.class);
    private static Form<String> formFinalizar = Form.form(String.class);
    private static int adsFinished = 15;

    @Transactional
    public static Result index() {
        return anuncios();
    }

    @Transactional
    public static Result anuncios() {
        List<Anuncio> resultado = DAO.findAllByClass(Anuncio.class);
        Collections.sort(resultado);

        return ok(index.render(resultado));
    }

    @Transactional
    public static Result novoAnuncio() {
        Form<Anuncio> formPreenchido = form.bindFromRequest();

        if (formPreenchido.hasErrors()) {
            List<Anuncio> resultado = DAO.findAllByClass(Anuncio.class);
            Collections.sort(resultado);

            return badRequest(index.render(resultado));
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
        String encontrouParceiros = formFinalizarPreenchido.data().get("encontrouParceiros");

        if (codigoForm.equals(codigo)) {
            DAO.removeById(Anuncio.class, id);
            DAO.flush();

            if (encontrouParceiros.equals("Sim")) {
                adsFinished++;
            }

            return anuncios();
        } else {
            List<Anuncio> resultado = DAO.findAllByClass(Anuncio.class);
            Collections.sort(resultado);

            return ok(index.render(resultado));
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
